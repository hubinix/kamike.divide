/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kami.console;

import com.kamike.db.GenericCreator;
import com.kamike.db.Transaction;
import com.kamike.divide.generic.KamiGenericInsert;

/**
 *
 * @author THiNk
 */
public class TestTableInsert extends KamiGenericInsert<TestTable>{

    public TestTableInsert(TestTable t) {
        super(t);
    }

    @Override
    public GenericCreator<TestTable> creator(Transaction ts, String dbName) {
       return new TestTableCreator(ts,dbName);
    }
    
}
