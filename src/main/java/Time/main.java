package Time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @CreateTime: 2024年 09月 12日 17:20
 * @Description:
 * @Author: MR.YU
 */
public class main {
    public static void main(String[] args) throws ParseException {
        test2();
    }

    public void test1() throws ParseException{
        //        System.out.println(LocalDateTime.now());
//        System.out.println(LocalDateTime.parse("20240912", DateTimeFormatter.BASIC_ISO_DATE));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        Date parse = dateFormat.parse("2024-12-06 09:31:00");


        System.out.println(date.compareTo(parse));
    }

    public static void test2(){
        LocalDate today = LocalDate.now();
        System.out.println(today.atStartOfDay());
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.getHour());
    }
}
