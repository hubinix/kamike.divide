/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class SerializableTransaction extends Transaction {

    public SerializableTransaction() {
        super();

        this.setTransactionIsolationLevel(Connection.TRANSACTION_SERIALIZABLE);

    }

    public void save() {
        try {

            if (this.rollback) {
                con.rollback();
            } else {
                con.commit();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SerializableTransaction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                this.resetTransactionIsolationLevel();
                if (con != null) {
                    con.setAutoCommit(true);

                    con.close();
                    con = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(SerializableTransaction.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * 如果忘记关闭连接池，那么对象自动销毁的时候，也需要归还链接
     */
    public void finalize() {

        try {

            if (con != null) {
                con.close();
                con = null;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SerializableTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(SerializableTransaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
