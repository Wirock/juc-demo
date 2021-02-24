package com.jucdemo.chapter1;

/**
 * 中断方法只是设置线程的中断标志位true。线程实际没有被中断，还会继续走下去，需要线程根据中断状态自行处理
 * void interrupt()方法，设置线程的中断标志位true，如果线程处于阻塞状态（调用wait,join,sleep）,会抛出InterruptedException异常
 * boolean isInterrupted()方法，检查当前线程是否中断，返回中断标志,不清除中断标志，底层调用isInterrupted(false),私有的本地方法，参数false,不清除中断标志（private native boolean isInterrupted(boolean ClearInterrupted)）
 * static boolean interrupted()方法，返回当前线程中断标志，会清除中断标志，底层调用isInterrupted(false)。与boolean isInterrupted()不同的是：这个方法是静态的，可以用Thread调用，获取的是当前线程Thread.currentThread()的标志。
 * @author chenzw
 * @date 2021/2/24
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        //1.使用iterrupted优雅退出
        /*Thread threadA = new Thread(()->{
            try {
                int works = 10000;
                System.out.println("start working");
                while(!Thread.interrupted()&&works>0){
                    works--;
                    System.out.println("do more work,remain works:"+works);
                }
            } finally {
                System.out.println("clean up");
            }
        });
        threadA.start();
        Thread.sleep(50);
        threadA.interrupt();*/



        //2.通过interrupt强制sleep中的线程抛出InterruptedException异常而返回，避免阻塞时间过长造成浪费
        /*Thread threadOne = new Thread(()->{
            try {
                System.out.println("threadOne begin sleep for 2000 seconds");
                Thread.sleep(2000000);
                System.out.println("threadOne awaking");
            } catch (InterruptedException e) {
                System.out.println("threadOne is interrupted while sleeping");
            }
            System.out.println("threadOne leaving normally");
        });
        threadOne.start();
        threadOne.sleep(1000);
        threadOne.interrupt();

        threadOne.join();
        System.out.println("main is over");*/


        //3.interrupted和isInterrupted方法的不同之处
        Thread threadOne = new Thread(()->{
            for(;;);
        });
        //启动线程
        threadOne.start();
        //设置中断标志
        threadOne.interrupt();
        //获取中断标志
        System.out.println("isInterrupted:"+threadOne.isInterrupted());
        Thread.currentThread().interrupt();
        //获取中断标志并重置
        System.out.println("interrupted:"+threadOne.interrupted());//注意这里同Thread.interrupted()，获取和重置的是main线程的标志
        //获取中断标志并重置
        System.out.println("interrupted:"+Thread.interrupted());
        //获取中断标志
        System.out.println("isInterrupted:"+threadOne.isInterrupted());

        threadOne.join();
        System.out.println("main is over");
    }

}
