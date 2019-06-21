package com.doudou.jcip.chapter5.HightSpeedCacheDesign;

import java.math.BigInteger;

/**
 * @author 豆豆
 * @date 2019/5/21 9:47
 * @flag 以万物智能，化百千万亿身
 */
public class ExpensiveFunction implements Computable<String, BigInteger> {
    @Override
    public BigInteger compute(String arg) throws InterruptedException {
        //after deep thought...wa ha ha ha
        return new BigInteger(arg);
    }
}
