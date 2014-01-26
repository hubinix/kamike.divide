/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.generic.GenericColumn;
import java.util.ArrayList;

/**
 *
 * @author THiNk
 */
public class KamiStatement implements Cloneable, AutoCloseable {

    private String sql;
    private int resultSetOption1;
    private int resultSetOption2;
    private ArrayList<GenericColumn> columns;

    private final String tableName;
    private String id;
    private String markedTableName;
    private static final String prefix = "__kami_statement_";
    private String realTableName;

    public KamiStatement(String tableName) {
        this.tableName = tableName;

        this.realTableName = tableName;
        this.markedTableName = prefix + tableName;
        columns = new ArrayList<>();
    }

    @Override
    public KamiStatement clone() {
        KamiStatement ks = new KamiStatement(tableName);
        ks.setSql(sql);
        ks.setResultSetOption1(resultSetOption1);
        ks.setResultSetOption2(resultSetOption2);
        ks.setColumns(columns);
        return ks;
    }

    /**
     * @return the sql
     */
    /**
     * @return the sql
     */
    public String getSql() {
        return sql.replaceAll(markedTableName, realTableName);
    }

    public String getMarkedTableName() {
        return markedTableName;

    }

    /**
     * @param sql the sql to set
     */
    public void setSql(String sql) {
        this.sql = sql;
    }

    /**
     * @return the resultSetOption1
     */
    public int getResultSetOption1() {
        return resultSetOption1;
    }

    /**
     * @param resultSetOption1 the resultSetOption1 to set
     */
    public void setResultSetOption1(int resultSetOption1) {
        this.resultSetOption1 = resultSetOption1;
    }

    /**
     * @return the resultSetOption2
     */
    public int getResultSetOption2() {
        return resultSetOption2;
    }

    /**
     * @param resultSetOption2 the resultSetOption2 to set
     */
    public void setResultSetOption2(int resultSetOption2) {
        this.resultSetOption2 = resultSetOption2;
    }

    /**
     * @return the realTableName
     */
    public String getRealTableName() {
        return realTableName;
    }

    /**
     * @param realTableName the realTableName to set
     */
    public void setRealTableName(String realTableName) {
        this.realTableName = realTableName;
    }

    @Override
    public void close() throws Exception {
        this.columns.clear();
    }

    /**
     * @return the columns
     */
    public ArrayList<GenericColumn> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(ArrayList<GenericColumn> columns) {
        this.columns = columns;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

}
