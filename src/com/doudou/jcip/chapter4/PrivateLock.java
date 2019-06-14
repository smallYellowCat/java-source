package com.doudou.jcip.chapter4;

import net.jcip.annotations.GuardedBy;

/**
 * 使用私有锁保护状态的类
 * @author 豆豆
 * @date 2019/5/16 9:49
 * @flag 以万物智能，化百千万亿身
 */
public class PrivateLock {
    private final Object myLock = new Object();
    @GuardedBy("myLock") Widget widget = new Widget();

    void someMethod(){
        synchronized (myLock){
            String threadName = Thread.currentThread().getName();
            //访问或修改widget的状态
            String s = widget.getName();
            widget.setName("dpw");
            System.out.println(threadName  + " : " + s);
        }
    }

}


