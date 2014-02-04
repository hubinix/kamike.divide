/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.config.SystemConfig;
import com.kamike.config.SystemConstants;
import com.kamike.db.Transaction;
import java.util.Date;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author THiNk
 */
public class KamiDbInst {
    
    private static volatile KamiDbInst kamiDbInst = new KamiDbInst();
    private KamiDatabaseReader kamiDatabaseReader;
    private final ConcurrentHashMap‎<String, KamiDatabase> pool = new ConcurrentHashMap‎<>();
    
    public void init() {
        Transaction ts = new Transaction();
        new KamiDatabaseCreator(ts, SystemConfig.SYSDBNAME).init();
        new KamiTableCreator(ts, SystemConfig.SYSDBNAME).init();
        //插入当前数据库连接
        KamiDatabaseWriter kdw = new KamiDatabaseWriter(ts);
        KamiDatabaseReader kdr = new KamiDatabaseReader();
        KamiDatabase database = kdr.get(SystemConfig.THIS_DB_ID);
        if (database == null) {
            database = new KamiDatabase();
            database.setId(SystemConfig.THIS_DB_ID);
            database.setCreateDate(new Date(System.currentTimeMillis()));
            database.setDbName(SystemConfig.DB_NAME);
            database.setDriver(SystemConstants.MYSQL_DRIVER);
            database.setInstance("");
            database.setIp(SystemConfig.DB_IP);
            database.setPassword(SystemConfig.DB_PASSWORD);
            database.setPort(SystemConfig.DB_PORT);
            database.setType(SystemConstants.MYSQL);
            database.setUpdateDate(database.getCreateDate());
            database.setUser(SystemConfig.DB_USER);
            kdw.add(database);
        }
        
        ts.save();
    }
    
    public void destroy() {
        if (pool != null) {
            Iterator<KamiDatabase> it = pool.values().iterator();
            while (it.hasNext()) {
                KamiDatabase value = it.next();
                value.destroy();
                
            }
        }
        
        pool.clear();
    }
    
    private KamiDbInst() {
        kamiDatabaseReader = new KamiDatabaseReader();
        
    }
    
    public static KamiDbInst getInstance() {
        return kamiDbInst;
    }
    
    public KamiDatabase getDatabase(String id) {
        KamiDatabase db = null;
        if (pool.containsKey(id)) {
            db = pool.get(id);
        } else {
            
            db = kamiDatabaseReader.get(id);
            pool.put(id, db);
        }
        return db;
    }
}
