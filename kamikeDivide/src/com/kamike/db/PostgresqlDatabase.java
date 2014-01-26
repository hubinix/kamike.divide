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
public class PostgresqlDatabase extends C3p0Database {

    public PostgresqlDatabase(String ip, String port, String name, String user, String password) {
        super(SystemConstants.POSTGRESQL, ip, "", port, name, user, password);
        
    }

    @Override
    public String getUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(SystemConstants.JDBC);
        sb.append(":");
        sb.append(SystemConstants.POSTGRESQL);
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
        return SystemConstants.POSTGRESQL_DRIVER;
    }
}
