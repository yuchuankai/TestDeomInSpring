/**
 * @Project: testDeomInSpring
 * @ClassName: Server
 * @Date: 2024年 02月 27日 14:33
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package CoR;

/**
 * @Description:
 * @Date: 2024年 02月 27日 14:33
 * @Author: MR.Yu
 **/
public class Server {

    private String name;

    private Middleware middleware;


    public Server(String name, Middleware middleware) {
        this.name = name;
        this.middleware = middleware;
    }

    public boolean check() {
        return middleware.check(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
