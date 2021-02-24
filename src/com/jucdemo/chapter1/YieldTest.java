package com.jucdemo.chapter1;

/**
 * yield和sleep的区别是yield不阻塞，只是让出时间片，回到就绪状态。而sleep是阻塞指定时间
 * @author chenzw
 * @date 2021/2/24
 */
public class YieldTest {
    public static class ThreadA extends Thread{
        @Override
        public void run() {
            for(int i=0;i<5;i++){
                if(i%5==0){
                    System.out.println(Thread.currentThread()+" yield cpu...");
                    Thread.yield();//当前线程让出CPU执行权，放弃时间片，进行下一轮调度
                }
            }
            System.out.println(Thread.currentThread()+" is over");
        }
    }

    public static void main(String[] args) {
        new ThreadA().start();
        new ThreadA().start();
        new ThreadA().start();
    }
}
