/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kamike.divide;

import com.kamike.db.GenericCreator;
import com.kamike.db.Transaction;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.commons.lang.time.FastDateFormat;

/**
 *
 * @author THiNk
 */
public abstract class KamiInsert<T> extends KamiSQL {

    private int copyNum = 1;
    private static volatile ReentrantLock lock = new ReentrantLock();

    public KamiInsert(String tableName) {
        super(tableName);
        this.copyNum = 1;

    }
     public KamiInsert() {
        super();
    }

    public KamiInsert(String tableName, int copyNum) {
        super(tableName);
        this.copyNum = copyNum;
    }

    public abstract GenericCreator<T> creator(Transaction ts,String dbName);
    @Override
    public ArrayList<KamiTable> findTables() {

        //以上将所有满的表都关闭
        ArrayList<KamiTable> ret = new ArrayList<>();
        Date now = new Date(System.currentTimeMillis());
        KamiDatabaseReader databaseReader = new KamiDatabaseReader();

        KamiTableReader tableReader = new KamiTableReader();

        Transaction ts = new Transaction();
        KamiTableWriter tableWriter = new KamiTableWriter(ts);
        tableWriter.closeFull();
        ts.save();
        ts=null;
        ArrayList<KamiTable> tables = tableReader.findForInsert(this.tableName, copyNum);

        if (tables.isEmpty()) {
            //所有表都关闭了，需要新建N个表
            //下面创建表
            lock.lock();
            try {

                ArrayList<KamiTable> confirmTables = tableReader.findForInsert(this.tableName, copyNum);
                if (confirmTables.isEmpty()) {

                    ArrayList<KamiDatabase> databases = databaseReader.findBalanced(copyNum);
                    for (KamiDatabase db : databases) {
                        KamiTable t = new KamiTable();
                        t.setId(UUID.randomUUID().toString());
                        t.setAlive(true);
                        t.setBeginDate(now);
                        t.setExpectedSize(50000);
                        t.setCreateDate(now);
                        t.setClosed(false);
                        t.setCurrentSize(1);
                        t.setDatabaseId(db.getId());
                        t.setEndDate(now);

                        t.setName(this.tableName);
                        t.setDatabaseType(db.getType());
                        t.setRealName(this.tableName + "_" + FastDateFormat.getInstance("yyyyMMddHHmmss").format(now));
                        t.setUpdateDate(now);

                        //在所有分库里面创建表              
                        Transaction createTableTranscation = new KamiTransaction(db.getId());
                        GenericCreator<T> tableCreator = creator(createTableTranscation, db.getDbName());
                        tableCreator.init(t.getRealName());
                        createTableTranscation.save();
                        ret.add(t);
                        

                        //将更新数据库的写入时间，供负载均衡使用
                        Transaction localTs = new Transaction();
                        //在本地库写入元数据表
                        KamiTableWriter createTableWriter = new KamiTableWriter(localTs);
                        createTableWriter.add(t);
                        KamiDatabaseWriter databaseWriter = new KamiDatabaseWriter(localTs);
                        db.setUpdateDate(now);
                        databaseWriter.edit(db);
                        localTs.save();
                        databaseWriter=null;
                        localTs=null;
                    }

                } else {
                    for (KamiTable confirmTable : confirmTables) {
                        Transaction addts = new Transaction();
                        KamiTableWriter addTableWriter = new KamiTableWriter(addts);
                        addTableWriter.increaseSize(confirmTable.getId());
                        addts.save();
                        addts = null;
                        addTableWriter = null;

                        ret.add(confirmTable);

                    }
                }
            } finally {
                lock.unlock();
            }
        } else {

            //目前是正常情况
            for (KamiTable confirmTable : tables) {

                Transaction addts = new Transaction();
                KamiTableWriter addTableWriter = new KamiTableWriter(addts);
                addTableWriter.increaseSize(confirmTable.getId());
                addts.save();
                addts = null;
                addTableWriter = null;

                ret.add(confirmTable);

            }

        }

        return ret;
    }

}
