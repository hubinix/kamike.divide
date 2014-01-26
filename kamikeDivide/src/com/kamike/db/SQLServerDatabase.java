/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.db;

import com.kamike.config.SystemConstants;

 

 

/**
 *
 * @author brin
 */
public class SQLServerDatabase extends C3p0Database {

    public SQLServerDatabase(String ip, String port, String name, String user, String password) {
        super("sqlserver", ip, "", port, name, user, password);
    }

    @Override
    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("jdbc");
        sb.append(":");
        sb.append("sqlserver");
        sb.append(":");
        sb.append("//");
        sb.append(ip);
        sb.append(":");
        sb.append(port);
        sb.append(";DatabaseName=");
        sb.append(name);
        this.url = sb.toString();
        return url;
    }

    @Override
    public String getDriver() {
        return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    }
}
