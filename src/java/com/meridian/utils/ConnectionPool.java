package com.meridian.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 刘洋
 * @date 2017年2月22日 上午10:58:56
 * @version 1.0
 * @parameter
 */
public class ConnectionPool {
    private static Logger LOG = LoggerFactory.getLogger(ConnectionPool.class);

    /** 初始连接数 **/
    private int initPoolSize = 1;
    /** 最大连接数 **/
    private int maxPoolSize = 10;
    /** 当前连接数 **/
    private int currentSize = 0;
    /** 最大清理时间(分钟) **/
    private int maxCleanupTime = 5;
    /** 空闲时间(分钟) **/
    private int freeTime = 0;
    /** 等待超时时间 **/
    private int waitTimeOut = 10;
    /** 关闭标志 **/
    private boolean isClosed = false;
    /** 连接列表 **/
    private LinkedList<Connection> connectionList = new LinkedList<Connection>();
    
    private Thread thread;
    
    public ConnectionPool() {
        initializationPool();
    }
    
    public ConnectionPool(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
        initializationPool();
    }
    
    public int getWaitTimeOut() {
        return waitTimeOut;
    }

    public void setWaitTimeOut(int waitTimeOut) {
        this.waitTimeOut = waitTimeOut;
    }
    
    private void initializationPool() {
        for (int i = 0; i < initPoolSize; i++) {
            Connection connection = MysqlConnect.getConnection();
            if (MysqlConnect.isValid(connection)) {
                connectionList.add(connection);
                currentSize++;
            } else {
                i--;
            }
        }
        
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
                        continue;
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
        int waitTime = 0;
        while (true) {
            if (connectionList.size() > 0) {
                connection = connectionList.getFirst();
                connectionList.removeFirst();
                if (MysqlConnect.isValid(connection)) {
                    return connection;
                } else {
                    currentSize--;
                    continue;
                }
            } else if (connectionList.size() == 0 && currentSize < maxPoolSize) {
                connection = MysqlConnect.getConnection();
                if (MysqlConnect.isValid(connection)) {
                    currentSize++;
                    return connection;
                } else {
                    continue;
                }
            } else if (waitTime > waitTimeOut) {
                throw new RuntimeException("连接数达到上限,请等待...");
            }
            
            try {
                Thread.sleep(1000);
                waitTime++;
            } catch (InterruptedException e) {
                continue;
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
            if (connectionList.size() < maxPoolSize) {
                connectionList.addLast(connection);
            } else {
                LOG.error("连接池已达最大连接数,关闭该连接...");
                MysqlConnect.closeConnection(connection);
            }
        } else {
            LOG.error("连接失效,已丢弃...");
            connection = null;
        }
    }
    
    public void clearConnectionPool() {
        while (true) {
            if (connectionList.size() < currentSize && connectionList.size() > 1) {
                MysqlConnect.closeConnection(connectionList.getFirst());
                connectionList.removeFirst();
                currentSize--;
            } else {
                break;
            }
        }
    }
    
    public void colse() {
        isClosed = true;
        clearConnectionPool();
        if (thread.isAlive()) {
            thread.interrupt();
        }
    }
}
