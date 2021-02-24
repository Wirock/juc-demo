package com.jucdemo.chapter1;

/**
 * @author chenzw
 * @date 2021/2/24
 */
public class JoinTest {

    public static void main(String[] args) {
        //线程1
        Thread threadOne = new Thread(()->{
            System.out.println("threadOne begin run");
            for(;;);
        });
        //获取主线程
        Thread mainThread = Thread.currentThread();
        //线程2
        Thread threadTwo = new Thread(()->{
            System.out.println("threadTwo begin run");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mainThread.interrupt();
        });

        threadOne.start();
        threadTwo.start();

        try {
            //调用了其他线程的join方法，当前线程会阻塞，等待被调用线程结束，才会继续执行
            //当前线程在此阻塞期间，如果有线程调用了它的interrupt方法，会抛出InterruptedException异常而返回
            threadOne.join();
        } catch (InterruptedException e) {
            System.out.println("mainThread:"+e);
        }
    }
}
