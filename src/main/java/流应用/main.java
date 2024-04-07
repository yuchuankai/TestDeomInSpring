/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 04月 07日 15:56
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 流应用;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 * @Date: 2024年 04月 07日 15:56
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) {
        List<String> list = Arrays.asList("1","2","3","4","5");

        List<String> collect = list.stream().map(s -> s + "1").collect(Collectors.toList());
        collect.stream().forEach(System.out::println);
    }
}
