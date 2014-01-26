/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamike.divide;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author THiNk
 */
public class KamiResultSet {
     private int index;

    List<KamiRow> rows;

 

    private KamiRow currentRow;
    
    public KamiResultSet() {
        //线程安全
        rows = Collections.synchronizedList(new ArrayList<KamiRow>());
  
        index = -1;
        currentRow=null;
    }


    public void add(KamiRow row) {
        this.rows.add(row);
    }
 
    public boolean next() {
        int size = rows.size();
        index++;
        if(index<size)
        {
            this.currentRow=rows.get(index);
            return true;
        }
        else
        {
            return false;
        }
        
    }

    public String getString(int i) {
        return rows.get(index).getColumns().get(i).getStrValue();
    }

    
    public void close() {
        for (KamiRow row : rows) {
            row.getColumns().clear();
        }
        rows.clear();
        rows = null;
    }

   
    public Timestamp getTimestamp(int i) {
        if(this.currentRow!=null)
        {
            return this.currentRow.getColumns().get(i).getTimestampValue();
        }
        else
        {
            return null;
        }
        
    }

  
    public int getInt(int i) {

        if(this.currentRow!=null)
        {
            return this.currentRow.getColumns().get(i).getIntValue();
        }
        else
        {
            return 0;
        }
    }

     public long getLong(int i) {

        if(this.currentRow!=null)
        {
            return this.currentRow.getColumns().get(i).getLongValue();
        }
        else
        {
            return 0L;
        }
    }
      public double getDouble(int i) {

        if(this.currentRow!=null)
        {
            return this.currentRow.getColumns().get(i).getDoubleValue();
        }
        else
        {
            return 0d;
        }
    }

    
    public int size() {
        return rows.size();
    }
}
