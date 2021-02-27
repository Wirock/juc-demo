package com.jucdemo.chapter2;

/**
 * 这段代码由于num变量没有加volatile修饰，存在指令重排序，readThread的输出有概率是0
 * volatile变量可确保volatile写之前的操作不会被编译器重排序到volatile写之后，volatile读之后的操作不会被重排到volatile读之前
 * @author chenzw
 * @date 2021/2/27
 */
public class InsResortTest {
    private static int num = 0;
    private static boolean ready = false;

    public static class ReadThread extends Thread {
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                if (ready) {
                    System.out.println(num + num);
                }
                System.out.println("read thread...");
            }
        }
    }

    public static class WriteThread extends Thread {
        @Override
        public void run() {
            num = 2;
            ready = true;//可能与num=2发生重排序，使读线程输出0
            System.out.println("write thread set over...");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadThread readThread = new ReadThread();
        WriteThread writeThread = new WriteThread();
        readThread.start();
        writeThread.start();
        Thread.sleep(1);
        readThread.interrupt();
        System.out.println("main over");
    }
}