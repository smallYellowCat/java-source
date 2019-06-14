package com.doudou.jcip.chapter4;


import net.jcip.annotations.Immutable;

/**
 * Point类是不可变的，因而是线程安全的，程序可以自由地共享与发布不可变值，所以
 * 返回location时不必再复制它们
 * @author 豆豆
 * @date 2019/5/16 11:24
 * @flag 以万物智能，化百千万亿身
 */
@Immutable
public class Point {

    public final int x, y;

    public Point(int x, int y){
        this.x = x;
        this.y = y;
    }
}
