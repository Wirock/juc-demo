package com.jucdemo.chapter2;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * CAS(Compare and Swap)是一种非阻塞原子性操作，JDK的Unsafe类提供了一系列的compareAndSwap*方法。
 * 这里以compareAndSwapLong为例，四个参数分别为：对象内存地址，对象中成员变量的偏移量，变量预期值，变量新值
 * 通过对象地址和偏移量找到要操作的变量，如果变量等于预期，更新为新值，返回是否更新成功的boolean类型
 *
 * 存在ABA问题
 * 变量由A变为B,再变为A,这个过程对CAS来说是不可见，CAS能够操作成功，但实际上变量已经经过两轮变化。
 *
 * JDK中的AtomicStampedReference类给每个变量的状态值都配备了一个时间戳，从而避免了ABA问题的产生
 *
 * @author chenzw
 * @date 2021/2/27
 */
public class UnsafeTest {
    //获取unsafe的实例，这里直接使用Unsafe.getUnsafe()会抛出SecurityException异常
    //为保证安全，不允许用户随意调用，这个方法中会判断调用的类是不是Boostrap加载器加载的，不是会抛出这个异常
    //而UnsafeTest是AppClassLoader加载的，故不能直接调用
    //static final Unsafe unsafe = Unsafe.getUnsafe();

    //可以使用反射来绕过这个校验
    static final Unsafe unsafe;
    //记录变量state在类UnsafeTest中的偏移值
    static final long stateOffset;

    //变量
    private volatile long state=0;

    static{
        try {
            //通过反射获取Unsafe实例
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe)field.get(null);

            stateOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        } catch (NoSuchFieldException e) {
            System.out.println(e.getLocalizedMessage());
            throw new Error(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new Error(e);
        }
    }

    public static void main(String[] args) {
        UnsafeTest test = new UnsafeTest();
        boolean b = unsafe.compareAndSwapLong(test, stateOffset, 0, 1);
        System.out.println(b);
    }
}
