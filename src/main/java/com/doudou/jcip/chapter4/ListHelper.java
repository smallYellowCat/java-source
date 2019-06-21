package com.doudou.jcip.chapter4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 客户端加锁实现的“缺少即加入”
 * @author 豆豆
 * @date 2019/5/16 14:39
 * @flag 以万物智能，化百千万亿身
 */
public class ListHelper<E> {

    public List<E> list = Collections.synchronizedList(new ArrayList<>());

    public boolean putIfAbsent(E x){
        synchronized (this){
            boolean absent = !list.contains(x);
            if (absent){
                list.add(x);
            }
            return absent;
        }
    }
}
