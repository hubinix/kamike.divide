/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import java.util.ArrayList;

/**
 *
 * @author THiNk
 */
public class KamiDelete extends KamiSQL {

    public KamiDelete(String tableName) {
        super(tableName);
    }
    public KamiDelete() {
        super();
    }
    
    

    @Override
    public ArrayList<KamiTable> findTables() {

        KamiTableReader kamiTableReader;
        kamiTableReader = new KamiTableReader();
        ArrayList<KamiTable> ret = kamiTableReader.find(this.tableName);
        return ret;

    }

}
