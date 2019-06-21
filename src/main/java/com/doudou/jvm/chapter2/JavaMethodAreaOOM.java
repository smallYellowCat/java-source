package com.doudou.jvm.chapter2;

import lombok.Setter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * VM Args: -XX:PermSize=10M -XX:MaxPermSize=10M
 *
 * When starting Java with "-XX:PermSize" and "-XX:MaxPermSize" on Linux and Mac, the following fatal error is issued:
 *
 * Unrecognized VM option 'MaxPermSize=384m'
 * Error: Could not create the Java Virtual Machine.
 * Error: A fatal exception has occurred. Program will exit.
 *
 * When starting on Windows, these options are ignored (as with JDK 8) and the Java application starts successfully.
 *
 * VM Args: -XX:MetaspaceSize=10M -XX:MaxMetaspaceSize=10M
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
           enhancer.create();

        }
    }

    static class OOMObject {


    }


}
