package com.jucdemo.chapter1;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 三种创建线程的方法
 * @author chenzw
 * @date 2021/2/23
 */
public class ThreadTest {
    //1.继承Thread并重写run方法,在run方法中获取当前线程可以用this
    public static class MyThread extends Thread{
        private String threadName;
        private Integer number;

        public void setThreadName(String threadName) {
            this.threadName = threadName;
        }

        public void setNumber(Integer number) {
            this.number = number;
        }

        @Override
        public void run() {
            System.out.println("I am a child thread:"+threadName+number);
        }
    }

    //2.实现Runnable接口的run方法,需构造为Thread对象来启动线程，在run方法中获取当前线程要用Thread.currentThread()
    public static class RunnableTask implements Runnable{
        private String threadName;
        private int number;
        public void setThreadName(String threadName) {
            this.threadName = threadName;
        }
        public void setNumber(int number) {
            this.number = number;
        }

        @Override
        public void run() {
            System.out.println("I am a child thread:"+threadName+number);
        }
    }

    //3.实现Callable接口的call方法,有返回值，需以FutureTask的方式来获取返回值
    public static class CallableTask implements Callable<String>{

        @Override
        public String call() throws Exception {
            return "hello";
        }
    }


    public static void main(String[] args) {
        String name = "A";
        int number = 1;
        MyThread myThread = new MyThread();
        myThread.setThreadName(name);
        myThread.setNumber(number);
        myThread.start();
        //2
        RunnableTask runnableTask = new RunnableTask();
        runnableTask.setThreadName(name);
        runnableTask.setNumber(number);
        new Thread(runnableTask).start();
        //name="b";//使用匿名内部类实现Runnable需保证主线程传入的参数不变（final or effectively final）
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("I am a child thread:"+name+number);
            }
        }).start();

        //3
        FutureTask<String> futureTask = new FutureTask<>(new CallableTask());
        new Thread(futureTask).start();
        try {
            System.out.println(futureTask.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
