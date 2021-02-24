package com.jucdemo.chapter1;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author chenzw
 * @date 2021/2/24
 */
public class WaitAndNotifyTest {
    public static final int MAX_SIZE=10;
    public static Queue queue = new LinkedList();
    //生产线程
    public static class Producer extends Thread{
        @Override
        public void run() {
            synchronized (queue){
                while(queue.size()==MAX_SIZE){//使用while而不是if，防止虚假唤醒
                    try {
                        //挂起线程，释放queue的监视器锁，让消费线程可以获取该锁，消费元素
                        //wait后进入阻塞，只有两种情况会返回：
                        //1.其他线程调用了queue的notifyAll方法唤醒全部因queue阻塞的线程或调用notify方法刚好唤醒当前线程
                        //2.其他线程调用了该线程的interrupt方法，该线程抛出InterruptedException异常
                        queue.wait();//必须获取对象的监视器锁（即要在该对象的synchronized里），才能调用，否则会抛出IllegalMonitorStateException
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //空闲则生成元素，并通知消费线程消费
                queue.add(new Object());
                System.out.println("生产一个元素，还有"+queue.size());
                queue.notifyAll();
            }
        }
    }

    //消费线程
    public static class Consumer extends Thread{
        @Override
        public void run() {
            synchronized (queue){
                while(queue.size()==0){//使用while而不是if，防止虚假唤醒
                    try {
                        //挂起线程，释放queue的监视器锁，让生产线程可以获取该锁，生产元素
                        queue.wait();//必须获取对象的监视器锁（即要在该对象的synchronized里），才能调用，否则会抛出IllegalMonitorStateException
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //空闲则生成元素，并通知消费线程消费
                queue.poll();
                System.out.println("消费一个元素，还有"+queue.size());
                queue.notifyAll();
            }
        }
    }

    public static void main(String[] args) {
        for(int i=0;i<10;i++){
            new Producer().start();
            new Consumer().start();
        }
    }
}
