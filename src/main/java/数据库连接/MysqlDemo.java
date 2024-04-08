/**
 * @Project: testDeomInSpring
 * @ClassName: MysqlDemo
 * @Date: 2024年 04月 08日 15:49
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 数据库连接;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Date: 2024年 04月 08日 15:49
 * @Author: MR.Yu
 **/
public class MysqlDemo {

    public static Connection conn = null;
    public static PreparedStatement ps = null;

    public static String url = "jdbc:mysql://10.0.47.57:3306/mysql?characterEncoding=utf8&useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    public static String user = "root";
    public static String password = "szoscar55";

    public static void main(String[] args){
        getConn();
        List<String> executeSqlList = new ArrayList<>();
        String sql1 = "INSERT INTO datasync.consume_detail_real(ID, AMOUNT, GOODS, REASON, PERSON, CONSUME_TIME) VALUES(14, 34, '文具盒1', '购买办公用品', '张立华', '2023-09-03 11:34:18');";
        String sql2 = "INSERT INTO datasync.consume_detail_real(ID, AMOUNT, GOODS, REASON, PERSON, CONSUME_TIME) VALUES(15, 34, '文具盒1', '购买办公用品', '张立华', '2023-09-03 11:34:18');";
        executeSqlList.add(sql1);
        executeSqlList.add(sql2);
        transactionSubmit(executeSqlList);
        closeConn();
    }


    public static void getConn(){
        try {
            //注册驱动(maven可以不用注册)
            Class<?> aClass = Class.forName("com.mysql.cj.jdbc.Driver");
            Driver driver = (Driver)aClass.newInstance();
            DriverManager.registerDriver(driver);

            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transactionSubmit(List<String> executeSqlList){

        try {
            conn.setAutoCommit(false);
            for (String sql : executeSqlList) {
                ps = conn.prepareStatement(sql);
//                ps.setString(1, "1");
                ps.executeUpdate();
            }
            conn.commit();
        }catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println("sql进行回滚");
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }


    public static void closeConn(){
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
    }
}
