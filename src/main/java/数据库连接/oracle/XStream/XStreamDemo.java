package 数据库连接.oracle.XStream;


//import oracle.jdbc.OracleConnection;
//import oracle.streams.XStreamOut;
import org.slf4j.Logger;

import java.sql.DriverManager;
import java.util.Properties;

/**
 * @CreateTime: 2025年 09月 22日 17:00
 * @Description:
 * @Author: MR.YU
 */
public class XStreamDemo {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(XStreamDemo.class);


//    public static void main(String[] args) {
//
//        System.out.println(System.getProperty("java.library.path"));
//
//        String url = "jdbc:oracle:oci:@10.0.47.57:1521/ORCL";
//        String username = "system";
//        String password = "szoscar55";
//        String outServer = "<xstream_out_name>";
//
//        OracleConnection connection = null;
//        XStreamOut xStreamOut = null;
//        try {
//            // 1. 设置连接属性
//            Properties props = new Properties();
//            props.put("user", username);
//            props.put("password", password);
//            props.put("oracle.jdbc.V8Compatible", "true");
//
//            // 2. 建立数据库连接
//            connection = (OracleConnection) DriverManager.getConnection(url, props);
//
//            // 3. 连接到 XStream 出站服务器
//            xStreamOut = XStreamOut.attach(connection, outServer, null, XStreamOut.DEFAULT_MODE);
//
//            System.out.println("Connected to XStream Out server: " + outServer);
//
//            // 4. 消费数据
//            while (true) {
//                // 读取 LCR (逻辑更改记录)
//                oracle.streams.LCR lcr = xStreamOut.receiveLCR(XStreamOut.DEFAULT_MODE);
//                if (lcr != null) {
//                    System.out.println("Received LCR: " + lcr.toString());
//                } else {
//                    System.out.println("No LCR available");
//                }
//            }
//        } catch (Exception e) {
//            logger.error("Error: ", e);
//        } finally {
//            try {
//                if (xStreamOut != null) {
//                    xStreamOut.detach(XStreamOut.DEFAULT_MODE);
//                }
//                if (connection != null) {
//                    connection.close();
//                }
//            } catch (Exception e) {
//                logger.error("Error: ", e);
//            }
//        }
//    }

}
