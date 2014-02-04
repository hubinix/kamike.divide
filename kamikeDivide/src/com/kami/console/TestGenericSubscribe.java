/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kami.console;

import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.Subscribe;

import com.kamike.db.Transaction;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class TestGenericSubscribe {

    public TestGenericSubscribe(AsyncEventBus asyncEventBus) {
        asyncEventBus.register(this);
    }

    @Subscribe
    @AllowConcurrentEvents
    public void handleEventConcurrent(CountDownLatch signal) {
        try {
            Date date = new Date(System.currentTimeMillis());
            TestTable test = new TestTable();
            test.setCount(signal.getCount());
            test.setCreateDate(date);
            test.setId(UUID.randomUUID().toString());
            test.setName("创建测试=" + UUID.randomUUID().toString());
           
            TestTableWriter ttw = new TestTableWriter();

            ttw.add(test);
            
        } catch (Exception ex) {
            Logger.getLogger(TestGenericSubscribe.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            signal.countDown();
        }

    }
}
