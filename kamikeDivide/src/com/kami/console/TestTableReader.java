/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kami.console;

import com.kamike.db.SysDbInst;
import com.kamike.db.generic.BaseReader;
import com.kamike.db.generic.GenericSelect;
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
public class TestTableReader extends BaseReader<TestTable> {

    public TestTableReader() {
        super();
    }

    @Override
    public GenericSelect<TestTable> createSelect() {

        return new TestTableSelect();
    }

    @Override
    public GenericSelect<TestTable> createSelect(TestTable t) {
        return new TestTableSelect(t);
    }

  

    @Override
    public ArrayList<TestTable> find(TestTable t) {
        GenericSelect<TestTable> select = createSelect();

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        ArrayList<TestTable> ret = null;
        try {
            conn = SysDbInst.getInstance().getDatabase().getSingleConnection();
            ps = conn.prepareStatement(select.rawSql() + "where t.count=? ", ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ps.setLong(1, t.getCount());
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

}
