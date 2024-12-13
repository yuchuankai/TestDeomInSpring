package 数据库连接.mysql;

import lombok.SneakyThrows;
import org.apache.commons.lang.Validate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @CreateTime: 2024年 12月 10日 11:17
 * @Description:
 * @Author: MR.YU
 */
public class mysql_mvcc {

    /**
     * 数据库隔离级别：可重复读
     * 多线程中使用事务，A事务查询id为1的数据后停止一段时间，B事务查询id为1的数据并修改数据，此时A事务再次查询id为1的数据时，
     * 数据还是旧的原因是此时A事务读取的并不是数据库的真是数据而是快照中的数据。
     * 解决办法：
     *      1.降低数据库的隔离级别。
     *      2.使用锁定读的方式查询数据库的最新数据select * from [table_name] for update,此种方式间接产生了“不可重复读”。
     *      3.将第一次查询提取到事务之外（推荐）
     */
    public static void main(String[] args) {
        Connection conn = MysqlDemo.getConn();
        Validate.notNull(conn,"连接失败");

//        initTable(conn);

        tableRollback(100, conn);

//        testLock("1",conn);

        for (int i = 1; i < 100; i++) {
            startThread(String.valueOf(i));
        }
    }



    private static void startThread(String i) {
        Thread thread1 = new Thread(() -> {
            Connection conn = MysqlDemo.getConn();
            Validate.notNull(conn,"连接失败");
            try {
                boolean exist = isExist(i,conn);
                conn.setAutoCommit(false);

                if (exist) {
                    lockForUpdate(i,conn);
                    if(!tableIsChange(i,conn)) {
                        updateTableThread1(i,conn);
                    }
                }
                conn.commit();
                conn.close();
            } catch (SQLException e1) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("sql执行异常:" + e1.getMessage());
            }
        }, "p_name-" + i);

        Thread thread2 = new Thread(() -> {
            Connection conn = MysqlDemo.getConn();
            Validate.notNull(conn,"连接失败");
            try {
                boolean exist = isExist(i, conn);
                conn.setAutoCommit(false);

                if (exist) {
                    lockForUpdate(i, conn);
                    if(!tableIsChange(i,conn)) {
                        updateTableThread2(i,conn);
                    }
                }
                conn.commit();
                conn.close();
            } catch (SQLException e1) {
                try {
                    conn.rollback();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("sql执行异常:" + e1.getMessage());
            }
        }, "p_age-" + i);

        thread1.start();
        thread2.start();
    }



    private static void updateTableThread1(String id, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("update people set p_name = id, p_status = '1' where id = ?");
            ps.setInt(1, Integer.parseInt(id));
            if (ps.execute()) {
                System.out.println("线程：" + Thread.currentThread().getName() + "修改数据");
            }
        } catch (SQLException e1) {
            System.out.println("sql执行异常:" + e1.getMessage());
        }
    }
    private static void updateTableThread2(String id, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("update people set p_age = id, p_status = '1' where id = ?");
            ps.setInt(1, Integer.parseInt(id));
            if (ps.executeUpdate() > 0) {
                System.out.println("线程：" + Thread.currentThread().getName() + "修改数据");
            }
        } catch (SQLException e1) {
            System.out.println("sql执行异常:" + e1.getMessage());
        }
    }

    private static boolean isExist(String id, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from datasync.people where id = ?");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet re = ps.executeQuery();
            if (re.next()) {
                System.out.println("线程：" + Thread.currentThread().getName() + "查询到数据");
                return true;
            } else {
                return false;
            }
        } catch (SQLException e1) {
            System.out.println("sql执行异常:" + e1.getMessage());
        }
        return false;
    }
    private static boolean tableIsChange(String id, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from datasync.people where id = ? ");
            ps.setInt(1, Integer.parseInt(id));
            ResultSet re = ps.executeQuery();
            if (re.next()) {
                if ("1".equals(re.getString("p_status"))) {
                    System.out.println("线程：" + Thread.currentThread().getName() + "查询到数据已经发生改变,p_status:" + re.getString("p_status"));
                    return true;
                }
                System.out.println("线程：" + Thread.currentThread().getName() + "查询到数据未修改");
            } else {
                return false;
            }
        } catch (SQLException e1) {
            System.out.println("sql执行异常:" + e1.getMessage());
        }
        return false;
    }

    private static void lockForUpdate(String id, Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("select * from datasync.people where id = ? for update");
            ps.setInt(1, Integer.parseInt(id));
            if (ps.execute()) {
                System.out.println("线程：" + Thread.currentThread().getName() + "获取到锁,id:" + id);
            } else {
                System.out.println("线程：" + Thread.currentThread().getName() + "未获取到锁,id:" + id);
            }
        } catch (SQLException e1) {
            System.out.println("sql执行异常:" + e1.getMessage());
        }
    }


    private static void tableRollback(int num, Connection conn) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement("update datasync.people set p_name = '0',p_age = 0, p_status = '2' where id <= ?");
            ps.setInt(1, num);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initTable(int num, Connection conn) {
        PreparedStatement ps = null;
        for (int i = 1; i < num; i++) {
            try {
                ps = conn.prepareStatement("INSERT INTO datasync.people (id, p_name, p_age) VALUES(?, '0', 0)");
                ps.setInt(1, i);
                ps.execute();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @SneakyThrows
    private static void testLock(String id, Connection conn) {
        conn.setAutoCommit(false);
        lockForUpdate(id,conn);
        try {
            Thread.sleep(1000 * 100);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        conn.commit();
        conn.setAutoCommit(true);
    }
}
