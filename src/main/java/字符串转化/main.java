/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 05月 06日 14:23
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 字符串转化;

import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Date: 2024年 05月 06日 14:23
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String a = "你好";

        byte[] bytes = a.getBytes("GBK");
//        for (int i = 0; i < bytes.length; i++) {
//            if (bytes[i] < 0) {
//                bytes[i] = (byte) (bytes[i] * -1);
//            }
//        }
        System.out.println(bytes.length);
        System.out.println(new String(bytes, "GBK"));
    }
}
