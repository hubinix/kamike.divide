/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class KamiSelect extends KamiSQL {

    public KamiSelect(String tableName) {
        super(tableName);

    }

    @Override
    public void close() throws Exception {

        super.close();
    }

    public KamiResultSet executeSyncQuery() {
        KamiResultSet result = new KamiResultSet();

        try {
            //查询在哪个库里面的哪个表

            long start = System.nanoTime();

            ArrayList<KamiTable> tables = this.findTables();

            CountDownLatch doneSignal = new CountDownLatch(tables.size());

            for (KamiTable t : tables) {

                KamiStatement cur = this.kamiStatement.clone();
                cur.setRealTableName(t.getRealName());
                cur.setId(t.getDatabaseId());
                KamiQuery ss = new KamiQuery(doneSignal, cur, result);
                ss.execute();
            }
            doneSignal.await(waitSecond, TimeUnit.SECONDS);

            long elapsed = start - System.nanoTime();

            //assertTrue(elapsed <= 300l && elapsed < 500l);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(KamiSelect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public KamiResultSet executeQuery() {
        KamiResultSet result = new KamiResultSet();
        try {
            //查询在哪个库里面的哪个表

            long start = System.nanoTime();
            ArrayList<KamiTable> tables = this.findTables();
            ExecutorService exec = Executors.newCachedThreadPool();
            CountDownLatch doneSignal = new CountDownLatch(tables.size());

            for (KamiTable t : tables) {

                KamiStatement cur = this.kamiStatement.clone();
                cur.setRealTableName(t.getRealName());
                cur.setId(t.getDatabaseId());
                KamiQuery ss = new KamiQuery(doneSignal, cur, result);
                exec.execute(ss);
            }
            doneSignal.await(waitSecond, TimeUnit.SECONDS);
            exec.shutdownNow();

            long elapsed = start - System.nanoTime();

            //assertTrue(elapsed <= 300l && elapsed < 500l);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(KamiSelect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public KamiResultSet executeQuery(Date beginDate, Date endDate) {
        KamiResultSet result = new KamiResultSet();

        try {
            //查询在哪个库里面的哪个表

            long start = System.nanoTime();

            ArrayList<KamiTable> tables = this.findTables(beginDate, endDate);
            ExecutorService exec = Executors.newCachedThreadPool();
            CountDownLatch doneSignal = new CountDownLatch(tables.size());

            for (KamiTable t : tables) {

                KamiStatement cur = this.kamiStatement.clone();
                cur.setRealTableName(t.getRealName());
                cur.setId(t.getDatabaseId());
                KamiQuery ss = new KamiQuery(doneSignal, cur, result);
                exec.execute(ss);
            }
            doneSignal.await(waitSecond, TimeUnit.SECONDS);
            exec.shutdownNow();

            long elapsed = start - System.nanoTime();

            //assertTrue(elapsed <= 300l && elapsed < 500l);
            return result;
        } catch (InterruptedException ex) {
            Logger.getLogger(KamiSelect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public KamiResultSet executeQuery(long offset, int size) {
        KamiResultSet result = new KamiResultSet();

        try {
            //查询在哪个库里面的哪个表

            long start = System.nanoTime();

            ArrayList<KamiTable> tables = this.findTables();

            long sum = 0;

            for (KamiTable t : tables) {
                if (size < 1) {
                    break;
                }

                sum = sum + count(t.getId(), kamiStatement.getSql());

                if (sum > offset) {

                    KamiStatement cur = kamiStatement.clone();
                    cur.setSql(kamiStatement.getSql() + " limit " + offset + "," + size + " ");
                    cur.setRealTableName(t.getRealName());
                    cur.setId(t.getId());
                    KamiQuery ss = new KamiQuery(cur, result);
                    ss.execute();
                    size = size - result.size();
                }

            }

            long elapsed = start - System.nanoTime();

            //assertTrue(elapsed <= 300l && elapsed < 500l);
            return result;
        } catch (Exception ex) {
            Logger.getLogger(KamiSelect.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public long count(String dbId, String sql) {

        ResultSet rs = null;
        PreparedStatement ps = null;
        Connection conn = null;

        long ret = 0;
        try {
            conn = KamiDbInst.getInstance().getDatabase(dbId).getConnection();
            ps = conn.prepareStatement(countSQL(sql), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

            rs = ps.executeQuery();
            ret = count(rs);

        } catch (Exception ex) {
            ret = 0;
            Logger.getLogger(KamiSelect.class.getName()).log(Level.SEVERE, null, ex);

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
                Logger.getLogger(KamiSelect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ret;
    }

    private long count(ResultSet rs) throws SQLException {
        long sum = 0;
        if (rs == null) {
            return 0;
        }
        if (rs.next()) {
            sum = rs.getLong(1);
        }
        return sum;
    }

    public String countSQL(String sql) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("select  count(1) ");
        //Sql sql2 = Sqls.fetchLong("select count(1) FROM ("+sql+")");
        buffer.append(" from (");

        buffer.append(sql);

        buffer.append(" ) ___countsql ");

        return buffer.toString();
    }

    @Override
    public ArrayList<KamiTable> findTables() {
        KamiTableReader kamiTableReader;
        kamiTableReader = new KamiTableReader();
        ArrayList<KamiTable> ret = kamiTableReader.find(this.tableName);
        return ret;
    }

    public ArrayList<KamiTable> findTables(Date beginDate, Date endDate) {
        KamiTableReader kamiTableReader;
        kamiTableReader = new KamiTableReader();
        ArrayList<KamiTable> ret = kamiTableReader.find(this.tableName, beginDate, endDate);
        return ret;
    }

}
