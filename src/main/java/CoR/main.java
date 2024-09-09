/**
 * @Project: testDeomInSpring
 * @ClassName: FileChange
 * @Date: 2024年 02月 27日 14:31
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package CoR;

/**
 * @Description:
 * @Date: 2024年 02月 27日 14:31
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) {
        Server server = new Server("test", Middleware.link(new IsBlankMiddleware(),new StringLengthMiddleware()));
        System.out.println(server.check());
    }
}
