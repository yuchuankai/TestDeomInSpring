/**
 * @Project: testDeomInSpring
 * @ClassName: Middleware
 * @Date: 2024年 02月 27日 14:22
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package CoR;

/**
 * @Description:
 * @Date: 2024年 02月 27日 14:22
 * @Author: MR.Yu
 **/
public abstract class Middleware {

    private Middleware middleware;

    public static Middleware link(Middleware first, Middleware... middlewares) {
        Middleware head = first;
        for (Middleware middleware : middlewares) {
            head.middleware = middleware;
            head = middleware;
        }
        return first;
    }

    public abstract boolean check(String eventType);

    protected boolean checkNext(String eventType) {
        return middleware == null || middleware.check(eventType);
    }
}
