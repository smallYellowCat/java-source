package com.doudou.jcip.chapter11;

import java.util.HashSet;

/**
 * @author 豆豆
 * @date 2019/6/14 15:59
 * @flag 以万物智能，化百千万亿身
 */
public class StripedMap {

    private static final int N_LOCAKS = 16;
    private final Node[] buckets;
    private final Object[] locks;

    private static class Node {
        Node next;
        Object key;
        Object value;
    }

    public StripedMap(int numBuckets){
        buckets = new Node[numBuckets];
        locks = new Object[numBuckets];
        for (int i = 0; i < N_LOCAKS; i++){
            locks[i] = new Object();
        }
    }

    private final int hash(Object key){
        return Math.abs(key.hashCode() % buckets.length);
    }

    public Object get(Object key){
        int hash = hash(key);
        synchronized (locks[hash % N_LOCAKS]){
            for (Node m = buckets[hash]; m != null; m = m.next){
                if (m.key.equals(key)){
                    return m.value;
                }
            }
        }
        return null;
    }


    public void clear(){
        for (int i = 0; i < buckets.length; i++){
            synchronized (locks[i % N_LOCAKS]){
                buckets[i] = null;
            }
        }
    }
}
