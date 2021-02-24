package com.jucdemo.chapter1;

/**
 * @author chenzw
 * @date 2021/2/24
 */
public class DeadLockTest {
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) {
        Thread threadA = new Thread(()->{
            try {
                synchronized (resourceA){
                    System.out.println("threadA get resourceA lock");
                    synchronized (resourceB){
                        System.out.println("threadA get resourceB lock");
                        System.out.println("threadA release resourceA lock");
                        resourceA.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadB = new Thread(()->{
            try {
                Thread.sleep(1000);//休眠1s，使threadA先执行
                synchronized (resourceA){
                    System.out.println("threadB get resourceA lock");
                    System.out.println("threadB try get resourceB lock...");
                    synchronized (resourceB){
                        System.out.println("threadB get resourceB lock");
                        System.out.println("threadB release resourceA lock");
                        resourceA.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
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
