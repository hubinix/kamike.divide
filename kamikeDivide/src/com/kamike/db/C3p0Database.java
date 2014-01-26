/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.db;

import java.sql.Connection;
import com.mchange.v2.c3p0.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author brin
 */
public class C3p0Database extends Database {

    public   ComboPooledDataSource cpds;
    
    public C3p0Database(
            String type,
            String ip,
            String instance,
            String port,
            String name,
            String user,
            String password) {
        
        super(type, ip, instance, port, name, user, password);
    }
    @Override
    public void destroy()
    {
        try {
            DataSources.destroy(cpds);
        } catch (SQLException ex) {
            Logger.getLogger(C3p0Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    @Override
    public Connection getSingleConnection() {

 
        if(cpds==null){
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
            return super.getSingleConnection();
        }



    }
}
