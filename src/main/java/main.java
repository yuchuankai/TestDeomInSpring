/**
 * @Project: testDeomInSpring
 * @ClassName: FileChange
 * @Date: 2024年 02月 23日 10:07
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/

import canal.testMigrateMap;
import collect.MigrateMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.cli.*;
import org.apache.commons.lang.math.NumberUtils;
import sun.net.dns.ResolverConfiguration;
import task.GlobalParam;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @Date: 2024年 02月 23日 10:07
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args){
        test2();
    }

    public static void test3() {
        LinkedBlockingQueue<Consumer<String>> list = new LinkedBlockingQueue<>();

        // 添加一些 Consumer<String> 对象到队列中
        list.add(a -> System.out.println("处理1: " + a));
        list.add(a -> System.out.println("处理2: " + a));
        list.add(a -> System.out.println("处理3: " + a));

        // 定义一个输入字符串
        String input = "测试字符串";
        input.hashCode();

        try {
            // 遍历队列并执行每个 Consumer<String> 对象
            while (!list.isEmpty()) {
                Consumer<String> consumer = list.take();
                consumer.accept(input);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("线程被中断：" + e.getMessage());
        }
    }

    public static void test2(){
        LinkedBlockingQueue<Consumer<String>> list = new LinkedBlockingQueue<>();

        try {
            list.put( a -> {
                System.out.println("处理3: " + a);
            });
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        // 定义一个输入字符串
        String input = "测试字符串";

        try {
            // 遍历队列并执行每个 Consumer<String> 对象
            while (!list.isEmpty()) {
                Consumer<String> consumer = list.take();
                consumer.accept(input);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("线程被中断：" + e.getMessage());
        }

    }

    public static void test1(){
        String w1 = new StringBuilder("你好").append("世界").toString();
        String w2 = "你好世界";
        String w3 = new String("你好世界");
//
        System.out.println(w3.intern() == w2);
//        System.out.println(w3.intern() == w1.intern());

        String str2 = new StringBuilder("ja").append("va").toString();
        String str3 = "java";
        String str4 = new String("java");
//        String str5 = "java";
//        String str6 = new String("java");

        System.out.println(str2.intern() == str3);
//        System.out.println(str2.intern() == str3);
//        System.out.println(str2 == str4.intern());
//        System.out.println(str4.intern() == str3);
//        System.out.println(str3 == str5);
//        System.out.println(str4.intern() == str6.intern());


        boolean isFailed = false;
        System.out.println(Math.round(1.5));
    }
}
