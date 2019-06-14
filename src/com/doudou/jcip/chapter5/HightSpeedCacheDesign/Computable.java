package com.doudou.jcip.chapter5.HightSpeedCacheDesign;

public interface Computable<A, V> {
    V compute(A arg) throws InterruptedException;
}
