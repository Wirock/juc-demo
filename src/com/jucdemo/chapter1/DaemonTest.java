package com.jucdemo.chapter1;

/**
 * java中线程分两类，用户线程和守护线程
 * 当所用用户线程运行完时，JVM会退出，而守护线程不影响JVM的退出。
 * @author chenzw
 * @date 2021/2/24
 */
public class DaemonTest {
    public static void main(String[] args) {
        Thread daemonThread = new Thread(()->{
            for(;;);
        });
        daemonThread.setDaemon(true);
        daemonThread.start();
        System.out.println("main is over");
    }
}
