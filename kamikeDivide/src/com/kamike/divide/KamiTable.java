/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kamike.divide;

import com.kamike.db.generic.FieldLength;
import com.kamike.db.generic.FieldName;
import com.kamike.db.generic.Id;
import com.kamike.db.generic.TableName;
import java.util.Date;

/**
 *
 * @author THiNk
 */
@TableName("t_kami_table")
public class KamiTable {
     @Id
    @FieldName("id")
    @FieldLength(64)
    private String id;

     
    @FieldName("name")
    @FieldLength(255)
    private String name;
    @FieldName("real_name")
    @FieldLength(255)
    private String realName;

    @FieldName("create_date")
    private Date createDate;

    @FieldName("update_date")
    private Date updateDate;

    @FieldName("database_id")
    @FieldLength(255)
    private String databaseId;

    

    @FieldName("database_type")
    @FieldLength(255)
    private String databaseType;

    @FieldName("expected_size")
    private long expectedSize;
    
    @FieldName("current_size")
    private long currentSize;

    @FieldName("closed")
    private boolean closed;

    @FieldName("begin_date")
    private Date beginDate;

    @FieldName("end_date")
    private Date endDate;

    @FieldName("alive")
    private boolean alive;

    
   
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

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the realName
     */
    public String getRealName() {
        return realName;
    }

    /**
     * @param realName the realName to set
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * @return the createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate the createDate to set
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return the updateDate
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * @param updateDate the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * @return the databaseId
     */
    public String getDatabaseId() {
        return databaseId;
    }

    /**
     * @param databaseId the databaseId to set
     */
    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }

   

    /**
     * @return the databaseType
     */
    public String getDatabaseType() {
        return databaseType;
    }

    /**
     * @param databaseType the databaseType to set
     */
    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    /**
     * @return the expectedSize
     */
    public long getExpectedSize() {
        return expectedSize;
    }

    /**
     * @param expectedSize the expectedSize to set
     */
    public void setExpectedSize(long expectedSize) {
        this.expectedSize = expectedSize;
    }

    /**
     * @return the currentSize
     */
    public long getCurrentSize() {
        return currentSize;
    }

    /**
     * @param currentSize the currentSize to set
     */
    public void setCurrentSize(long currentSize) {
        this.currentSize = currentSize;
    }

    /**
     * @return the closed
     */
    public boolean isClosed() {
        return closed;
    }

    /**
     * @param closed the closed to set
     */
    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    /**
     * @return the beginDate
     */
    public Date getBeginDate() {
        return beginDate;
    }

    /**
     * @param beginDate the beginDate to set
     */
    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the alive
     */
    public boolean isAlive() {
        return alive;
    }

    /**
     * @param alive the alive to set
     */
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
