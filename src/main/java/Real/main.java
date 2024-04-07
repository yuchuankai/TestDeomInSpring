/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 03月 11日 11:00
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real;

import Real.builder.Builder;
import Real.builder.RealBuilder;
import Real.director.TaskDirector;
import org.apache.commons.io.FileUtils;
import task.utils.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @Description:
 * @Date: 2024年 03月 11日 11:00
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) throws IOException {
        String patch = "C:\\Users\\yuchuankai\\Desktop\\2024.3\\real-Json.json";
        File file = new File(patch);
        String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        Content content = JsonUtils.jobFromString(json, Content.class);
        Builder builder = new RealBuilder();
        TaskDirector taskDirector = new TaskDirector(content);
        taskDirector.createRealTask(builder);
        String task = builder.getTask();
        System.out.println(task);

    }
}
