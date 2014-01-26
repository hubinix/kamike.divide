/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamike.db;

 
import com.kamike.db.generic.GenericDelete;
import com.kamike.db.generic.GenericInsert;
import com.kamike.db.generic.GenericUpdate;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericWriter<T> {

    //Protected
   
    protected Transaction ts;

    protected GenericWriter(Transaction ts) {
        
        this.ts= ts;
    }
  
    public boolean add(T t) {
          if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();
        GenericInsert<T> insert = new GenericInsert<>(t);
        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(insert.sql());
            insert.bind(ps);
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
                Logger.getLogger(GenericWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

     
    public boolean delete(T t) {
          if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();
        GenericDelete<T> delete = new GenericDelete<>(t);
        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(delete.sql());
            delete.bind(ps);
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
                Logger.getLogger(GenericWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

 
    public boolean edit(T t) {
         
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();
        GenericUpdate<T> update = new GenericUpdate<>(t);
        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(update.sql());
            update.bind(ps);
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
                Logger.getLogger(GenericWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;

    }
  

}