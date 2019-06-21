package com.doudou.jvm.chapter2;

import lombok.Setter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 * @author 豆豆
 * @date 2019/6/20 20:48
 * @flag 以万物智能，化百千万亿身
 */
public class JavaMethodAreaOOM {

    /**
     *入口函数，自动生成
     * @author 豆豆
     * @date 2019/6/21 10:58a
    */
    public static void main(String[] args){
        while (true){
            Enhancer enhancer = new Enhancer();
            enhancer.setUseCache(false);
            enhancer.setSuperclass(OOMObject.class);
            enhancer.setCallback(new MethodInterceptor(){

                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                    return methodProxy.invokeSuper(obj, args);
                }
            });
        }
    }

    static class OOMObject{

    }


}
