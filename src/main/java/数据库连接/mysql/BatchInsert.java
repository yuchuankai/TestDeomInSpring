package 数据库连接.mysql;

import org.apache.commons.lang.Validate;

import java.sql.*;
import java.util.List;
import java.util.Random;

/**
 * @CreateTime: 2025年 04月 15日 15:41
 * @Description:
 * @Author: MR.YU
 */
public class BatchInsert {


    public static String url = "jdbc:mysql://172.24.134.66:3306/mysql?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    public static String user = "root";
    public static String password = "123456";


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

    public static void main(String[] args) {
        Connection conn = getConn();
        Validate.notNull(conn, "Get connection filed.");
        long start = System.currentTimeMillis();
        batchInsert(conn);
        long end = System.currentTimeMillis();
        System.out.println("耗时：" + (end - start));

    }

    public static void batchInsert(Connection conn) {

        String sqlPrefix = "INSERT INTO datasync.t5 VALUES(?,?,?,?) ";
        try {
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(sqlPrefix);

            for (int j = 0; j <= 100; j++) {

                int i = j * 1000 + 1;
                int max  = i + 1000;

                for ( ; i < max; i++) {
                    ps.setInt(1, i);
                    ps.setInt(2, i);
                    ps.setString(3, getColumn1());
                    ps.setInt(4, getColumn());
                    ps.addBatch();
                }


                System.out.println("执行：" + j);
                ps.executeBatch();
                conn.commit();
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static Integer getColumn() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }


    public static String getColumn1() {
        String[] values = {"苹果", "香蕉", "橙子", "葡萄", "草莓"};

        Random random = new Random();
        return values[random.nextInt(values.length)];
    }

}
