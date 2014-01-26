/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.db;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brin
 */
public class Database {

    
    
    protected String ip="127.0.0.1";
    protected String type="postgresql";
    protected String driver="org.postgresql.Driver";
    protected String url="";
    protected String port="5432";
    protected String instance="";
    protected String name="postgis_spatial";
    protected String user="postgres";
    protected String password="root123";
    protected Connection singleConnection;

    public Database(    
     String type,
     String ip,
     String instance,
     String port, 
     String name,
     String user,
     String password)
    {
        this.type=type;
        this.ip=ip;
        this.instance=instance;
        this.port=port;
        this.name=name;
        this.user=user;
        this.password=password;
        
        
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

    public void destroy()
    {
        
    }
    /**
     * @return the singleConnection
     */
    public Connection getSingleConnection() {
        

        if(singleConnection!=null)
        {
            try {
                if(!singleConnection.isClosed())
                    singleConnection.close();
                
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
            singleConnection=null;
        }
        try {
            Class.forName(this.getDriver()).newInstance();
            try {
                singleConnection = DriverManager.getConnection(this.getUrl(), user, password);
            } catch (SQLException ex) {
                singleConnection=null;
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (InstantiationException ex) {
            singleConnection=null;
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            singleConnection=null;
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException e) {
              singleConnection=null;
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
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}