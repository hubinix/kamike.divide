/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.db;

 
 
/**
 *
 * @author brin
 */
public class MySQLDatabase extends C3p0Database {

    public MySQLDatabase(String ip, String port, String name, String user, String password) {
        super("mysql", ip, "", port, name, user, password);
    }

    @Override
    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc");
        sb.append(":");
        sb.append("mysql");
        sb.append(":");
        sb.append("//");
        sb.append(ip);
        sb.append(":");
        sb.append(port);
        sb.append("/");
        sb.append(name);
        this.url = sb.toString();
        return url;
    }

    @Override
    public String getDriver() {
        return "com.mysql.jdbc.Driver";
    }
}
