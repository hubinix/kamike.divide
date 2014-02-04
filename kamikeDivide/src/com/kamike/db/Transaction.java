/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamike.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class Transaction {
    protected Connection con;

    

    protected boolean rollback;

    protected int originalTransactionIsolationLevel;

    public Transaction() {

      init();
        
    }

    protected void init() {
        this.rollback = false;
        try {
            con = DbInst.getInstance().getDatabase().getSingleConnection();

            con.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void setTransactionIsolationLevel(int transactionIsolationLevel) {
        try {
            if (con != null) {
                DatabaseMetaData dbmt = con.getMetaData();
                if (dbmt.supportsTransactions()) {
                    if (dbmt.supportsTransactionIsolationLevel(transactionIsolationLevel)) {

                        originalTransactionIsolationLevel = con.getTransactionIsolation();
                        con.setTransactionIsolation(transactionIsolationLevel);
                    }
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void resetTransactionIsolationLevel() {
        try {
            if (con != null) {
                DatabaseMetaData dbmt = con.getMetaData();
                if (dbmt.supportsTransactions()) {
                    if (dbmt.supportsTransactionIsolationLevel(originalTransactionIsolationLevel)) {
                        con.setTransactionIsolation(originalTransactionIsolationLevel);
                    }
                }

            }

        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        try {
            if (this.rollback) {
                con.rollback();
            } else {
                con.commit();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {

                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                    con = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(Transaction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return the ps
     */
    public PreparedStatement preparedStatement(String sql) throws SQLException {

        return con.prepareStatement(sql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
    }

    /**
     * @param rollback the rollback to set
     */
    public void rollback() {
        this.rollback = true;
    }
}
