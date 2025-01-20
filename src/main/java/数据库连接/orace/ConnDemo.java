package 数据库连接.orace;

import org.apache.commons.lang.Validate;

import java.sql.*;
import java.util.Properties;

/**
 * @CreateTime: 2025年 01月 02日 17:05
 * @Description:
 * @Author: MR.YU
 */
public class ConnDemo {

    public static Connection conn = null;
    public static PreparedStatement ps = null;
//    public static String url = "jdbc:oscar://10.0.66.107:2003/yd";
public static String url = "jdbc:oscar://10.0.47.73:2003/osrdb";
    public static String user = "sysdba";
    public static String password = "szoscar55";

    public static void main(String[] args) {
        try {
            selectTableColumns();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection getConn(){
        try {
//            Class<?> aClass = Class.forName("oracle.jdbc.driver.OracleDriver");
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

    public static void selectTable() throws SQLException {
        conn = getConn();
        Validate.notNull(conn, "Get connection filed.");
        ResultSet rs = conn.getMetaData().getTables(null, "A_TEST", "%", new String[]{"TABLE"});
        while (rs.next()) {
            System.out.println("Table Name: " + rs.getString("TABLE_NAME"));
            System.out.println("Table Type: " + rs.getString("TABLE_TYPE"));
            System.out.println("Remarks: " + rs.getString("REMARKS"));
            System.out.println("--------------------");
        }
    }


    public static void selectTableColumns() throws SQLException {
        conn = getConn();
        Validate.notNull(conn, "Get connection filed.");
        ResultSet rs = conn.getMetaData().getColumns(null, "ZTF", "A_VARBIT",null);
        while (rs.next()) {
            System.out.println("Table Name: " + rs.getString("TABLE_NAME"));
            System.out.println("Column Name: " + rs.getString("COLUMN_NAME"));
            System.out.println("Data Type: " + rs.getInt("DATA_TYPE"));
            System.out.println("Column Type: " + rs.getString("TYPE_NAME"));
            System.out.println("--------------------");
        }
    }

}
