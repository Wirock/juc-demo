package com.jucdemo.chapter1;

/**
 * ThreadLocal底层用来存数据的是Thread实例的ThreadLocalMap,存取值时通过当前的线程对象获取ThreadLocalMap，再用ThreadLocalMap来进行存取,所以只要不是同个线程，通过ThreadLocal获取的值就不一样，子线程无法获取父线程的值
 * @author chenzw
 * @date 2021/2/24
 */
public class ThreadLocalTest {
    static ThreadLocal<String> localVariable = new ThreadLocal<>();
    static void print(String str){
        //打印当前线程本地内存中localVariable变量的值
        System.out.println(str+":"+localVariable.get());
        //清除当前本地内存中的localVariable变量
        localVariable.remove();
    }

    public static void main(String[] args) {
        Thread threadOne = new Thread(()->{
            localVariable.set("threadOne local variable");
            print("threadOne");
            System.out.println("threadOne remove after:"+localVariable.get());
        });

        Thread threadTwo = new Thread(()->{
            localVariable.set("threadTwo local variable");
            print("threadTwo");
            System.out.println("threadTwo remove after:"+localVariable.get());
        });

        threadOne.start();
        threadTwo.start();
    }
}
