package com.karta.nio.chapter02;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 通道(Channel) 在Java NIO中只是用来传输数据用的，连接源节点与目标节点的连接，Channle本身不存储数据 需要配合缓冲区传输
 */
public class TesBuffer2 {

    public static void main(String[] args) {
        TesBuffer2 tesBuffer2 = new TesBuffer2();
       // tesBuffer2.test1();
//        try {
//            tesBuffer2.test2();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        tesBuffer2.test3();
    }

    //方法一 通过InputStream 和outputStream 获取Channel（buffer为非直接缓冲区）
    public void test1(){
        long start = System.currentTimeMillis();
        FileInputStream ins = null;
        FileOutputStream os = null;
        FileChannel insChannel = null;
        FileChannel ousChannel = null;
        try {
//            ins = new FileInputStream("D:/nio-start/nioDemo/1.jpg");
//            os = new FileOutputStream("D:/nio-start/nioDemo/2.jpg");
            ins = new FileInputStream("D:\\1.exe");
            os = new FileOutputStream("D:\\2.exe");
            //获取channel
            insChannel = ins.getChannel();
            ousChannel = os.getChannel();

            //创建buffer
            ByteBuffer buffer = ByteBuffer.allocate(1024);//创建分配buffer
            //读取buffer
            while (insChannel.read(buffer) != -1){//读完之后position limit capcity 没发生变化
                //切换读取模式
                buffer.flip();//limit 发生变化，limit=buffer的大小
                //开始写入buffer 到osChannel中
                ousChannel.write(buffer);//取数据position 发生变化.position = limit
                buffer.clear();//limit 和position 发生变化，position 归0，limit = capacity
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(ousChannel != null){
                try {
                    ousChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(insChannel != null){
                try {
                    insChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(ins != null){
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("spent time "+(end - start));
    }
    //通过直接缓冲区完成文件的复制（内存映射文件）
    //通过直接缓冲区完成文件操作，明显有效率提升
    // 瞬间完成 copy，可能JVM还没有来得及GC导致程序无法即时释放资源
    public  void test2() throws IOException{
        long start = System.currentTimeMillis();
        FileChannel inChannel = FileChannel.open(Paths.get("D:\\1.exe"),StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("D:\\2.exe"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

        //创建buffer 通过channel.map 的方式获取buffer 它和ByteBuffer.allocateDirect()原理一样，并且只有byteMappbuffer
        MappedByteBuffer inMap = inChannel.map(FileChannel.MapMode.READ_ONLY,0,inChannel.size());
        MappedByteBuffer outMap =outChannel.map(FileChannel.MapMode.READ_WRITE,0,inChannel.size());


        //因为channel是连接源节点和目标节点的，并且buffer 已通过channel获取 所以我们只要冲inbuffer 去数据放入 outmapBuffer 就可以了，channle会自动的传输数据写入
        byte[] bytes = new byte[inMap.limit()];
        //从buffermap中获取limit大小的数据 放入字节数组中
        inMap.get(bytes);
        //p放入数据
        outMap.put(bytes);
        outChannel.close();
        inChannel.close();
        long end = System.currentTimeMillis();
        System.out.println("spent time "+(end - start));
    }
    //通道之间的数据传输
    //transferFrom
    //transferTo
    //其原理也是通过直接缓冲区传送数据
    //效率比内存映射小点 高于非直接缓冲
    public void test3(){
        long start = System.currentTimeMillis();
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("D:\\1.exe"),StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("D:\\2.exe"),StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE);

            inChannel.transferTo(0,inChannel.size(),outChannel);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outChannel.close();
                inChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("spent time "+(end - start));
    }


}
