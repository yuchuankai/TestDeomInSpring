/**
 * @Project: testDeomInSpring
 * @ClassName: TaskDirector
 * @Date: 2024年 03月 12日 10:44
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real.director;

import Real.Content;
import Real.builder.Builder;

/**
 * @Description:
 * @Date: 2024年 03月 12日 10:44
 * @Author: MR.Yu
 **/
public class TaskDirector {

    private Content content;

    public TaskDirector(Content content) {
        this.content = content;
    }

    public void createRealTask(Builder builder) {
        builder.buildJob();
        builder.buildReader(content.getReader());
        builder.buildWriter(content.getWriter());
        builder.buildSetting();
    }

}
