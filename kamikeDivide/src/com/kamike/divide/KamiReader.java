/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.DbInst;
import com.kamike.db.generic.BaseReader;
import com.kamike.db.generic.GenericSelect;
import com.kamike.divide.generic.KamiGenericSelect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public abstract class KamiReader<T> {

    protected KamiReader() {

    }

    public abstract KamiGenericSelect<T> createSelect();

    public abstract KamiGenericSelect<T> createSelect(T t);

    public long count() {
        KamiGenericSelect<T> select = createSelect();

        KamiResultSet rs = null;

        long ret = 0;
        try {
            select.prepareStatement(select.countSQL(select.rawSql()), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = select.executeQuery();
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
                if (select != null) {
                    select.close();
                    select = null;
                }

            } catch (Exception ex) {
                Logger.getLogger(BaseReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    public ArrayList<T> find() {

        KamiGenericSelect<T> select = createSelect();

        KamiResultSet rs = null;

        ArrayList<T> ret = null;
        try {
            select.prepareStatement(select.rawSql(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = select.executeQuery();
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
                if (select != null) {
                    select.close();
                    select = null;
                }
            } catch (Exception ex) {
                Logger.getLogger(BaseReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;

    }

    public T get(T t) {

        T entity = null;
        KamiGenericSelect<T> select = createSelect();

        KamiResultSet rs = null;

        try {
            select.prepareStatement(select.sql(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            select.bind();
            rs = select.executeQuery();
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
                if (select != null) {
                    select.close();
                    select = null;
                }
            } catch (Exception ex) {
                Logger.getLogger(BaseReader.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return entity;
    }
}
