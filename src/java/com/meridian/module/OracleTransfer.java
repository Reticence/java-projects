package com.meridian.module;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meridian.param.DBParam;
import com.meridian.utils.CheckNull;
import com.meridian.utils.ConnectionPool;

/**
 * 
 * @author Reticence (liuyang_blue@qq.com)
 * @date 2017年12月12日 下午5:18:54
 * @version 1.0
 * @parameter
 */
public class OracleTransfer {
    private static Logger LOGGER = LoggerFactory.getLogger(OracleTransfer.class);
    private static DecimalFormat DF = new DecimalFormat("#,###");
    private static String SF = "%1$10s";
    private static String SF2 = "%1$4s";

    private final static int threadCount[] = { 0 };
    private final String[] stopObject = new String[1];
    private final Map<String, String> rMap = new HashMap<String, String>();
    private final List<String> errors = new ArrayList<String>();

    private ConnectionPool pool;
    private BlockingQueue<String[]> queue;
    private Set<String> targets = null;
    private DBParam dbp;
    private String writeDB;
    private BufferedWriter fbw = null;
    private int numRows;
    private boolean toFile = false;
    private boolean toMysql = false;
    private boolean toOracle = false;

    public OracleTransfer(DBParam dbp) {
        this.dbp = dbp;
    }

    public void set(Set<String> targets, String writeDB, int numRows) {
        if(!CheckNull.isNull(targets)) {
            this.targets = new HashSet<String>();
            for (String target : targets) {
                this.targets.add(target.toLowerCase());
            }
        }
        this.writeDB = writeDB;
        this.numRows = numRows;
        queue = new ArrayBlockingQueue<String[]>(numRows * 3);
    }

