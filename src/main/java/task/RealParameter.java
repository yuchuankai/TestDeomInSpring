/**
 * @Project: testDeomInSpring
 * @ClassName: RealParameter
 * @Date: 2024年 02月 26日 16:43
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package task;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * @Description:
 * @Date: 2024年 02月 26日 16:43
 * @Author: MR.Yu
 **/
public class RealParameter extends Parameter {

    private static final long serialVersionUID = 3875012010277005819L;

    @JSONField(name = "encrypt_column")
    private List<Column> encryptColumn;

    public List<Column> getEncryptColumn() {
        return encryptColumn;
    }

    public void setEncryptColumn(List<Column> encryptColumn) {
        this.encryptColumn = encryptColumn;
    }

}