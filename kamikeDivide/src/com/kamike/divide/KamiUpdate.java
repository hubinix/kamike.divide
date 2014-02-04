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
public class KamiUpdate extends KamiSQL {

    public KamiUpdate(String tableName) {
        super(tableName);
    }

    public KamiUpdate() {
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
