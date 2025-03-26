package 数据库连接.tdengine;

import java.sql.*;

/**
 * @CreateTime: 2025年 03月 21日 14:23
 * @Description:
 * @Author: MR.YU
 */
public class TdengineDemo {


    public static String url = "jdbc:TAOS-RS://10.0.47.74:6041/";
    public static String user = "root";
    public static String password = "1qaz!QAZ";
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {

//        Class<?> aClass = Class.forName("com.taosdata.jdbc.ws.entity.Code");
//        Driver driver = (Driver)aClass.newInstance();
//        DriverManager.registerDriver(driver);

        Connection connection = DriverManager.getConnection(url, user, password);

        PreparedStatement preparedStatement = connection.prepareStatement("select * from datasync.chuan");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            System.out.println(resultSet.getString("a"));
        }

        preparedStatement.close();
    }
}
