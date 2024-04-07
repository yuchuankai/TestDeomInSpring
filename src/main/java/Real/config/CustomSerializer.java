/**
 * @Project: testDeomInSpring
 * @ClassName: CustomSerializer
 * @Date: 2024年 03月 11日 10:57
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real.config;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Description:
 * @Date: 2024年 03月 11日 10:57
 * @Author: MR.Yu
 **/
public class CustomSerializer implements ObjectSerializer {
    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        System.out.println(serializer.out);
        System.out.println(object);
        System.out.println(fieldName);
        System.out.println(fieldType);
        System.out.println(features);
    }
}
