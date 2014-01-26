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
public class KamiDatabaseSelect extends GenericSelect<KamiDatabase> {

    public KamiDatabaseSelect(KamiDatabase t) {
        super(t);
    }

    public KamiDatabaseSelect() {
        super();
    }

    @Override
    public KamiDatabase create() {
        return new KamiDatabase();
    }

}
