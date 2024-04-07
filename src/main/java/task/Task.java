/**
 * @Project: testDeomInSpring
 * @ClassName: Job
 * @Date: 2024年 02月 26日 15:23
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

import org.apache.commons.lang.builder.ToStringBuilder;
import task.utils.CanalToStringStyle;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2024年 02月 26日 15:23
 * @Author: MR.Yu
 **/
public abstract class Task implements Serializable {

    private static final long serialVersionUID = 2332798099928474975L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this, CanalToStringStyle.DEFAULT_STYLE);
    }
}
