/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamike.divide;

import com.kamike.db.GenericWriter;
import com.kamike.db.Transaction;

/**
 *
 * @author THiNk
 */
public class KamiDatabaseWriter extends GenericWriter<KamiDatabase>{
   public  KamiDatabaseWriter(Transaction ts)
   {
       super(ts);
   }
}
