/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kami.console;

import com.kamike.db.GenericCreator;
import com.kamike.db.Transaction;
import com.kamike.db.generic.GenericCreate;

/**
 *
 * @author THiNk
 */
public class TestTableCreator extends GenericCreator<TestTable>{

    public TestTableCreator(Transaction ts,String dbName)
    {
        super(ts,dbName);
    }
    @Override
    public GenericCreate<TestTable> newCreate() {
       
        return new TestTableCreate();
    }
    
}
