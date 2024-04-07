/**
 * @Project: testDeomInSpring
 * @ClassName: read
 * @Date: 2024年 03月 11日 10:17
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Real;

import Real.dataBaseParams.Mysql;
import Real.dataBaseParams.Oracle;
import org.apache.commons.lang.builder.ToStringBuilder;
import task.utils.CanalToStringStyle;

import java.io.Serializable;

/**
 * @Description:
 * @Date: 2024年 03月 11日 10:17
 * @Author: MR.Yu
 **/
public class Reader implements Serializable {

    private static final long serialVersionUID = 2332798099928474978L;

    private String dbType;

    private String sourceInId;

    private String schemaIn;

    private String tableIn;

    private String pkName;

//    @JSONField(serializeUsing = CustomSerializer.class, deserializeUsing = CustomDeserializer.class)
    private Mysql mysql;

    private Oracle oracle;

    public String getDbType() {
        return dbType;
    }

    public void setDbType(String dbType) {
        this.dbType = dbType;
    }

    public String getSourceInId() {
        return sourceInId;
    }

    public void setSourceInId(String sourceInId) {
        this.sourceInId = sourceInId;
    }

    public String getSchemaIn() {
        return schemaIn;
    }

    public void setSchemaIn(String schemaIn) {
        this.schemaIn = schemaIn;
    }

    public String getTableIn() {
        return tableIn;
    }

    public void setTableIn(String tableIn) {
        this.tableIn = tableIn;
    }

    public String getPkName() {
        return pkName;
    }

    public void setPkName(String pkName) {
        this.pkName = pkName;
    }

    public Mysql getMysql() {
        return mysql;
    }

    public void setMysql(Mysql mysql) {
        this.mysql = mysql;
    }

    public Oracle getOracle() {
        return oracle;
    }

    public void setOracle(Oracle oracle) {
        this.oracle = oracle;
    }


    public String toString() {
        return ToStringBuilder.reflectionToString(this, CanalToStringStyle.DEFAULT_STYLE);
    }

}
