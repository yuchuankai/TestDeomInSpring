/**
 * @Project: testDeomInSpring
 * @ClassName: MysqlSlave
 * @Date: 2024年 04月 09日 10:07
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 数据库连接.mysql;

import org.apache.commons.lang.Validate;

import java.sql.*;

/**
 * @Description:
 * @Date: 2024年 04月 09日 10:07
 * @Author: MR.Yu
 **/
public class MysqlSlave {

    private final static String QUERY_BINLOG = "show master status";

    private static String file = null;

    private static String position = null;
    public static void main(String[] args) {
        Connection conn = MysqlDemo.getConn();
        Validate.notNull(conn,"连接失败");
        try {
            PreparedStatement ps = conn.prepareStatement(QUERY_BINLOG);
            ResultSet re = ps.executeQuery();
            while (re.next()) {
                file = re.getString("File");
                position = re.getString("Position");
            }
            System.out.println("file:" + file + " position:" + position);

        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
}
