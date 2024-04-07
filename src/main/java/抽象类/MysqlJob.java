/**
 * @Project: testDeomInSpring
 * @ClassName: MysqlJob
 * @Date: 2024年 03月 20日 9:17
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 抽象类;

/**
 * @Description:
 * @Date: 2024年 03月 20日 9:17
 * @Author: MR.Yu
 **/
public class MysqlJob extends Job{
    @Override
    public void init() {
        System.out.println("mysqlJob init");
        super.init();
    }

    @Override
    public void destroy() {
        System.out.println("mysqlJob destroy");
        super.destroy();
    }
}
