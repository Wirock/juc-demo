package com.jucdemo.chapter1;

/**
 * 死锁产生具备四个条件：
 * 1.互斥条件，即资源在一个时刻只能由一个线程占有
 * 2.请求并持有，即线程占有了至少一个资源，同时又请求新的资源
 * 3.不可剥夺，即线程一旦占有资源，在释放前资源无法被其他线程抢占
 * 4.环路等待，即两个线程分别占有对方请求的资源，相互等待
 *
 * 其中，只有请求并持有和环路等待两个条件是可破坏的，避免死锁从这两个条件入手,破坏其中一个即可
 * @author chenzw
 * @date 2021/2/24
 */
public class DeadLockTest {
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(()->{
                synchronized (resourceA){
                    System.out.println("threadA get resourceA lock");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("threadA waiting for resourceB lock");
                    synchronized (resourceB){
                        System.out.println("threadA get resourceB lock");
                    }
                }
        });

        Thread threadB = new Thread(()->{
            synchronized (resourceB){
                System.out.println("threadB get resourceB lock");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadB waiting for resourceA lock");
                synchronized (resourceA){
                    System.out.println("threadB get resourceA lock");
                }
            }
        });

        threadA.start();
        threadB.start();

        //等待两个线程结束后，再打印main over
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main over");
    }
}
