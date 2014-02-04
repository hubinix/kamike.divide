/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.generic.GenericColumn;
import com.kamike.db.generic.GenericType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
public abstract class KamiSQL implements AutoCloseable {

    protected KamiStatement kamiStatement;
    protected String tableName;

    protected static final String prefix = "__prefix_kami_table_";

    protected int waitSecond;

    public abstract ArrayList<KamiTable> findTables();

    /**
     * @return the waitSecond
     */
    public int getWaitSecond() {
        return waitSecond;
    }

    /**
     * @param waitSecond the waitSecond to set
     */
    public void setWaitSecond(int waitSecond) {
        this.waitSecond = waitSecond;
    }

    public KamiSQL(String tableName) {
        this.tableName = tableName;
        kamiStatement = new KamiStatement(tableName);
        this.waitSecond = 60;
    }

    public KamiSQL() {

    }

    public void init() {
         
        kamiStatement = new KamiStatement(tableName);
        this.waitSecond = 60;
    }

    public void executeUpdate() {

        try {
            //查询在哪个库里面的哪个表

            long start = System.nanoTime();

            ArrayList<KamiTable> tables = this.findTables();
            ExecutorService exec = Executors.newCachedThreadPool();
            CountDownLatch doneSignal = new CountDownLatch(tables.size());

            for (KamiTable t : tables) {
                KamiStatement cur = kamiStatement.clone();
                cur.setRealTableName(t.getRealName());
                cur.setId(t.getDatabaseId());
                KamiExecute ce = new KamiExecute(cur, doneSignal);
                exec.execute(ce);
            }
            doneSignal.await(waitSecond, TimeUnit.SECONDS);
            exec.shutdownNow();

            long elapsed = start - System.nanoTime();

            //assertTrue(elapsed <= 300l && elapsed < 500l);
        } catch (InterruptedException ex) {
            Logger.getLogger(KamiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String TableName() {
        return kamiStatement.getMarkedTableName();
    }

    @Override
    public void close() throws Exception {
        kamiStatement.close();
    }

    /**
     * 如果忘记关闭连接池，那么对象自动销毁的时候，也需要归还链接
     */
    @Override
    public void finalize() {
        try {
            super.finalize();
        } catch (Throwable ex) {
            Logger.getLogger(KamiSQL.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void preparedStatement(String sql, int i, int i1) throws SQLException {
        kamiStatement.setSql(sql);
        kamiStatement.setResultSetOption1(i);
        kamiStatement.setResultSetOption2(i1);
    }

    public void preparedStatement(String sql) throws SQLException {
        kamiStatement.setSql(sql);
        kamiStatement.setResultSetOption1(ResultSet.TYPE_SCROLL_SENSITIVE);
        kamiStatement.setResultSetOption2(ResultSet.CONCUR_UPDATABLE);
    }

    public void setString(int i, String value) {
        GenericColumn column = new GenericColumn();
        column.setStrValue(value);
        column.setType(GenericType.String);

        kamiStatement.getColumns().add(i, column);

    }

    public void setInt(int i, int value) {
        GenericColumn column = new GenericColumn();
        column.setIntValue(value);
        column.setType(GenericType.Int);
        kamiStatement.getColumns().add(i, column);

    }

    public void setColumn(int i, GenericColumn column) {

        kamiStatement.getColumns().add(i, column);

    }

    public void setTimestamp(int i, Timestamp value) {
        GenericColumn column = new GenericColumn();
        column.setTimestampValue(value);
        column.setType(GenericType.Timestamp);
        kamiStatement.getColumns().add(i, column);

    }

    public void setLong(int i, long value) {
        GenericColumn column = new GenericColumn();
        column.setLongValue(value);
        column.setType(GenericType.Long);
        kamiStatement.getColumns().add(i, column);

    }

    public void setBoolean(int i, boolean value) {
        GenericColumn column = new GenericColumn();
        column.setBooleanValue(value);
        column.setType(GenericType.Boolean);
        kamiStatement.getColumns().add(i, column);

    }

    public void setDouble(int i, double value) {
        GenericColumn column = new GenericColumn();
        column.setDoubleValue(value);
        column.setType(GenericType.Double);
        kamiStatement.getColumns().add(i, column);

    }
}
