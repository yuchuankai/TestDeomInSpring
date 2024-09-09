/**
 * @Project: testDeomInSpring
 * @ClassName: FileChange
 * @Date: 2024年 03月 20日 9:19
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 抽象类;

/**
 * @Description:
 * @Date: 2024年 03月 20日 9:19
 * @Author: MR.Yu
 **/
public class main {
    public static void main(String[] args) {
        AbstractJobPlugin abstractJobPlugin = new MysqlJob();
        abstractJobPlugin.setAbstractJobPlugin("mysql");
        abstractJobPlugin.init();
        abstractJobPlugin.destroy();
        System.out.println(abstractJobPlugin.getAbstractJobPlugin());
    }
}
