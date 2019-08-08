package com.karta.nio.chapter01;

import java.nio.ByteBuffer;

/**
 *一.缓冲区Buffer, 在Java NIO中负责存储数据，而缓冲区其本质就是一个数组，用于存储不同数据类型的数据
 *
 */
public class TestBuffer {
    public static void main(String[] args) {
        TestBuffer testBuffer = new TestBuffer();
        testBuffer.Test1();

        //testBuffer.test2();
    }

    public void Test1(){
        String str = "abcde";
        //分配容量
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);//分配一个容量为1024字节的ByteBufffer
        System.out.println("---------------------------");
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());
        //存放数据 写数据模式
        byteBuffer.put(str.getBytes());
        System.out.println("------------put---------------");
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());

        //切换模式 将写模式切换到读模式 通过flip();
        byteBuffer.flip();//必须通过这个方法才能开始读
        System.out.println("------------flip---------------");
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());

        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes);
        System.out.println("------------get---------------");
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());
        //byteBuffer.get();
       //System.out.println("byteBuffer.get(); "+byteBuffer.get(0));
        byteBuffer.rewind();//position 恢复到读取之前的位置
        System.out.println("------------rewind---------------");
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());

        byteBuffer.clear();//清除缓存。但是不是真的清除，只是position和limit的位置发生变化，buffer里面的数据处于“被遗忘”的状态
        System.out.println("------------clear---------------");
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());
        byte[] byteCache = new byte[10];
        System.out.println("get char "+byteBuffer.get(byteCache,1,5));
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());

        byteBuffer.put("fghjk".getBytes());
        System.out.println("------------put twoice---------------");
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());

    }

    public void test2(){
        String str="abcde";
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        System.out.println("---------------------------");
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());
        byteBuffer.put(str.getBytes());
        System.out.println("----------put-----------------");
        System.out.println("position "+byteBuffer.position());
        System.out.println("limit "+byteBuffer.limit());
        System.out.println("capacity "+byteBuffer.capacity());
        //切换读取模式
        byteBuffer.flip();

        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes,0,2);//从第0个位置开始读 读长度为2
        System.out.println(new String(bytes));
        //mark 标记position 的位置
        byteBuffer.mark();
        byteBuffer.get(bytes,2,2);
        System.out.println(new String(bytes)+"-------"+new String(bytes,2,2));
        System.out.println(byteBuffer.position());
        //reset  使position恢复到mark的位置
        byteBuffer.reset();
        System.out.println(byteBuffer.position());
        //remaining 缓存中还剩下多少个数据
        if(byteBuffer.hasRemaining()){
            System.out.println(byteBuffer.remaining());
        }
    }

    public void test03(){
        ByteBuffer byteBuffer =ByteBuffer.allocate(1024);//创建的是基于JVM的非直接缓冲区 会从OS中把缓存copy 到JVM中然后在应用程序中使用
        ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(1024);//创建的基于物理内存页的缓冲区(抛弃了os到jvm的过程) 效率相对不较高，但有风险
    }

}
