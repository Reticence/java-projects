package com.meridian.param;

import java.io.Serializable;

/**
 * 
 * @author Reticence (liuyang_blue@qq.com)
 * @date 2018年3月19日 下午12:52:08
 * @version 1.0
 * @parameter
 */
public class DBParam implements Serializable {

    /**  **/
    private static final long serialVersionUID = -3904977641299387433L;

    /** 主机IP地址 **/
    private String host;
    /** 端口 **/
    private String port;
    /** 用户名 **/
    private String username;
    /** 密码 **/
    private String password;
    /** 数据库名称 **/
    private String database;
    /** 实例名 **/
    private String sid;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

}
