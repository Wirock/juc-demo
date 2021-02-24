package com.jucdemo.chapter1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程在睡眠时拥有的监视器资源不会被释放，在睡眠时被调用interrupt会抛出InterruptedException异常而中断
 * @author chenzw
 * @date 2021/2/24
 */
public class SleepTest {
    //创建一个独占锁
    public static final Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        Thread threaA = new Thread(()->{
            //获取独占锁
            lock.lock();
            try {
                System.out.println("child threadA is in sleep");
                Thread.sleep(10000);
                System.out.println("child threadA is awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //释放独占锁
                lock.unlock();
            }
        });
        Thread threaB = new Thread(()->{
            //获取独占锁
            lock.lock();
            try {
                System.out.println("child threadB is in sleep");
                Thread.sleep(10000);
                System.out.println("child threadB is awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //释放独占锁
                lock.unlock();
            }
        });

        Thread threadC = new Thread(()->{
            try {
                System.out.println("child threadC is in sleep");
                Thread.sleep(10000);
                System.out.println("child threadC is awake");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        threaA.start();
        threaB.start();

        threadC.start();
        Thread.sleep(2000);
        threadC.interrupt();
    }
}
