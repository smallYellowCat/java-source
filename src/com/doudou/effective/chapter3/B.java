package com.doudou.effective.chapter3;

public class B extends A {
    @Override
    public void say() {
        super.say();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        return super.clone();
    }
}
