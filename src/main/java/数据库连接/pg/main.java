package 数据库连接.pg;

import java.sql.*;

/**
 * @CreateTime: 2024年 08月 21日 16:34
 * @Description:
 * @Author: MR.YU
 */
public class main {

    public static void main(String[] args) throws SQLException {
//        Connection conn = getConn();
//        DatabaseMetaData metaData = conn.getMetaData();
//        selectTableColumns(metaData);


        try {
            testPost();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public static Connection getConn(){
        try {
            //注册驱动(maven可以不用注册)
//            Class<?> aClass = Class.forName("org.opengauss.Driver");
//            Driver driver = (Driver)aClass.newInstance();
//            DriverManager.registerDriver(driver);
//            String url = "jdbc:opengauss://10.0.47.73:5432/datasync";

            Class<?> aClass = Class.forName("org.postgresql.Driver");
            Driver driver = (Driver)aClass.newInstance();
            DriverManager.registerDriver(driver);
            String url = "jdbc:postgresql://10.0.47.73:5432/datasync";

            return DriverManager.getConnection(url, "datasync", "szoscar55..");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void selectTableColumns(DatabaseMetaData metaData) throws SQLException {
        ResultSet rs = metaData.getColumns(null, "xyjtest1", "nnn1", null);
        while (rs.next()) {
            System.out.print("列名称： " + rs.getString(4));
            System.out.print("  列类型： " + rs.getString(6) + "\n");
            System.out.println("-------------------------------------");
        }
    }

    private static void selectTable(DatabaseMetaData metaData) throws SQLException {
        ResultSet rs = metaData.getTables(null, "datasync", (String)null, (String[])null);

        int i = 1;
        while(rs.next()) {
            com.alibaba.fastjson.JSONObject mapOfColValues = new com.alibaba.fastjson.JSONObject(true);
            mapOfColValues.put("TABLE_CAT", rs.getString(1));
            mapOfColValues.put("TABLE_SCHEMA", rs.getString(2));
            mapOfColValues.put("TABLE_NAME", rs.getString(3));
            mapOfColValues.put("TABLE_TYPE", rs.getString(4));
            mapOfColValues.put("REMARKS", rs.getString(5));
            if (mapOfColValues.getString("TABLE_TYPE").equals("TABLE")) {
                System.out.println(mapOfColValues);
                System.out.println("-------------------------------------" + i++);
            }


        }
    }


    public static void testPost() throws ClassNotFoundException, SQLException {
//        Class.forName("org.postgresql.Driver");
        String url = "jdbc:postgresql://10.0.47.75:15400/datasync";
        Connection connection = DriverManager.getConnection(url, "datasync", "szoscar55..");
        try {
            Statement statement = connection.createStatement();
            boolean execute = statement.execute("select * from ztf.st_lxtb_yd_clob");

            ResultSet resultSet = statement.getResultSet();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println("columnCount:" + columnCount);
            for (int i = 1; i <= columnCount; i++) {
                resultSet.next();
                System.out.print(resultSet.getString(1) + "   ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
