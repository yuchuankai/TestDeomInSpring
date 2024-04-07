/**
 * @Project: testDeomInSpring
 * @ClassName: taskWriter
 * @Date: 2024年 02月 26日 15:21
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

/**
 * @Description:
 * @Date: 2024年 02月 26日 15:21
 * @Author: MR.Yu
 **/
public class Writer extends Task {

    private static final long serialVersionUID = 3875012010277005819L;

    private Parameter parameter;

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
