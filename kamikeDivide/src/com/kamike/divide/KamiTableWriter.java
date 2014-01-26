/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.GenericWriter;
import com.kamike.db.Transaction;
 
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class KamiTableWriter extends GenericWriter<KamiTable> {

    private String tableName;

    public KamiTableWriter(Transaction ts) {
        super(ts);
        this.tableName = "t_kami_table";
    }

    public boolean increaseSize(String id) {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();
        String sql = "update  " + this.tableName + " set update_date=?,"
                + "current_size=current_size+1,"
                + "end_date=? "
                + " where id=? ";
        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(sql);

            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));

            ps.setString(3, id);

            success = ps.executeUpdate();

        } catch (Exception e) {

            ts.rollback();
            System.out.println(this.getClass().getName() + e.toString());
            return false;

        } finally {
            try {

                if (ps != null) {
                    ps.close();
                    ps = null;
                }

            } catch (SQLException ex) {
                Logger.getLogger(KamiTableWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public boolean closeFull() {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();
        String sql = "update  " + this.tableName + " set update_date=?,"
                + "end_date=?,closed=1 "
                + " where current_size>=bucket_size and closed=0";
        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(sql);

            ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));

            success = ps.executeUpdate();

        } catch (Exception e) {

            ts.rollback();
            System.out.println(this.getClass().getName() + e.toString());
            return false;

        } finally {
            try {

                if (ps != null) {
                    ps.close();
                    ps = null;
                }

            } catch (SQLException ex) {
                Logger.getLogger(KamiTableWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }
}
