 
package com.kamike.message;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import java.util.concurrent.Executors;

/**
 * just for test
 * 
 * @author brin
 *  hubinix@gmail.com
 */
public class EventInst {

    private static volatile EventInst eventInst = new EventInst();
    
    private EventBus eventBus;
    private AsyncEventBus asyncEventBus;

    private EventInst() {
        eventBus=new EventBus();
        asyncEventBus = new AsyncEventBus(Executors.newFixedThreadPool(20));
    }

    public static EventInst getInstance() {

        return eventInst;
    }

    /**
     * @return the eventBus
     */
    public EventBus getEventBus() {
        return eventBus;
    }
     /**
     * @return the eventBus
     */
    public AsyncEventBus getAsyncEventBus() {
        return asyncEventBus;
    }

    
}
