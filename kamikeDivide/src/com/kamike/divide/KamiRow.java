/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamike.divide;

import com.kamike.db.generic.GenericColumn;
import java.util.ArrayList;

/**
 *
 * @author THiNk
 */
public class KamiRow {
    private ArrayList<GenericColumn> columns; 
    public KamiRow()
    {
        this.columns=new ArrayList<GenericColumn>();
    }

    /**
     * @return the columns
     */
    public ArrayList<GenericColumn> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(ArrayList<GenericColumn> columns) {
        this.columns = columns;
    }
}
