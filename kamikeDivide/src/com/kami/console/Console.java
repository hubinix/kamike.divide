/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kami.console;

import com.kami.misc.SysInit;
import com.kamike.config.SystemConfig;
import com.kamike.db.GenericCreator;

import com.kamike.db.Transaction;
import com.kamike.message.EventInst;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author THiNk
 */
public class Console {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            // TODO code application logic here
            SysInit sys = new SysInit();
            sys.startup();
            //查询一下初始化连接池
            Transaction ts = new Transaction();
            GenericCreator<TestTable> creator = new TestTableCreator(ts, SystemConfig.SYSDBNAME);
            creator.init();
            ts.save();

            //查询测试
            TestTableReader tts = new TestTableReader();
            TestTable template = new TestTable();
            template.setCount(500);
            ArrayList<TestTable> testList = tts.find(template);
            System.out.println(tts.count());
            Transaction testTs = new Transaction();
            TestTableWriter ttw = new TestTableWriter(testTs);

            for (TestTable test : testList) {
                System.out.println(test.getName() + ":" + test.getCreateDate());
                test.setName("修改修改测试");
                ttw.edit(test);

            }
            testTs.save();

           //修改完毕
            ArrayList<TestTable> testList2 = tts.find(template);
            for (TestTable test : testList2) {

                System.out.println(test.getName() + ":" + test.getCreateDate());

            }

            TestGenericSubscribe subscriber = new TestGenericSubscribe(EventInst.getInstance().getAsyncEventBus());
            //date是时间戳
            int num = 5000;
            //同一盘下的桶目录
            long start = System.currentTimeMillis();

            CountDownLatch doneSignal = new CountDownLatch(num);

            for (int i = 0; i < num; i++) {

                EventInst.getInstance().getAsyncEventBus().post(doneSignal);

            }
            System.out.println(doneSignal.getCount());
            doneSignal.await();
            long elapsed = System.currentTimeMillis() - start;

            System.out.println(num / (elapsed / 1000));
            // System.out.print(file.getName());
            sys.close();
        } catch (Exception ex) {
            Logger.getLogger(Console.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
