# nio-start
 ###五 直接缓冲区 与非直接缓冲区
 
 非直接缓冲区： 通过allocate()方法分配缓冲区，将缓冲区建立在JVM
 上
 ByteBuffer.allocate(1024);
 直接缓冲区：通过allocateDirect()方法分配缓冲区，将缓冲区建立在物理内存中。可以提高效率；
 ByteBuffer.allocateDirect(1024); 一旦断开就与应用程序没有任何关系，一般来说直接缓冲区的分配和取消分配开销比非直接大，
 所以建议将直接缓冲区分配给受基础系统的本机I/O操作影响比较大，持久的缓冲区，最好是性能上比较明显；
 
 
 
 可以通过isDirect（）来判断是否直接/非直接缓冲区；
 
 
 ###六通道（Channel）
 
通道主要实现类：

    1.FileChannel
    2.SocketChannel
    3.ServerSocketChannel
    4.DatagramChannel
    
获取通道：
    
    1.Java针对支持通道的类提供了getChannel()方法
        本地IO
        FileInputStream / FileOutputStream
        RandomAccessFile
        网络IO
        Socket
        ServerSocket
        DatagramSocket
    2.在JDK1.7中NIO.2针对各个通道提供了静态方法open()
    3.在JDK1.7中NIO.2的Flies工具类的newByteChannel()
    
以上有三种方法可以获取通道

通道之间的数据传输
    
    1.transferFrom
    2.transferTo
    
    


    
 