package 数据库连接.mysql;

import org.apache.commons.lang.Validate;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @CreateTime: 2024年 09月 25日 10:39
 * @Description:
 * @Author: MR.YU
 */
public class MysqlStatement {

    public static Connection conn = null;
    public static PreparedStatement ps = null;

    public static String url = "jdbc:mysql://10.0.47.57:3306/mysql?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    public static String user = "root";
    public static String password = "szoscar55";

    public static void main(String[] args) throws SQLException {
        conn = getConn();
        Validate.notNull(conn, "Get connection filed.");

        PreparedStatement statement =
                conn.prepareStatement(
                        "SELECT ID, AMOUNT, GOODS, REASON, PERSON, CONSUME_TIME, ABC FROM datasync.consume_detail", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
        statement.setFetchSize(Integer.MIN_VALUE);

        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("REASON"));
        }
    }


    public static Connection getConn(){
        try {
            //注册驱动(maven可以不用注册)
            Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver = (Driver)aClass.newInstance();
            DriverManager.registerDriver(driver);

            return DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
