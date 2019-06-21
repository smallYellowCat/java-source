package com.doudou.jvm.chapter2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * VM Args: -Xmx20M -XX:MaxDirectMemorySize=10M
 *
 * 使用 Unsafe 分配本机内存
 * @author 豆豆
 * @date 2019/6/21 14:30
 * @flag 以万物智能，化百千万亿身
 */
public class DirectMemoryOOM {
    private static final int _1MB = 1024 *1024;

    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/6/21 14:31a
    */
    public static void main(String[] args) throws IllegalAccessException {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true){
            unsafe.allocateMemory(_1MB);
        }
    }
}
