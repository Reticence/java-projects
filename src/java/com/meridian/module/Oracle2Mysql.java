package com.meridian.module;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meridian.utils.ConnectionPool;

/**
 * 
 * @author Reticence (liuyang_blue@qq.com)
 * @date 2017年12月12日 下午5:18:54
 * @version 1.0
 * @parameter
 */
public class Oracle2Mysql {
    private static Logger LOG = LoggerFactory.getLogger(Oracle2Mysql.class);
    private static DecimalFormat DF = new DecimalFormat("#,###");
    private static String SF = "%1$10s";

    private ConnectionPool pool = new ConnectionPool(5);
    private BlockingQueue<String[]> queue = new ArrayBlockingQueue<String[]>(10);
    private final String[] stopObject = new String[2];
    private Set<String> targets;
    private String sid;
    private int numRows;

    public void set(Set<String> targets, String sid, int numRows) {
        this.targets = new HashSet<String>();
        for (String target : targets) {
            this.targets.add(target.toUpperCase());
        }
        this.sid = sid;
        this.numRows = numRows;
    }

    public void start() {
        Runnable runnable = new Runnable() {
            public void run() {
                queryFromOracle();
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();

        execInsert();
        pool.colse();
    }

    private Connection oracleConn(String username, String password) {
        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            // 要是导入驱动没有成功的话都是会出现classnotfoundException.自己看看是不是哪里错了,例如classpath这些设置
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            // "jdbc:oracle:thin:@计算机名称:监听端口:系统实例名", username, password,
            conn = DriverManager.getConnection("jdbc:oracle:thin:@10.1.1.102:1521:" + sid, username, password); // phyexam/meridian@10.1.1.102:1521
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    private void queryFromOracle() {
        Connection conn = oracleConn("phyexam", "meridian");
        try {
            if (conn == null) {
                LOG.error("连接失败");
                System.exit(0);
            }
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT TABLE_NAME FROM USER_TABLES ORDER BY TABLE_NAME ASC");
            while (rs.next()) {
                String table = rs.getString(1).toUpperCase();
                if (targets.contains(table)) {
                    pool.execUpdate("TRUNCATE TABLE jfjzyy301_" + sid + "." + table);
                    queryAndWrite(conn, table);
                }
            }
            rs.close();
            queue.put(stopObject);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void queryAndWrite(Connection conn, String table) {
        String sqlPrepare = "INSERT INTO jfjzyy301_" + sid + "." + table + " VALUES ";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + table);
            StringBuffer values = new StringBuffer(sqlPrepare);
            int columnCount = rs.getMetaData().getColumnCount();
            int count = 0;
            boolean isSurplus = false;
            while (rs.next()) {
                String value;
                isSurplus = true;
                values.append("(");
                for (int i = 1; i <= columnCount; i++) {
                    value = rs.getString(i);
                    if (null == value) {
                        values.append("null,");
                    } else {
                        try {
                            value = new String(value.getBytes("ISO8859_1"), "GBK");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        values.append("'");
                        values.append(value.replace("\\", "\\\\").replace("'", "\\'"));
                        values.append("',");
                    }
                }
                values.deleteCharAt(values.length() - 1);
                values.append("),");
                if (++count % numRows == 0) {
                    isSurplus = false;
                    putInfos(values, String.format(SF, DF.format(count)) + " @" + sid + "." + table);
                    values = new StringBuffer(sqlPrepare);
                }
            }
            if (isSurplus) {
                putInfos(values, String.format(SF, DF.format(count)) + "(Finished) @" + sid + "." + table);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void putInfos(StringBuffer values, String progress) {
        values.deleteCharAt(values.length() - 1);
        String[] infos = { values.toString(), progress };
        try {
            queue.put(infos);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void execInsert() {
        try {
            while (true) {
                String[] infos = queue.take();
                if (stopObject == infos) {
                    return;
                }
                boolean b = pool.execUpdate(infos[0]);
                LOG.info("Transfer " + b + ": " + infos[1]);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
