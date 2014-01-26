/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.kami.console;

import java.util.concurrent.CountDownLatch;

/**
 *
 * @author THiNk
 */
public class CountDownEvent {
    private  CountDownLatch doneSignal;

    /**
     * @return the doneSignal
     */
    public CountDownLatch getDoneSignal() {
        return doneSignal;
    }

    /**
     * @param doneSignal the doneSignal to set
     */
    public void setDoneSignal(CountDownLatch doneSignal) {
        this.doneSignal = doneSignal;
    }
    
}
