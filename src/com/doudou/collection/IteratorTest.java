package com.doudou.collection;

import java.util.*;

/**
 * @author 豆豆
 * @date 2019/4/12 12:46
 * @flag 以万物智能，化百千万亿身
 */
public class IteratorTest {
    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/4/12 12:46a
    */
    public static void main(String[] args){
        List<String> collection = new ArrayList<>();
        collection.add("a");
        collection.add("b");
        collection.add("c");
        collection.add("d");
        collection.add("e");
        collection.add("b");
        Collections.rotate(collection, 1);
        //int i = collection.subList(2,4).indexOf("d");
        List<String> strings = collection.subList(2,4);
        collection.add("f");
        //strings.remove(0);
        //System.out.println(i);
        System.out.println("*****");
    }
}
