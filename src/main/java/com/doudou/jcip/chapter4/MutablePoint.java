package com.doudou.jcip.chapter4;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class MutablePoint {
    public int x, y;

    public MutablePoint(){
        x = 0;
        y = 0;
    }

    public MutablePoint(MutablePoint point){
        this.x = point.x;
        this.y = point.y;
    }
}
