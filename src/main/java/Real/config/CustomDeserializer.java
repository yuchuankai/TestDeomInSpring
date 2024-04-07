/**
 * @Project: testDeomInSpring
 * @ClassName: CustomDeserializer
 * @Date: 2024年 03月 11日 11:14
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real.config;

import Real.dataBaseParams.Mysql;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * @Description:
 * @Date: 2024年 03月 11日 11:14
 * @Author: MR.Yu
 **/
public class CustomDeserializer implements ObjectDeserializer {
    @Override
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        JSONObject jsonObject = (JSONObject) parser.parse();
        return JSON.parseObject(jsonObject.toJSONString(), (Type) Mysql.class);
    }

    @Override
    public int getFastMatchToken() {
        return 0;
    }
}