    public void start2File(String savePath, int maxThreads) {
        LOGGER.info("Transfer data started...");
        this.toFile = true;
        try {
            this.fbw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(savePath), "UTF-8"));
            start2Mysql(maxThreads);
            fbw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start2Mysql(final int maxThreads) {
        LOGGER.info("Transfer data started...");
        this.toMysql = true;
        rMap.put("\"", "`");
        rMap.put(" BYTE", "");
        rMap.put(" ENABLE", "");
        rMap.put(" VARCHAR2", " VARCHAR");
        rMap.put(" NUMBER", " DECIMAL");
        rMap.put(" DATE", " DATETIME");
        rMap.put(" CLOB", " TEXT");
        rMap.put(" LONG RAW", " LONG");
        rMap.put(" DECIMAL(*", " DECIMAL(10");
        rMap.put(" NUMBER(*", " DECIMAL(10");

        Connection _conn = oracleConn(dbp);
        List<String> tables = queryTabnles(_conn);
        try {
            queue.put(stopObject);
            pool = new ConnectionPool(maxThreads / 2);
            boolean b;
            while (true) {
                String[] infos = queue.take();
                if (stopObject == infos) {
                    break;
                }
                b = pool.execUpdate(infos[0]);
                LOGGER.info("Transfer: " + b + " " + infos[1]);
            }

            List<Thread> threads = new ArrayList<Thread>();
            for (final String table : tables) {
                while (threadCount[0] >= maxThreads) {
                    Thread.sleep(1000 * 10);
                }
                final Connection conn = oracleConn(dbp);
                Runnable runnable = new Runnable() {
                    public void run() {
                        queueAndInsert(conn, table);
                    }
                };
                Thread thread = new Thread(runnable);
                threads.add(thread);
                thread.start();
                threadCount[0]++;
            }
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start2Oracle(DBParam tdbp, int maxThreads) {
        LOGGER.info("Transfer " + tdbp.getUsername() + " data started...");
        this.toOracle = true;
        this.writeDB = tdbp.getUsername().toUpperCase();
        Connection _conn = oracleConn(dbp);
        List<String> tables = queryTabnles(_conn);
        try {
            queue.put(stopObject);
            Connection _tconn = oracleConn(tdbp);
            Statement tstmt = _tconn.createStatement();
            boolean b;
            while (true) {
                String[] infos = queue.take();
                if (stopObject == infos) {
                    break;
                }
                b = executeUpdate(tstmt, infos[0]);
                LOGGER.info("Transfer: " + b + " " + infos[1]);
            }
            _tconn.close();
            List<Thread> threads = new ArrayList<Thread>();
            for (final String table : tables) {
                while (threadCount[0] >= maxThreads) {
                    Thread.sleep(1000 * 5);
                }
                final Connection conn = oracleConn(dbp);
                final Connection tconn = oracleConn(tdbp);
                Runnable runnable = new Runnable() {
                    public void run() {
                        queueAndInsert(conn, tconn, table);
                    }
                };
                Thread thread = new Thread(runnable);
                threads.add(thread);
                thread.start();
                threadCount[0]++;
            }
            for (Thread thread : threads) {
                thread.join();
            }

            Collections.sort(errors);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("D:/Desktop/transfer_err/transfer_" + tdbp.getUsername() + ".err"), "UTF-8"));
            for (int i = 0; i < errors.size(); i++) {
                bw.write(errors.get(i));
                bw.newLine();
            }
            bw.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Connection oracleConn(DBParam dbp) {
        Connection conn = null;
        try {
            // 要是导入驱动没有成功的话都是会出现classnotfoundException.自己看看是不是哪里错了,例如classpath这些设置
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // "jdbc:oracle:thin:@计算机名称:监听端口:系统实例名", username, password,
            // phyexam/meridian@10.1.1.102:1521
            conn = DriverManager.getConnection("jdbc:oracle:thin:@" + dbp.getHost() + ":" + dbp.getPort() + ":" + dbp.getSid(), dbp.getUsername(), dbp.getPassword());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (conn == null) {
            LOGGER.error("连接失败");
            System.exit(0);
        }
        return conn;
    }

    private List<String> queryTabnles(Connection conn) {
        List<String> tables = new ArrayList<String>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TABLE_NAME FROM USER_TABLES ORDER BY TABLE_NAME ASC");
            while (rs.next()) {
                String tableOracle = rs.getString(1);
                String tableMysql = tableOracle.toLowerCase();
                if (CheckNull.isNull(targets) || targets.contains(tableMysql)) {
                    Statement _stmt = conn.createStatement();
                    ResultSet _rs = _stmt.executeQuery("SELECT DBMS_METADATA.get_ddl('TABLE', '" + tableOracle + "') FROM DUAL");
                    _rs.next();
                    String createSQL = _rs.getString(1);
                    if (toOracle) {
                        createSQL = createSQL.replace(dbp.getUsername().toUpperCase(), writeDB)
                                .replace(" TABLESPACE \"TSP_PE\"", "TABLESPACE \"" + writeDB + "\"")
                                .replace(" TABLESPACE \"TSP_PEPIC\"", "TABLESPACE \"" + writeDB + "\"");
                        queue.put(new String[] { "DROP TABLE " + writeDB + "." + tableOracle, "Drop table " + writeDB + "." + tableMysql });
                        queue.put(new String[] { createSQL, "Create table " + writeDB + "." + tableOracle });
                    } else {
                        int stop0 = createSQL.indexOf(" CONSTRAINT");
                        int stop1 = createSQL.indexOf(" USING INDEX");
                        int stop2 = createSQL.indexOf(" SEGMENT CREATION");
                        if (stop0 > 0) {
                            createSQL = createSQL.substring(0, stop0);
                            createSQL = createSQL.substring(0, createSQL.lastIndexOf(","))+ ")";
                        } else if (stop1 > 0) {
                            createSQL = createSQL.substring(0, stop1) + ")";
                        } else {
                            createSQL = createSQL.substring(0, stop2);
                        }
                        for (String key : rMap.keySet()) {
                            createSQL = createSQL.replace(key, rMap.get(key));
                        }
                        createSQL = createSQL.replaceAll(" FLOAT\\(\\d+\\)", " FLOAT");
                        createSQL = createSQL.replace(dbp.getUsername().toUpperCase(), writeDB);
                        queue.put(new String[] { "DROP TABLE IF EXISTS " + writeDB + ".`" + tableMysql + "`", "Drop table " + writeDB + "." + tableMysql });
                        queue.put(new String[] { createSQL, "Create table " + writeDB + "." + tableMysql });
                    }
                    tables.add(tableOracle);
                }
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return tables;
    }

    private void queueAndInsert(Connection conn, String table) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            int columnCount = rs.getMetaData().getColumnCount();
            int count = 0;
            String tablename = writeDB + "." + table.toLowerCase();
            StringBuffer sql = new StringBuffer("INSERT INTO " + tablename + " VALUES ");
            while (rs.next()) {
                sql.append("(");
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    if (null == value) {
                        sql.append("null,");
                    } else {
                        value = _getValue(value);
                        sql.append("'");
                        sql.append(value.replace("\\", "\\\\").replace("'", "\\'"));
                        sql.append("',");
                    }
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append("),");
                if (++count % numRows == 0) {
                    sql.deleteCharAt(sql.length() - 1);
                    execOperation(sql.toString(), String.format(SF, DF.format(count)) + "  Thread:" + String.format(SF2, Thread.currentThread().getId()) + "  @" + tablename);
                    sql = new StringBuffer("INSERT INTO " + tablename + " VALUES ");
                }
            }
            if (count % numRows > 0) {
                sql.deleteCharAt(sql.length() - 1);
                execOperation(sql.toString(), String.format(SF, DF.format(count)) + "  Thread:" + String.format(SF2, Thread.currentThread().getId()) + "  @" + tablename);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        threadCount[0]--;
    }

    private void queueAndInsert(Connection conn, Connection tconn, String table) {
        try {
            Statement tstmt = tconn.createStatement();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            int count = 0;
            StringBuffer sql;
            String tablename = writeDB + "." + table.toUpperCase();
            while (rs.next()) {
                sql = new StringBuffer("INSERT INTO " + tablename + "  VALUES (");
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    if (null == value) {
                        sql.append("null,");
                    } else {
                        value = _getValue(value);
                        if (rsmd.getColumnType(i) == 93) {
                            sql.append("TO_DATE('" + value + "', 'YYYY-MM-DD HH24:MI:SS'),");
                        } else {
                            sql.append("'");
                            sql.append(value.replace("\\", "\\\\").replace("'", "''"));
                            sql.append("',");
                        }
                    }
                }
                sql.deleteCharAt(sql.length() - 1);
                sql.append(")");

                try {
                    tstmt.executeUpdate(sql.toString());
                } catch (Exception e) {
                    errors.add(e.toString().replace("\n", " - ") + sql.toString());
                }
                if (++count % numRows == 0) {
                    LOGGER.info("Transfer: " + String.format(SF, DF.format(count)) + "  Thread:" + String.format(SF2, Thread.currentThread().getId()) + "  @" + tablename);
                }
            }
            LOGGER.info("Transfer: " + String.format(SF, DF.format(count)) + "  Thread:" + String.format(SF2, Thread.currentThread().getId()) + "  @" + tablename);
            conn.close();
            tconn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        threadCount[0]--;
    }

    private boolean executeUpdate(Statement stmt, String sql) {
        try {
            stmt.executeUpdate(sql);
            return true;
        } catch (Exception e) {
            System.out.println(sql);
            return false;
        }
        
    }

    private String _getValue(String value) {
        try {
            value = new String(value.getBytes("ISO8859_1"), "GBK");
            value = value.trim();
            if (value.startsWith("-") && value.endsWith("00:00:00")) {
                value = "1900-01-01 00:00:00";
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    private void execOperation(String sql, String info) {
        if (toFile) {
            try {
                fbw.write(sql);
                fbw.newLine();
                LOGGER.info("Write to file... " + info);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (toMysql) {
            LOGGER.info("Transfer: " + pool.execUpdate(sql.toString()) + " " + info);
        }
    }
}
