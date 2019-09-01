package com.karta.nio.chapter03;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.BatchUpdateException;

/**
 * 分散读取与聚集写入
 */
public class TestNio3 {
    public static void main(String[] args) {
        TestNio3 testNio3 = new TestNio3();
        testNio3.test1();
    }

    //分散写入 将多个buffer按照顺序依次写入到一个channle中
    private void test1(){
        try {
            //创建一个随机访问文件类，RandomAccessFile 是java中最为丰富的文件访问类，提供了众多的文件访问方法
            //这里的随机访问 在于RandomAccessFile 能够跳转到文件的任意位置处读写数据
            RandomAccessFile raf = new RandomAccessFile("D:\\nio-start\\nioDemo\\1.txt","rw");
            FileChannel channel1 = raf.getChannel();
            //创建两个buffer 存放数据
            ByteBuffer byteBuffer1 = ByteBuffer.allocate(100);
            ByteBuffer byteBuffer2 = ByteBuffer.allocate(1024);
            byteBuffer1.flip();//
            ByteBuffer[] byteBuffers = {byteBuffer1,byteBuffer2};
            //分散读取数据
            try {
                channel1.read(byteBuffers);//采用通道读取数据到不同的buffer中
                //切换buffer读取模式
                for(ByteBuffer byteBuffer:byteBuffers){
                    byteBuffer.flip();
                }
//                byteBuffer1.flip();
//                byteBuffer2.flip();
                System.out.println(new String(byteBuffers[0].array(),0,byteBuffers[0].limit()));
                System.out.println(new String(byteBuffers[1].array(),0,byteBuffers[1].limit()));
                System.out.println("------------------------------------------");
                //聚集写入  按照buffer顺序依次写入channel
                RandomAccessFile raf2 = new RandomAccessFile("D:\\nio-start\\nioDemo\\2.txt","rw");
                //获取通道
                FileChannel channel2 = raf2.getChannel();
                //开始写入 按照buffer的顺序依次写入到channle

                channel2.write(byteBuffers);

                //关闭channel
                channel1.close();
                channel2.close();


            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
