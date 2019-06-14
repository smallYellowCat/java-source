package com.doudou.jcip.chapter5;

import java.util.concurrent.CountDownLatch;

/**
 * @author 豆豆
 * @date 2019/5/20 20:10
 * @flag 以万物智能，化百千万亿身
 */
public class TestHarness {

    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++){
            Thread t = new Thread(){
                @Override
                public void run() {
                    //
                    try {
                        startGate.await();
                        try {
                            task.run();
                        }finally {
                            endGate.countDown();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            };
            t.start();
        }

        long start = System.nanoTime();
        startGate.await();
        long end = System.nanoTime();
        return end - start;
    }
}
