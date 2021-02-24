package com.jucdemo.chapter1;

/**
 * InheritableThreadLocal和ThreadLocal一样是Thread的成员变量，InheritableThreadLocal在构造时或判断父线程的InheritableThreadLocal是否存在，存在的话会把其中的值复制过来。故InheritableThreadLocal可以获取父线程的值
 * @author chenzw
 * @date 2021/2/24
 */
public class InheritableThreadLocalTest {
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        threadLocal.set("hello world");
        inheritableThreadLocal.set("hello world");

        new Thread(()->{
            System.out.println("thread threadLocal:"+threadLocal.get());
            System.out.println("thread inheritableThreadLocal:"+inheritableThreadLocal.get());
        }).start();

        System.out.println("main threadLocal:"+threadLocal.get());
        System.out.println("main inheritableThreadLocal:"+inheritableThreadLocal.get());
    }

}
