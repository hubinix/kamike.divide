/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kami.console;

import com.kamike.db.generic.BaseReader;
import com.kamike.divide.KamiReader;
import com.kamike.divide.KamiResultSet;
import com.kamike.divide.generic.KamiGenericSelect;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class TestTableReader extends KamiReader<TestTable> {

    public TestTableReader() {
        super();
    }

    @Override
    public KamiGenericSelect<TestTable> createSelect() {

        return new TestTableSelect();
    }

    @Override
    public KamiGenericSelect<TestTable> createSelect(TestTable t) {
        return new TestTableSelect(t);
    }

    public ArrayList<TestTable> find(TestTable t) {
        KamiGenericSelect<TestTable> select = createSelect();

        KamiResultSet rs = null;

        ArrayList<TestTable> ret = null;
        try {

            select.prepareStatement(select.rawSql() + "where t.count=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            select.setLong(1, t.getCount());
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

}
