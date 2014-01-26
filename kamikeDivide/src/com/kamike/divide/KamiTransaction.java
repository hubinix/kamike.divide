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

    private String id;
    public KamiTransaction(String id) {
        super();
        this.id=id;
    }
    @Override
     protected void init() {
        this.rollback = false;
        try {
            con = KamiDbInst.getInstance().getDatabase(id).getConnection();

            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(KamiTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
