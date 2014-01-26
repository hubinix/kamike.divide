/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.*;
import com.kamike.db.generic.FieldLength;
import com.kamike.db.generic.FieldName;
import com.kamike.db.generic.Id;
import com.kamike.db.generic.TableName;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brin
 */
@TableName("t_kami_database")
public class KamiDatabase {

    @Id
    @FieldName("id")
    @FieldLength(64)
    private String id;

    @FieldName("ip")
    @FieldLength(64)
    protected String ip = "127.0.0.1";

    @FieldName("type")
    @FieldLength(64)
    protected String type = "postgresql";

    @FieldName("driver")
    @FieldLength(255)
    protected String driver = "org.postgresql.Driver";

    protected String url = "";

    @FieldName("port")
    @FieldLength(64)
    protected String port = "5432";

    @FieldName("instance")
    @FieldLength(255)
    protected String instance = "";

    @FieldName("dbName")
    @FieldLength(255)
    protected String dbName = "postgis_spatial";

    @FieldName("user")
    @FieldLength(255)
    protected String user = "postgres";
    @FieldName("password")
    @FieldLength(255)
    protected String password = "root123";
    
    @FieldName("create_date")
    private Date createDate;
    
    @FieldName("update_date")
    private Date updateDate;
    
    protected Connection singleConnection;

    protected ComboPooledDataSource cpds;

    public void init(
            String type,
            String ip,
            String instance,
            String port,
            String dbName,
            String user,
            String password) {
        this.type = type;
        this.ip = ip;
        this.instance = instance;
        this.port = port;
        this.dbName = dbName;
        this.user = user;
        this.password = password;

    }

    public String getUrl() {
        return url;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the driver
     */
    public String getDriver() {
        return driver;
    }

    /**
     * @param driver the driver to set
     */
    public void setDriver(String driver) {
        this.driver = driver;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the instance
     */
    public String getInstance() {
        return instance;
    }

    /**
     * @param instance the instance to set
     */
    public void setInstance(String instance) {
        this.instance = instance;
    }

    public void destroy() {

    }

    /**
     * @return the singleConnection
     */
    public Connection getConnection() {
        if (cpds == null) {
            try {
                cpds = new ComboPooledDataSource();
                cpds.setDriverClass(this.getDriver());
                cpds.setJdbcUrl(this.getUrl());
                cpds.setUser(user);
                cpds.setPassword(password);
                cpds.setMaxStatements(180);
                cpds.setMinPoolSize(1);
                cpds.setMaxPoolSize(50);
                cpds.setAcquireIncrement(5);
                cpds.setMaxIdleTime(3600);
                cpds.setMaxIdleTimeExcessConnections(1200);
                cpds.setMaxConnectionAge(27000);
                cpds.setMaxStatements(200);
                cpds.setMaxStatementsPerConnection(50);
                cpds.setNumHelperThreads(10);
                cpds.setIdleConnectionTestPeriod(30);
                cpds.setCheckoutTimeout(30000);
                cpds.setPreferredTestQuery("select 1");
                cpds.setAcquireRetryDelay(1000);
                cpds.setAcquireRetryAttempts(3);
                cpds.setTestConnectionOnCheckin(true);
            //cpds.setAutomaticTestTable();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        try {
            return cpds.getConnection();
        } catch (SQLException ex) {
            Logger.getLogger(C3p0Database.class.getName()).log(Level.SEVERE, null, ex);
            return this.getSingleConnection();
        }

    }

    public Connection getSingleConnection() {

        if (singleConnection != null) {
            try {
                if (!singleConnection.isClosed()) {
                    singleConnection.close();
                }

            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            singleConnection = null;
        }
        try {
            Class.forName(this.getDriver()).newInstance();
            try {
                singleConnection = DriverManager.getConnection(this.getUrl(), user, password);
            } catch (SQLException ex) {
                singleConnection = null;
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InstantiationException ex) {
            singleConnection = null;
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            singleConnection = null;
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
            singleConnection = null;
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, e);
        }

        return singleConnection;

    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the name
     */
    public String getDbName() {
        return dbName;
    }

    /**
     * @param name the name to set
     */
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}
