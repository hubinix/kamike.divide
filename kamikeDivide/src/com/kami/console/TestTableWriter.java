/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kami.console;

import com.kamike.divide.KamiWriter;
import com.kamike.divide.generic.KamiGenericInsert;

/**
 *
 * @author THiNk
 */
public class TestTableWriter extends KamiWriter<TestTable> {

    @Override
    public KamiGenericInsert<TestTable> createKamiGenericInsert(TestTable t) {
       return new TestTableInsert(t);
    }

}
