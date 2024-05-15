/**
 * @Project: testDeomInSpring
 * @ClassName: MapToJson
 * @Date: 2024年 04月 17日 10:30
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Map转JSON;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import task.Column;

import java.util.*;

/**
 * @Description:
 * @Date: 2024年 04月 17日 10:30
 * @Author: MR.Yu
 **/
public class MapToJson {

    private final static String READER_NAME = "job.content[0].reader.name";
    private final static String READER_COLUMN = "job.content[0].reader.parameter.column";
    private final static String READER_JDBC_URL = "job.content[0].reader.parameter.connection[0].jdbcUrl";
    private final static String WRITER_NAME = "job.content[1].writer.name";
    private final static String WRITER_COLUMN = "job.content[1].writer.parameter.column";
    private final static String WRITER_JDBC_URL = "job.content[1].writer.parameter.connection[0].jdbcUrl";
    private final static String CHANNEL  = "job.setting.speed.channel";
    private final static String BYTES  = "job.setting.speed.bytes";
    public static void main(String[] args) {
        Map<String, Object> jobMap = new HashMap<>();
        jobMap.put(READER_NAME, "mysqlReader");
        List<Column> readerColumns = new ArrayList<>();
        Column column = new Column();
        column.setName("id");
        column.setIndex(0);
        column.setType("int");
        Column column1 = new Column();
        column1.setName("name");
        column1.setIndex(1);
        column1.setType("varchar");
        readerColumns.add(column);
        readerColumns.add(column1);
        List<String> jdbcUrl = new ArrayList<>();
        jdbcUrl.add("jdbc:mysql://10.0.44.37:3306/ems2x");
        jobMap.put(READER_COLUMN, readerColumns);
        jobMap.put(WRITER_NAME, "mysqlWriter");
        jobMap.put(READER_JDBC_URL, jdbcUrl);
        jobMap.put(WRITER_COLUMN, readerColumns);
        jobMap.put(WRITER_JDBC_URL, jdbcUrl);
        jobMap.put(CHANNEL, 1);
        jobMap.put(BYTES, 1024);
        long start = System.currentTimeMillis();
        String s = mapToJson(jobMap);
        System.out.println("耗时：" + (System.currentTimeMillis() - start) + " 毫秒");
        System.out.println(s);
    }


    public static String mapToJson(Map<String, Object> jobMap) {
        if (jobMap == null) {
            return null;
        }
        JSONObject taskJson = new JSONObject();
        for (String key : jobMap.keySet()) {
            analysisKey(taskJson, key, jobMap.get(key));
        }
        return JSON.toJSONString(taskJson, SerializerFeature.DisableCircularReferenceDetect);
    }

    private static void analysisKey(JSONObject taskJson, String key, Object o) {
        List<String> split2List = split2List(key);
        String target = split2List.get(split2List.size() - 1);
        for (final String eachKey : split2List) {
            String each = purgeValue(eachKey);
            if (target.equals(eachKey)) {
                taskJson.put(each, o);
                break;
            }
            if (isKeyMap(eachKey)) {
                taskJson = ensureNestedMap(taskJson, each);
            } else {
                int index = getIndexInKey(eachKey);
                taskJson = ensureNestedArray(taskJson, each, index);
            }
        }
    }

    private static List<String> split2List(final String key) {
        return Arrays.asList(StringUtils.split(key, "."));
    }

    private static boolean isKeyMap(final String key) {
        return !(key.contains("[") && key.contains("]"));
    }

    private static Integer getIndexInKey(String eachKey) {
        String index = eachKey.substring(eachKey.indexOf("[") + 1, eachKey.indexOf("]")).trim();
        Validate.isTrue(!StringUtils.isBlank(index), "key中的[]为空,请检查配置项,key=" + eachKey);
        Validate.isTrue(StringUtils.isNumeric(index), "key中[]包含的不是数字,请检查配置项,key=" + eachKey);
        return Integer.valueOf(index);
    }

    private static String purgeValue(String eachKey) {
        if (eachKey.contains("[")) {
            return eachKey.substring(0,eachKey.indexOf("[")).trim();
        }
        return eachKey.trim();
    }

    private static JSONObject ensureNestedMap(JSONObject taskJson, String key) throws JSONException {
        if (!taskJson.containsKey(key)) {
            taskJson.put(key, new JSONObject());
        }
        return taskJson.getJSONObject(key);
    }

    private static JSONObject ensureNestedArray(JSONObject taskJson, String key, int index) throws JSONException {
        ensureArray(taskJson, key);
        while (taskJson.getJSONArray(key).size() <= index) {
            taskJson.getJSONArray(key).add(new JSONObject());
        }
        return taskJson.getJSONArray(key).getJSONObject(index);
    }
    private static void ensureArray(JSONObject taskJson, String key) throws JSONException {
        if (!taskJson.containsKey(key)) {
            taskJson.put(key, new JSONArray());
        }
    }
}
