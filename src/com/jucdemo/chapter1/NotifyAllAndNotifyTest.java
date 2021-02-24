package com.jucdemo.chapter1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author chenzw
 * @date 2021/2/24
 */
public class NotifyAllAndNotifyTest {
    private static volatile Object resourceA = new Object();

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(()->{
            try {
                synchronized (resourceA){
                    System.out.println("threadA get resourceA lock");
                    System.out.println("threadA begin wait");
                    resourceA.wait();
                    System.out.println("threadA end wait");
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
                    System.out.println("threadB begin wait");
                    resourceA.wait();
                    System.out.println("threadB end wait");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread threadC = new Thread(()->{
                synchronized (resourceA){
                    System.out.println("threadC begin notify");
                    resourceA.notify();//只唤醒一个
                    //resourceA.notifyAll();//唤醒全部
                }
        });


        threadA.start();
        threadB.start();
        Thread.sleep(1000);
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();
        System.out.println("main over");
    }
}
