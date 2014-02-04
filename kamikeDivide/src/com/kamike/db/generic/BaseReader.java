/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.db.generic;

import com.kamike.db.GenericReader;
import com.kamike.db.DbInst;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 *
 * @author brin hubinix@gmail.com
 */
public abstract class BaseReader<T> extends GenericReader<T> {

    protected BaseReader() {

    }

    public abstract GenericSelect<T> createSelect();

    public abstract GenericSelect<T> createSelect(T t);

    @Override
    public long count() {
        GenericSelect<T> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        long ret = 0;
        try {
            conn = DbInst.getInstance().getDatabase().getSingleConnection();
            ps = conn.prepareStatement(select.countSQL(select.rawSql()), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = ps.executeQuery();
            ret = select.count(rs);

        } catch (Exception e) {
            ret = 0;
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(BaseReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    @Override
    public ArrayList<T> find() {

        GenericSelect<T> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        ArrayList<T> ret = null;
        try {
            conn = DbInst.getInstance().getDatabase().getSingleConnection();
            ps = conn.prepareStatement(select.rawSql(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = ps.executeQuery();
            ret = select.fetch(rs);

        } catch (Exception e) {
            ret = new ArrayList<>();
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(BaseReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;

    }

    public T get(T t) {
        GenericSelect<T> select = createSelect(t);

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        T entity = null;

        try {
            conn = DbInst.getInstance().getDatabase().getSingleConnection();
            ps = conn.prepareStatement(select.sql(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            int index = select.bind(ps);

            rs = ps.executeQuery();
            entity = select.fetchOnce(rs);

        } catch (Exception e) {
            entity = null;
            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(BaseReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return entity;
    }
}
