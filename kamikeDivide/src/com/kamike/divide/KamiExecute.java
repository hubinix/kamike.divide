/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.generic.GenericColumn;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class KamiExecute implements Runnable {

    private CountDownLatch doneSignal;
    private KamiStatement kamiStatement;

    public KamiExecute(KamiStatement kamiStatement, CountDownLatch doneSignal) {
        this.doneSignal = doneSignal;
        this.kamiStatement = kamiStatement;
    }

    public void execute() {

        PreparedStatement ps = null;
        Connection conn = null;

        try {

            conn = KamiDbInst.getInstance().getDatabase(kamiStatement.getId()).getConnection();
            ps = conn.prepareStatement(kamiStatement.getSql(), kamiStatement.getResultSetOption1(), kamiStatement.getResultSetOption2());
            ArrayList<GenericColumn> columns = kamiStatement.getColumns();
            int i = 1;
            for (Iterator<GenericColumn> it = columns.iterator(); it.hasNext();) {
                GenericColumn column = it.next();
                switch (column.getType()) {
                    case Int:
                        ps.setInt(i, column.getIntValue());
                        break;
                    case Long:
                        ps.setLong(i, column.getLongValue());
                        break;
                    case Double:
                        ps.setDouble(i, column.getDoubleValue());
                        break;
                    case Boolean:
                        ps.setBoolean(i, column.getBooleanValue());
                        break;
                    case Timestamp:
                        ps.setTimestamp(i, column.getTimestampValue());
                        break;
                    case String:
                        ps.setString(i, column.getStrValue());
                        break;
                }
                i++;
            }

            int sum = ps.executeUpdate();

        } catch (Exception e) {

            System.out.println(this.getClass().getName() + e.toString());

        } finally {
            try {

                if (ps != null) {
                    ps.close();
                    ps = null;
                }
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (SQLException ex) {
                Logger.getLogger(KamiExecute.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        doneSignal.countDown();
    }

    @Override
    public void run() {

        this.execute();
    }

}
