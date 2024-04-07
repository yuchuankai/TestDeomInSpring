/**
 * @Project: testDeomInSpring
 * @ClassName: DatabaseParams
 * @Date: 2024年 03月 11日 10:46
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real.dataBaseParams;

import org.apache.commons.lang.builder.ToStringBuilder;
import task.utils.CanalToStringStyle;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2024年03月11日 10:46
 * @Author: MR.Yu
 **/
public abstract class DatabaseParams implements Serializable {

    private static final long serialVersionUID = 2332798099928474975L;

    public String toString() {
        return ToStringBuilder.reflectionToString(this, CanalToStringStyle.DEFAULT_STYLE);
    }
}
