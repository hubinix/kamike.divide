/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kami.console;

import com.kamike.divide.generic.KamiGenericSelect;

/**
 *
 * @author THiNk
 */
public class TestTableSelect extends KamiGenericSelect<TestTable> {

    public TestTableSelect(TestTable t) {
        super(t);
    }

    public TestTableSelect() {
        super();
    }

    @Override
    public TestTable create() {
        return new TestTable();
    }

}
