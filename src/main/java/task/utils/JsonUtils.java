/**
 * @Project: testDeomInSpring
 * @ClassName: JsonUtils
 * @Date: 2024年 02月 26日 15:48
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task.utils;

import com.alibaba.fastjson.JSON;

/**
 * @Description:
 * @Date: 2024年 02月 26日 15:48
 * @Author: MR.Yu
 **/
public class JsonUtils {



    public static <T> T jobFromString(String json, Class<T> targetClass) {
        return (T) JSON.parseObject(json, targetClass);// 默认为UTF-8
    }


    public static String stringFromJob(Object targetClass) {
        return  JSON.toJSONString(targetClass);
    }
}
