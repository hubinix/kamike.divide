/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.db;

import com.kamike.config.SystemConfig;
import com.kamike.config.SystemConstants;

/**
 *
 * @author brin
 */
public class SysDbInst {

    private static volatile SysDbInst dbinst = new SysDbInst();
    private Database database;

    public void destroy() {
        if (database != null) {
            database.destroy();
        }

    }

    private SysDbInst() {

    }

    public static SysDbInst getInstance() {

        return dbinst;
    }

    /**
     * @return the DATABASE_URL
     */
    public Database getDatabase(boolean newDatabase) {
        if (newDatabase) {

            String ip = SystemConfig.getInstance().getSystemConstant("website.db_ip");
            String port = SystemConfig.getInstance().getSystemConstant("website.db_port");
            String name = SystemConfig.getInstance().getSystemConstant("website.db_name");
            String user = SystemConfig.getInstance().getSystemConstant("website.db_user");
            String password = SystemConfig.getInstance().getSystemConstant("website.db_password");

            database = new MySQLDatabase(ip, port, name, user, password);
        }
        return database;
    }

    public Database getDatabase() {
        if (database == null) {

            String ip = SystemConfig.getInstance().getSystemConstant("website.db_ip");
            String port = SystemConfig.getInstance().getSystemConstant("website.db_port");
            String name = SystemConfig.getInstance().getSystemConstant("website.db_name");
            String user = SystemConfig.getInstance().getSystemConstant("website.db_user");
            String password = SystemConfig.getInstance().getSystemConstant("website.db_password");

            database = new MySQLDatabase(ip, port, name, user, password);
        }
        return database;
    }

}
