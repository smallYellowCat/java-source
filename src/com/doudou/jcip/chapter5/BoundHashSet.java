package com.doudou.jcip.chapter5;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 用信号量来约束容器，你可以使用Semaphore把任何容器转换成有界的阻塞容器。
 * <p>
 *     the Semaphore was initialized a max capacity of a collection.
 *     the add operation first access a permit before add entry into down-level collection
 * </>
 * @author 豆豆
 * @date 2019/5/21 10:01
 * @flag 以万物智能，化百千万亿身
 */
public class BoundHashSet<E> {

    private final Set<E> set;

    private final Semaphore sem;

    public BoundHashSet(int bound){
        this.set = Collections.synchronizedSet(new HashSet<>());
        this.sem = new Semaphore(bound);
    }

    public boolean add(E e) throws InterruptedException {
        sem.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(e);
            return wasAdded;
        }finally {
            if (!wasAdded){
                sem.release();
            }
        }
    }


    public boolean remove(Object o){
        boolean wasRemoved = set.remove(o);
        if (wasRemoved){
            //删除掉一个元素，释放一个许可
            sem.release();
        }
        return wasRemoved;
    }
}
