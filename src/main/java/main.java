/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 02月 23日 10:07
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/

import canal.testMigrateMap;
import collect.MigrateMap;
import org.apache.commons.cli.*;
import org.apache.commons.lang.math.NumberUtils;
import sun.net.dns.ResolverConfiguration;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description:
 * @Date: 2024年 02月 23日 10:07
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) {
        String a = "你好,世界这么大我想去kankan";

//        System.out.println(NumberUtils.createBigDecimal(a).toBigInteger());

        byte[] bytes = new byte[0];
        bytes = a.getBytes(StandardCharsets.UTF_8);
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] < 0) {
                bytes[i] = (byte) (bytes[i] * -1);
            }
        }
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

}
