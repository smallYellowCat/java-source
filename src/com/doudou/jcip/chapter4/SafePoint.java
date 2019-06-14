package com.doudou.jcip.chapter4;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * 可变的线程安全的类，私有构造器的存在避免了一些竞争条件，这些竞争条件会发生在复制构造
 * 函数实现为this(point.x, point.y)的时候，这是私有构造函数捕获模式的实例
 * @author 豆豆
 * @date 2019/5/16 14:10
 * @flag 以万物智能，化百千万亿身
 */
@ThreadSafe
public class SafePoint {

    @GuardedBy("this")
    private int x, y;

    private SafePoint(int[] a){
        this(a[0], a[1]);
    }

    public SafePoint(SafePoint point){
        this(point.get());
    }

    public SafePoint(int x, int y){
        this.x = x;
        this.y = y;
    }

    public synchronized int[] get(){
        //会一次性获取x和y的值，避免产生获取的x和y不对应的情况
        return new int[] {x, y};
    }

    public synchronized void set(int x, int y){
        this.x = x;
        this.y = y;
    }
}
