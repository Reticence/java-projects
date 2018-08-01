package com.meridian.utils;

import com.meridian.param.DBParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Reticence (liuyang_blue@qq.com)
 * @version 1.0
 * @date 2018-03-27 16:44
 * @parameter
 */
public class OracleConnect {
    private static Logger LOGGER = LoggerFactory.getLogger(OracleConnect.class);

    static {
        try {
            // 要是导入驱动没有成功的话都是会出现classnotfoundException.自己看看是不是哪里错了,例如classpath这些设置
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getCnnectiopn(DBParam dbp) {
        return getConnection(dbp.getHost(), dbp.getPort(), dbp.getSid(), dbp.getUsername(), dbp.getPassword());
    }

    public static Connection getConnection(String host, String port, String sid, String username, String password) {
        String url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
        return getConnection(url, username, password);
    }

    public static Connection getConnection(String url, String username, String password) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            LOGGER.error(url);
            LOGGER.error("MySQL error(connect)");
        }
        return conn;
    }
}
