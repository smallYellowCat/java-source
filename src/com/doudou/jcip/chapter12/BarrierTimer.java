package com.doudou.jcip.chapter12;

/**
 * 基于栅栏的计时器
 * @author 豆豆
 * @date 2019/6/18 19:53
 * @flag 以万物智能，化百千万亿身
 */
public class BarrierTimer implements Runnable {

    private boolean started;

    private long startTime, endTime;


    @Override
    public synchronized void run() {
        long t = System.nanoTime();
        if (!started){
            started = true;
            startTime = t;
        }else {
            endTime = t;
        }
    }


    public synchronized void clear(){
        started = false;
    }

    public synchronized long getTime(){
        return endTime - startTime;
    }
}
