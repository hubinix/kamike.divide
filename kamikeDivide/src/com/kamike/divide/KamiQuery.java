/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.generic.GenericColumn;
import com.kamike.db.generic.GenericType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class KamiQuery implements Runnable {

    private CountDownLatch doneSignal;
    private KamiResultSet result;
    private KamiStatement cs;
    private ArrayList<GenericType> types;

    public KamiQuery(CountDownLatch doneSignal, KamiStatement cs, KamiResultSet result) {
        this.cs = cs;
        this.doneSignal = doneSignal;
        this.result = result;
        this.types = new ArrayList<>();

    }

    public KamiQuery(KamiStatement cs, KamiResultSet result) {
        this.cs = cs;
        this.result = result;
        this.types = new ArrayList<>();

    }

    public void fillResultTypes(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();

        for (int i = 1; i <= rsmd.getColumnCount(); i++) {
            int type = rsmd.getColumnType(i);
            String name = rsmd.getColumnName(i);
            switch (type) {
                case Types.BIGINT:
                    types.add(GenericType.Long);
                    break;
                case Types.BIT:
                case Types.BOOLEAN:
                case Types.INTEGER:
                case Types.TINYINT:
                case Types.SMALLINT:
                    types.add(GenericType.Int);
                    break;
                case Types.CHAR:
                case Types.CLOB:
                case Types.LONGNVARCHAR:
                case Types.LONGVARCHAR:
                case Types.NCHAR:
                case Types.NCLOB:
                case Types.VARCHAR:
                    types.add(GenericType.String);
                    break;
                case Types.DATE:
                case Types.TIME:
                case Types.TIMESTAMP:
                    types.add(GenericType.Timestamp);
                    break;
                case Types.DECIMAL:
                case Types.DOUBLE:
                case Types.FLOAT:
                case Types.NUMERIC:
                    types.add(GenericType.Double);
                default:
                    types.add(GenericType.String);
                    break;
            }

        }

    }

    public void execute() {

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;
        KamiRow entity = null;

        try {
            conn = KamiDbInst.getInstance().getDatabase(cs.getId()).getConnection();
            ps = conn.prepareStatement(cs.getSql(), cs.getResultSetOption1(), cs.getResultSetOption2());
            ArrayList<GenericColumn> columns = cs.getColumns();
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

            rs = ps.executeQuery();
            //下面解析recordset的metadata
            this.fillResultTypes(rs);
            while (rs.next()) {
                entity = new KamiRow();

                i = 1;
                for (Iterator<GenericType> it = types.iterator(); it.hasNext();) {
                    GenericType type = it.next();
                    GenericColumn column = new GenericColumn();
                    column.setType(type);
                    switch (type) {
                        case Int:

                            column.setIntValue(rs.getInt(i));

                            break;
                        case Long:
                            column.setLongValue(rs.getLong(i));
                            break;
                        case Double:
                            column.setDoubleValue(rs.getDouble(i));

                            break;
                        case Boolean:
                            column.setBooleanValue(rs.getInt(i) > 0);

                            break;
                        case Timestamp:
                            column.setTimestampValue(rs.getTimestamp(i));

                            break;
                        case String:
                            column.setStrValue(rs.getString(i));
                            break;
                    }
                    i++;
                    entity.getColumns().add(column);
                }
                if (result != null) {
                    result.add(entity);
                }

            }

        } catch (Exception e) {

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
                Logger.getLogger(KamiQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        doneSignal.countDown();
    }

    /**
     * @return the result
     */
    public KamiResultSet getResult() {
        return result;
    }

    @Override
    public void run() {
        execute();
    }

}
