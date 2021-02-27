package com.jucdemo.chapter2;

/**
 * 1.相同点
 * synchronized和volatile都能保证内存可见性
 * 进入synchronize会把synchronized块中的用到的变量从线程工作内存中清空，重新从主内存中读取
 * 读取volatile修饰的变量，也会把该变量从线程工作内存中清空，重新从主内存中读取
 *
 * 退出synchronized块时会把写入工作内存中的值写入到主内存
 * 修改volatile修饰的变量，也会把改变量的值写入到主内存
 *
 * 2.不同点
 * synchronized还能保证原子性，synchronized块中的操作是原子性的
 * 而volatile不能保证原子性
 *
 * 如果写入变量依赖变量当前值，即需要 读取-计算-写入 这三步是原子操作时，不能使用volatile,而要用synchronized
 * 如果写入变量不依赖变量当前值，或只是单纯的读取变量，则使用volatile就足够，
 * synchronized是独占锁，会阻塞其他共享资源的线程，同时存在上下文切换和线程重新调度的开销。
 *
 * @author chenzw
 * @date 2021/2/26
 */
public class SynchronizedAndVolatileTest {
    //线程不安全
    public static class ThreadNotSafeInteger{
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    //synchronized保证线程安全
    public static class ThreadSafeInteger1{
        private int value;

        public synchronized int getValue() {
            return value;
        }

        public synchronized void setValue(int value) {
            this.value = value;
        }
    }
    //volatile保证可见性，单步操作本身具备原子性，进而保证线程安全
    public static class ThreadSafeInteger2{
        private volatile int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    //value++分为读取、计算、写入三步，volatile不保证原子性，此处线程不安全
    public static class ThreadNotSafeCount{
        private volatile int value;

        public int getValue() {
            return value;
        }

        public void inc() {
            this.value++;
        }
    }

    //synchronized保证原子性和可见性，线程安全
    public static class ThreadSafeCount{
        private int value;

        public synchronized int getValue() {
            return value;
        }

        public synchronized void inc() {
            this.value++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadNotSafeCount threadNotSafeCount = new ThreadNotSafeCount();
        ThreadSafeCount threadSafeCount = new ThreadSafeCount();
        for(int i=0;i<1000;i++){
            new Thread(()->{
                threadNotSafeCount.inc();
            }).start();
        }
        for(int i=0;i<1000;i++){
            new Thread(()->{
                threadSafeCount.inc();
            }).start();
        }
        Thread.sleep(3000);
        System.out.println(threadNotSafeCount.getValue());
        System.out.println(threadSafeCount.getValue());
    }
}
