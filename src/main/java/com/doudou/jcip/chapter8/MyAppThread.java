package com.doudou.jcip.chapter8;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Custom thread base class</p>
 * @author 豆豆
 * @date 2019/6/3 10:26
 * @flag 以万物智能，化百千万亿身
 */
public class MyAppThread extends Thread {

    public static final String DEFAULT_NAME = "MyAppThread";

    private static volatile boolean debugLifecycle = false;
    private static final AtomicInteger created = new AtomicInteger();
    private static final AtomicInteger alive = new AtomicInteger();
    private static final Logger log = Logger.getAnonymousLogger();

    public MyAppThread(Runnable runnable){
        this(runnable, DEFAULT_NAME);
    }

    public MyAppThread(Runnable runnable, String name){
        super(runnable, name + "_" + created.incrementAndGet());
        setUncaughtExceptionHandler((Thread t, Throwable e) -> {
            log.log(Level.SEVERE, "UNCAUGHT in thread " + t.getName(), e);
        });
    }

    @Override
    public void run() {
        //super.run();

        //Copy debug flag to ensure consistent value throughout.
        boolean debug = debugLifecycle;
        if (debug) log.log(Level.FINE, "Created " + getName());
        try {
            alive.incrementAndGet();
            super.run();
        }finally {
            alive.decrementAndGet();
            if (debug) log.log(Level.FINE, "Exiting " + getName());
        }
    }

    public static int getThreadsCreated(){
        return created.get();
    }

    public static int getThreadsAlive(){
        return alive.get();
    }

    public static boolean getDebug(){
        return debugLifecycle;
    }

    public static void setDebug(boolean b){
        debugLifecycle = b;
    }
}
