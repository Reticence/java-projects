package com.meridian.testCode;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.concurrent.Callable;

import com.meridian.utils.ConnectionPool;

/**
 * 
 * @author 刘洋
 * @date 2017年2月21日 下午5:48:34
 * @version 1.0
 * @parameter
 */
public class ThreadPoolTask implements Callable<String> {
    private ConnectionPool connectionPool;
    private String sql;
    
    public ThreadPoolTask(ConnectionPool connectionPool, String sql) {
        this.connectionPool = connectionPool;
        this.sql = sql;
    }

    public String call() throws Exception {
        StringBuffer result = new StringBuffer();
        Connection connection = connectionPool.getConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            resultSet.next();
            result.append(resultSet.getString(1) + " >> " + resultSet.getString(2) + " >> " + resultSet.getString(3) + connection.toString());
            Thread.sleep(1000 * 1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            connectionPool.releaseConnection(connection);
        }
        return result.toString();
    }
}
