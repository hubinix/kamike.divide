/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamike.divide;

import com.kamike.db.Transaction;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class KamiTransaction extends Transaction{

   
    public KamiTransaction(String id) {
        super();
        this.init(id);
    }
    @Override
     protected void init() {
         
    }
      protected void init(String id) {
        this.rollback = false;
        try {
            con = KamiDbInst.getInstance().getDatabase(id).getConnection();

            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(KamiTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
