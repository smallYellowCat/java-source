package com.doudou.jvm.chapter2;

public class RuntineConstantPoolOOM {


    /**
     * 常量池测试
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/6/20 20:30a
    */
    public static void main(String[] args){
        //didn't  have this string in constant pool
        String s1 = new StringBuffer("Dream").append("Maker").toString();
        System.out.println(s1.intern() == s1);

        //already has this string in constant pool
        String s2 = new StringBuffer("ja").append("va").toString();
        System.out.println(s2.intern() == s2);
    }
}
