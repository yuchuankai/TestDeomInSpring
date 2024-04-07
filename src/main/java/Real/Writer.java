/**
 * @Project: testDeomInSpring
 * @ClassName: writer
 * @Date: 2024年 03月 11日 10:17
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real;

import org.apache.commons.lang.builder.ToStringBuilder;
import task.utils.CanalToStringStyle;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2024年 03月 11日 10:17
 * @Author: MR.Yu
 **/
public class Writer implements Serializable {

    private static final long serialVersionUID = 2332798099928474979L;

    private String dbType;

    private String sourceOutId;

    private String schemaOut;

    private String tableOut;

    private boolean isClean;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getSourceOutId() {
        return sourceOutId;
    }

    public void setSourceOutId(String sourceOutId) {
        this.sourceOutId = sourceOutId;
    }

    public String getSchemaOut() {
        return schemaOut;
    }

    public void setSchemaOut(String schemaOut) {
        this.schemaOut = schemaOut;
    }

    public String getTableOut() {
        return tableOut;
    }

    public void setTableOut(String tableOut) {
        this.tableOut = tableOut;
    }

    public boolean isClean() {
        return isClean;
    }

    public void setClean(boolean clean) {
        isClean = clean;
    }

    public String toString() {
        return ToStringBuilder.reflectionToString(this, CanalToStringStyle.DEFAULT_STYLE);
    }
}
