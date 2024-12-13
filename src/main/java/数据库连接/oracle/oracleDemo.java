package 数据库连接.oracle;


import org.apache.commons.lang.Validate;

import java.sql.*;
import java.util.Properties;

/**
 * @CreateTime: 2024年 09月 07日 18:11
 * @Description:
 * @Author: MR.YU
 */
public class oracleDemo {

    public static Connection conn = null;
    public static PreparedStatement ps = null;

    public static String url = "jdbc:oracle:thin:@//10.0.47.57:1521/ORCL;remarksReporting=true";
    public static String user = "system";
    public static String password = "szoscar55";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        conn = getConn();
        Validate.notNull(conn, "Get connection filed.");
        ResultSet rs = conn.getMetaData().getTables(null, "C##ROOT1", "%", new String[]{"TABLE"});
        while (rs.next()) {
            System.out.println("Table Name: " + rs.getString("TABLE_NAME"));
            System.out.println("Table Type: " + rs.getString("TABLE_TYPE"));
            System.out.println("Remarks: " + rs.getString("REMARKS"));
            System.out.println("--------------------");
        }
    }


    public static Connection getConn(){
        try {

            Class<?> aClass = Class.forName("oracle.jdbc.driver.OracleDriver");
            Properties prop = new Properties();
            prop.setProperty("user", user);
            prop.setProperty("password", password);
            // 获取注释
//            prop.setProperty("remarksReporting", "true");
            return DriverManager.getConnection(url, prop);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
