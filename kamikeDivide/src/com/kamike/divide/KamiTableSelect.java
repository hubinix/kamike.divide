/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.generic.GenericSelect;

/**
 *
 * @author THiNk
 */
public class KamiTableSelect extends GenericSelect<KamiTable> {

    public KamiTableSelect(KamiTable t) {
        super(t);
    }

    public KamiTableSelect() {
        super();
    }

    @Override
    public KamiTable create() {
        return new KamiTable();
    }

}
