/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.GenericWriter;
import com.kamike.divide.generic.KamiGenericDelete;
import com.kamike.divide.generic.KamiGenericInsert;
import com.kamike.divide.generic.KamiGenericUpdate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 * @param <T>
 */
public abstract class KamiWriter<T> {

    protected KamiWriter() {

    }

    public abstract KamiGenericInsert<T> createKamiGenericInsert(T t);

    public boolean add(T t) {

        // String uuid= UUID.randomUUID().toString();
        KamiGenericInsert<T> insert = createKamiGenericInsert(t);
        int success = 0;

        try {
            insert.prepareStatement(insert.sql());
            insert.bind();
            insert.executeUpdate();

        } catch (Exception e) {

            System.out.println(this.getClass().getName() + e.toString());
            return false;

        } finally {
            try {

                if (insert != null) {
                    insert.close();
                    insert = null;
                }

            } catch (Exception ex) {
                Logger.getLogger(GenericWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public boolean delete(T t) {

        // String uuid= UUID.randomUUID().toString();
        KamiGenericDelete<T> delete = new KamiGenericDelete<>(t);
        int success = 0;

        try {
            delete.prepareStatement(delete.sql());
            delete.bind();
            delete.executeUpdate();

        } catch (Exception e) {

            System.out.println(this.getClass().getName() + e.toString());
            return false;

        } finally {
            try {

                if (delete != null) {
                    delete.close();
                    delete = null;
                }

            } catch (Exception ex) {
                Logger.getLogger(GenericWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;
    }

    public boolean edit(T t) {

        // String uuid= UUID.randomUUID().toString();
        KamiGenericUpdate<T> update = new KamiGenericUpdate<>(t);
        int success = 0;

        try {
            update.prepareStatement(update.sql());
            update.bind();
            update.executeUpdate();

        } catch (Exception e) {

            System.out.println(this.getClass().getName() + e.toString());
            return false;

        } finally {
            try {

                if (update != null) {
                    update.close();
                    update = null;
                }

            } catch (Exception ex) {
                Logger.getLogger(GenericWriter.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return true;

    }
}
