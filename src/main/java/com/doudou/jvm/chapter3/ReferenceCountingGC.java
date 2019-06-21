package com.doudou.jvm.chapter3;

/**
 * <p>
 * 引用计数算法判断对象是否存活的缺陷
 * 开启GC日志:
 *  1. 标准输出到控制台：-XX:+PrintGCDetails -XX:+PrintGCDateStamps。
 *  2. 输出到文件：-Xloggc:filename.log
 *
 * 从控制台的GC日志发生了[936K->0K(76288K)]侧面证明了虚拟机并不是使用引用计数来进行垃圾回收的。
*  </p>
 * @author 豆豆
 * @date 2019/6/21 14:52
 * @flag 以万物智能，化百千万亿身
 */
public class ReferenceCountingGC {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;

    /**此成员存在的唯一意义是占点内存，以便能再GC日志中看清楚是否被回收过*/
    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGC(){
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();

        objA.instance = objB;
        objB.instance = objA;

        objA = null;
        objB = null;

        //假设在这行发生GC，objA和objB是否能被回收
        System.gc();
    }

    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/6/21 15:03a
    */
    public static void main(String[] args){
        testGC();
    }

}
