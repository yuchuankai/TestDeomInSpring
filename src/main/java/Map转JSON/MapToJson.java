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
    private final static String READER_COLUMN = "job.content[0].reader.column";
    private final static String READER_CONNECTION = "job.content[0].reader.connection[0].connection";
    private final static String WRITER_NAME = "job.content[1].writer.name";
    private final static String WRITER_COLUMN = "job.content[1].writer.column";
    private final static String WRITER_CONNECTION = "job.content[1].writer.connection[0].connection";
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
        jobMap.put(READER_COLUMN, readerColumns);
        jobMap.put(READER_CONNECTION, "readerConnection的值");
        jobMap.put(WRITER_NAME, "mysqlWriter");
        jobMap.put(WRITER_COLUMN, readerColumns);
        jobMap.put(WRITER_CONNECTION, "writerConnection的值");
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
            if (target.equals(each)) {
                taskJson.put(target, o);
                break;
            }
            if (isKeyMap(eachKey)) {
                if (taskJson.containsKey(each)) {
                    taskJson = taskJson.getJSONObject(each);
                } else {
                    taskJson.put(each, new JSONObject());
                    taskJson = taskJson.getJSONObject(each);
                }
            } else {
                int index = getIndexInKey(eachKey);
                if (!taskJson.containsKey(each)) {
                    taskJson.put(each, new JSONArray());
                }
                if (taskJson.getJSONArray(each).size() <= index) {
                    while (taskJson.getJSONArray(each).size() <= index) {
                        taskJson.getJSONArray(each).add(new JSONObject());
                    }
                }
                taskJson = taskJson.getJSONArray(each).getJSONObject(index);
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
}
