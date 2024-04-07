/**
 * @Project: testDeomInSpring
 * @ClassName: parameter
 * @Date: 2024年 02月 26日 15:22
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

import org.apache.commons.lang.builder.ToStringBuilder;
import task.utils.CanalToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * @Description:
 * @Date: 2024年 02月 26日 15:22
 * @Author: MR.Yu
 **/
public class Parameter implements Serializable {

    private static final long serialVersionUID = 3875012010277005810L;

    private List<Column> column;

    public List<Column> getColumn() {
        return column;
    }

    public void setColumn(List<Column> column) {
        this.column = column;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, CanalToStringStyle.DEFAULT_STYLE);
    }


}
