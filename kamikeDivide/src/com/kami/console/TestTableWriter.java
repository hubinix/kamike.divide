/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kami.console;

import com.kamike.db.GenericWriter;
import com.kamike.db.Transaction;

/**
 *
 * @author THiNk
 */
public class TestTableWriter extends GenericWriter<TestTable> {

    public TestTableWriter(Transaction ts) {
        super(ts);
    }
    
}
