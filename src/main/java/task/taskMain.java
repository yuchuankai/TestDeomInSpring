/**
 * @Project: testDeomInSpring
 * @ClassName: taskMain
 * @Date: 2024年 02月 26日 15:47
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

import org.apache.commons.io.FileUtils;
import task.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @Date: 2024年 02月 26日 15:47
 * @Author: MR.Yu
 **/
public class taskMain {

    public static void main(String[] args) {

        File dataFile = new File("C:\\Users\\yuchuankai\\Desktop\\文档\\2024.1\\mysql-kingbase.json");

        try {
            String json = FileUtils.readFileToString(dataFile, StandardCharsets.UTF_8);
            OfflineTask offlineJob = JsonUtils.jobFromString(json, OfflineTask.class);
            offlineJob.getJob().getContent().get(0).getReader().getParameter().getColumn().forEach(column -> {
                System.out.println(column);
            });
            System.out.println(offlineJob);
            System.out.println(JsonUtils.stringFromJob(offlineJob));
        } catch (IOException e) {
            e.printStackTrace();
        }

//
//        Writer writer = new Writer();
//        writer.setParameter(new RealParameter());


    }
}
