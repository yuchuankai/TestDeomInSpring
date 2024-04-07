/**
 * @Project: testDeomInSpring
 * @ClassName: Column
 * @Date: 2024年 02月 26日 16:09
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

/**
 * @Description:
 * @Date: 2024年 02月 26日 16:09
 * @Author: MR.Yu
 **/
public class Column extends Task {

    private static final long serialVersionUID = 3875012010277005819L;

    private String name;

    private Integer index;

    private String type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
