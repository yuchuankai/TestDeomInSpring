/**
 * @Project: datagov-server
 * @ClassName: GlobalParam
 * @Date: 2024年 05月 15日 10:35
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

import Real.dataBaseParams.DatabaseParams;

/**
 * @Description:
 * @Date: 2024年 05月 15日 10:35
 * @Author: MR.Yu
 **/
public class GlobalParam extends DatabaseParams {

    private static final long serialVersionUID = 2332798099928474983L;

    private String prop;

    private String direct;

    private String type;

    private String value;

    public GlobalParam(String prop, String value) {
        this.prop = prop;
        this.value = value;
        this.direct = "INSIDE";
        this.type = "VARCHAR";
    }

    public GlobalParam(String prop, String direct, String type, String value) {
        this.prop = prop;
        this.direct = direct;
        this.type = type;
        this.value = value;
    }


    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
