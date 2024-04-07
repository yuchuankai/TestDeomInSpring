/**
 * @Project: testDeomInSpring
 * @ClassName: StringLengthMiddleware
 * @Date: 2024年 02月 27日 14:30
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package CoR;

/**
 * @Description:
 * @Date: 2024年 02月 27日 14:30
 * @Author: MR.Yu
 **/
public class StringLengthMiddleware extends Middleware{
    @Override
    public boolean check(String eventType) {
        System.out.println("StringLengthMiddleware: " + eventType.length());
        return checkNext(eventType);
    }
}
