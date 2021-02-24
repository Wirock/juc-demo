package com.jucdemo.chapter1;

/**
 * @author chenzw
 * @date 2021/2/24
 */
public class WaitNotifyInterrupt {
    static Object obj = new Object();
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            System.out.println("-----begin----");
            synchronized (obj){
                try {
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("-----end-----");
        });
        thread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----begin interrupt thread----");
        thread.interrupt();
        System.out.println("----end interrupt thread----");
    }
}
