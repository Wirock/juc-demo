package com.jucdemo.chapter2;

/**
 * 缓存时按行从主内存中读取的，缓存一个变量时，会把变量所在内存中大小为缓存行的内存放入缓存行
 * 单一线程对缓存行的修改不会使缓存行失效。 对于二维数组，行内存地址是相邻的，按行操作更容易命中缓存，速度会更快
 * @author chenzw
 * @date 2021/2/27
 */
public class CacheTest {
    static final int LINE_NUM = 1024;
    static final int COLUMN_NUM = 1024;
    public static void test1(){
        long[][] array = new long[LINE_NUM][COLUMN_NUM];
        long startTime = System.currentTimeMillis();
        for(int i=0;i<LINE_NUM;i++){
            for(int j=0;j<COLUMN_NUM;j++){
                array[i][j] = i*2+j;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("cache time:"+(endTime-startTime));
    }
    public static void test2(){
        long[][] array = new long[LINE_NUM][COLUMN_NUM];
        long startTime = System.currentTimeMillis();
        for(int i=0;i<COLUMN_NUM;i++){
            for(int j=0;j<LINE_NUM;j++){
                array[j][i] = i*2+j;
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("no cache time:"+(endTime-startTime));
    }

    public static void main(String[] args) {
        test1();
        test2();
    }
}
