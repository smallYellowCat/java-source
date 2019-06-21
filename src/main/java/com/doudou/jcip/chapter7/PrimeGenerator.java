package com.doudou.jcip.chapter7;

import net.jcip.annotations.GuardedBy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * 素数生成
 * @author 豆豆
 * @date 2019/5/24 9:40
 * @flag 以万物智能，化百千万亿身
 */
public class PrimeGenerator implements Runnable {

    @GuardedBy("this")
    private final List<BigInteger> primes = new ArrayList<>();

    private volatile boolean cancelled;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {

        BigInteger p = BigInteger.ONE;
        while (!cancelled){
            p = p.nextProbablePrime();
            synchronized (this){
                primes.add(p);
            }
        }

    }

    public void cancel(){cancelled = true;}

    public synchronized List<BigInteger> get(){
        return new ArrayList<>(primes);
    }
}


/**
 * 素数生成器运行一秒
 * @author 豆豆
 * @date 2019/5/24 9:55
 * @flag 以万物智能，化百千万亿身
 */
class Test{
    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/5/24 9:41a
    */
    public static void main(String[] args){
        try {
            System.out.println(aSecondOfPrimes());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    static List<BigInteger> aSecondOfPrimes() throws InterruptedException {
        PrimeGenerator primeGenerator = new PrimeGenerator();
        new Thread(primeGenerator).start();
        try {
            SECONDS.sleep(1);
        }finally {
            primeGenerator.cancel();
        }

        return primeGenerator.get();
    }
}
