package com.meridian.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meridian.param.DBParam;

/**
 * 
 * @author 刘洋
 * @date 2017年2月22日 上午10:58:56
 * @version 1.0
 * @parameter
 */
public class MysqlConnectionPool {
    private static Logger LOG = LoggerFactory.getLogger(MysqlConnectionPool.class);

    /** 最大连接数 **/
    private int maxPoolSize = 10;
    /** 当前连接数 **/
    private int currentSize = 0;
    /** 最大清理时间(分钟) **/
    private int maxCleanupTime = 5;
    /** 空闲时间(分钟) **/
    private int freeTime = 0;
    /** 关闭标志 **/
    private boolean isClosed = false;
    /** 连接列表 **/
    private BlockingQueue<Connection> connections;
    
    private DBParam dbp;
    private boolean useConfig = true;
    
    private Thread thread;
    
    public MysqlConnectionPool() {
        create();
    }
    
    public MysqlConnectionPool(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        create();
    }
    
    public MysqlConnectionPool(int maxPoolSize, DBParam dbp) {
        this.maxPoolSize = maxPoolSize;
        this.dbp = dbp;
        this.useConfig = false;
        create();
    }
    
    private void _put(Connection connection) {
        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private Connection _get() {
        while (true) {
            try {
                return connections.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    private Connection _mcgc() {
        if (useConfig) {
            return MysqlConnect.getConnection();
        } else {
            return MysqlConnect.getConnection(dbp.getHost(), dbp.getHost(), dbp.getUsername(), dbp.getPassword(), dbp.getDatabase());
        }
    }
    
    private void create() {
        connections = new ArrayBlockingQueue<Connection>(10);
        _put(_mcgc());
        currentSize++;

        Runnable runnable = new Runnable() {
            public void run() {
                while (!isClosed) {
                    try {
                        Thread.sleep(1000 * 60);
                        freeTime++;
                        if (freeTime > maxCleanupTime) {
                            clearConnectionPool();
                        }
                    } catch (InterruptedException e) {
                    }
                }
            }
        };
        thread = new Thread(runnable);
        thread.start();
    }
    
    public synchronized Connection getConnection() {
        if (isClosed) {
            throw new RuntimeException("连接池已关闭...");
        }
        freeTime = 0;
        Connection connection;
        while (true) {
            if (connections.size() == 0 && currentSize < maxPoolSize) {
                connection = _mcgc();
                currentSize++;
            } else {
                connection = _get();
            }
            if (MysqlConnect.isValid(connection)) {
                return connection;
            } else {
                currentSize--;
            }
        }
    }
    
    public List<String[]> execQuery(String sql) {
        List<String[]> queryResultList = new ArrayList<String[]>();
        Connection connection = getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            ResultSetMetaData rsmd =rs.getMetaData();
            int maxcol = rsmd.getColumnCount();
            while (rs.next()) {
                String[] results = new String[maxcol];
                for (int i = 0; i < maxcol; i++) {
                    results[i] = rs.getString(i + 1);
                }
                queryResultList.add(results);
            }
            statement.close();
        } catch (SQLException e) {
        } finally {
            releaseConnection(connection);
        }
        return queryResultList;
    }
    
    public boolean execUpdate(String sql) {
        Connection connection = getConnection();
        try {
            connection.createStatement().executeUpdate(sql);
        } catch (SQLException e) {
            LOG.error(e.toString());
            return false;
        } finally {
            releaseConnection(connection);
        }
        return true;
    }
    
    public void releaseConnection(Connection connection) {
        if (isClosed) {
            LOG.error("连接池已关闭...");
            MysqlConnect.closeConnection(connection);
        } else if (MysqlConnect.isValid(connection)) {
            if (connections.size() < maxPoolSize) {
                _put(connection);
            } else {
                LOG.error("连接池已达最大连接数,关闭该连接...");
                MysqlConnect.closeConnection(connection);
            }
        } else {
            LOG.error("连接失效,已丢弃...");
            currentSize--;
        }
    }
    
    private void clearConnectionPool() {
        while (connections.size() < currentSize && connections.size() > 1) {
            MysqlConnect.closeConnection(_get());
            currentSize--;
        }
    }
    
    public void close() {
        isClosed = true;
        clearConnectionPool();
        if (thread.isAlive()) {
            thread.interrupt();
        }
    }
}
