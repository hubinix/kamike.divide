/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.db;

import com.kamike.config.SystemConfig;

/**
 *
 * @author brin
 */
public class DbInst {

    private static volatile DbInst dbinst = new DbInst();
    private Database database;

    public void destroy() {
        if (database != null) {
            database.destroy();
        }

    }

    private DbInst() {

    }

    public static DbInst getInstance() {

        return dbinst;
    }

    /**
     * @return the DATABASE_URL
     */
    public Database getDatabase(boolean newDatabase) {
        if (newDatabase) {

           String ip = SystemConfig.DB_IP;
            String port = SystemConfig.DB_PORT;
            String name = SystemConfig.DB_NAME;
            String user = SystemConfig.DB_USER;
            String password = SystemConfig.DB_PASSWORD;

            database = new MySQLDatabase(ip, port, name, user, password);
        }
        return database;
    }

    public Database getDatabase() {
        if (database == null) {

            String ip = SystemConfig.DB_IP;
            String port = SystemConfig.DB_PORT;
            String name = SystemConfig.DB_NAME;
            String user = SystemConfig.DB_USER;
            String password = SystemConfig.DB_PASSWORD;

            database = new MySQLDatabase(ip, port, name, user, password);
        }
        return database;
    }

}
