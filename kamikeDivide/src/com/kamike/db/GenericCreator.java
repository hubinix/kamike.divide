/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.db;

 
import com.kamike.db.generic.GenericCreate;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public abstract class GenericCreator<T> {

    protected Transaction ts;
    protected GenericCreate<T> create;

    protected GenericCreator(Transaction ts,String dbName) {

        this.ts = ts;
        create = newCreate();
        create.setSchema(dbName);

    }

    public boolean isExistDatabase() {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();

        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(create.databaseExistSQL());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

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
                Logger.getLogger(GenericCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return false;
    }

    public boolean isExistTable() {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();

        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(create.tableExistSQL());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

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
                Logger.getLogger(GenericCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return false;
    }

    public boolean init() {
        boolean ret = true;
        if (!this.isExistDatabase()) {
            ret = this.createDatabase();
        }
        if (!ret) {
            return ret;
        }

        if (!this.isExistTable()) {
            ret = this.createTable();
        }
        return ret;
    }
    
     public boolean init(String tableName) {
        boolean ret = true;
        if (!this.isExistDatabase()) {
            ret = this.createDatabase();
        }
        if (!ret) {
            return ret;
        }

        if (!this.isExistTable(tableName)) {
            ret = this.createTable(tableName);
        }
        return ret;
    }

    public boolean createDatabase() {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();

        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(create.createDatebaseSQL());

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
                Logger.getLogger(GenericCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public boolean createTable() {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();

        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(create.createTableSQL());

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
                Logger.getLogger(GenericCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public boolean isExistTable(String tableName) {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();

        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(create.tableExistSQL(tableName));

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            }

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
                Logger.getLogger(GenericCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return false;
    }

    public boolean createTable(String tableName) {
        if (ts == null) {
            return false;
        }
        // String uuid= UUID.randomUUID().toString();

        int success = 0;

        PreparedStatement ps = null;

        try {

            ps = ts.preparedStatement(create.createTableSQL(tableName));

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
                Logger.getLogger(GenericCreator.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public abstract GenericCreate<T> newCreate();
}
