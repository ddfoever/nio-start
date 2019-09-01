package com.karta.nio.chapter03;


import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.Map;
import java.util.Set;

/**
 * 字符集 CharSet  操作字符集防止io过程中乱码
 * Charset
 * 编码-》将字符转换成字节码
 * 解码-》将字节码转换成字符
 */
public class TestCharset {
    public static void main(String[] args) {
        TestCharset testCharset = new TestCharset();
//        testCharset.test1();
        testCharset.test2();
    }


    //获取目前支持的字符集
    private void test1(){
        Map<String,Charset> map = Charset.availableCharsets();
        Set<Map.Entry<String,Charset>> sets = map.entrySet();
        for(Map.Entry<String,Charset> ch:sets){
            System.out.println(ch.getValue());
        }
    }


    //
    private void test2(){
        Charset cs = Charset.forName("GBK");//设定一个字符集
        //获取编码器
        CharsetEncoder ce = cs.newEncoder();

        //获取解码器
        CharsetDecoder cd = cs.newDecoder();

        CharBuffer charBuffer = CharBuffer.allocate(1024);//初始化一个字符缓冲区

        charBuffer.put("我要努力生活,活出个样子来");
        charBuffer.flip();//切换读取模式
        //开始编码 就是把字符转为字节 通过编码器
        try {
            ByteBuffer bf = ce.encode(charBuffer);
            for(int i=0;i<25;i++){
                System.out.println(bf.get());
            }
            System.out.println("--------------------");
            System.out.println(new String(bf.array(),0,bf.limit()));


            //解码   就是从字节码转换成字符
            bf.flip();//切换读取模式
            CharBuffer cf = cd.decode(bf);
            System.out.println(cf.toString());

            //
            Charset cs1 = Charset.forName("utf-8");
//            CharsetDecoder cd1=cs1.newDecoder();
            bf.flip();
            CharBuffer cb = cs1.decode(bf);
            System.out.println(cb.toString());

        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }

    }
}
