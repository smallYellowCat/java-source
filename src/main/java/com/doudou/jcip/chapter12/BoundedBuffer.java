package com.doudou.jcip.chapter12;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.Semaphore;

/**
 * <p>Use semaphore achieve a bounded cache, this is just an example.
 * in practice, you should use ArrayBlockingQueue or LinkedBlockingQueue
 * other than produce by yourself when you need a bounded cache.</p>
 * @author 豆豆
 * @date 2019/6/17 18:35
 * @flag 以万物智能，化百千万亿身
 */
public class BoundedBuffer<E> {

    private final Semaphore availableItems, availableSpaces;

    @GuardedBy("this")
    private final E[] items;

    @GuardedBy("this")
    private int putPosition = 0, takePosition = 0;

    @SuppressWarnings("unchecked")
    public BoundedBuffer(int capacity){
        availableItems = new Semaphore(0);
        availableSpaces = new Semaphore(capacity);
        items = (E[]) new Object[capacity];
    }

    public boolean isEmpty(){
        return availableItems.availablePermits() == 0;
    }

    public boolean isFull(){
        return availableSpaces.availablePermits() == 0;
    }

    /**
     *
     * @param x
     * @throws InterruptedException
     */
    public void put(E x) throws InterruptedException {
        availableSpaces.acquire();
        doInsert(x);
        availableItems.release();
    }

    public E take() throws InterruptedException {
        availableItems.acquire();
        E item = doExtract();
        availableSpaces.release();
        return item;
    }

    private synchronized void doInsert(E x){
        int i = putPosition;
        items[i] = x;
        putPosition = (++i == items.length)? 0 : i;
    }

    private synchronized E doExtract(){
        int i = takePosition;
        E x = items[i];
        items[i] = null;
        takePosition = (++i == items.length)? 0 : i;
        return x;
    }

}
