package com.doudou.jvm.chapter2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 豆豆
 * @date 2019/6/19 19:58
 * @flag 以万物智能，化百千万亿身
 */
public class HeapOOM {

    static class OOMObject{}


    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/6/19 19:58a
    */
    public static void main(String[] args){
        List<OOMObject> list = new ArrayList<>();
        while (true){
            list.add(new OOMObject());
        }
    }
}
