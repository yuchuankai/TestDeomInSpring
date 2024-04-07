/**
 * @Project: testDeomInSpring
 * @ClassName: Job
 * @Date: 2024年 02月 26日 16:03
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

import java.util.List;

/**
 * @Description:
 * @Date: 2024年 02月 26日 16:03
 * @Author: MR.Yu
 **/
public class Job extends Task {

    private static final long serialVersionUID = 3875012010277005819L;

    private List<Content> content;

    public List<Content> getContent() {
        return content;
    }

    public void setContent(List<Content> content) {
        this.content = content;
    }
}
