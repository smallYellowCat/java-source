package com.doudou.effective.chapter3;

public class A implements Cloneable{
   private String s;

   public A(){
       this.s = "default s";
   }

   public A(String s){
       this.s = s;
   }

   public void say(){
       System.out.println(s);
   }

}
