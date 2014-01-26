/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;
 
import com.kamike.config.SystemConfig;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author THiNk
 */
public class KamiDbInst {

    private static volatile KamiDbInst kamiDbInst = new KamiDbInst();
    private   KamiDatabaseReader kamiDatabaseReader;
    private final ConcurrentHashMap‎<String, KamiDatabase> pool = new ConcurrentHashMap‎<>();

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
        kamiDatabaseReader=new KamiDatabaseReader();
    }

    public static KamiDbInst getInstance() {
        return kamiDbInst;
    }

  
    public KamiDatabase getDatabase(String id) {
        KamiDatabase db=null;
        if(pool.containsKey(id))
        {
            db=pool.get(id);
        }
        else {
           
            db=kamiDatabaseReader.get(id);
            pool.put(id, db); 
        }
        return db;
    }
}
