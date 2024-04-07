/**
 * @Project: testDeomInSpring
 * @ClassName: ThrottlingMiddleware
 * @Date: 2024年 02月 27日 14:26
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package CoR;

import org.apache.commons.lang.StringUtils;

/**
 * @Description:
 * @Date: 2024年 02月 27日 14:26
 * @Author: MR.Yu
 **/
public class IsBlankMiddleware extends Middleware{

    @Override
    public boolean check(String eventType) {

        if (StringUtils.isNotBlank(eventType)) {
            // 事件处理
            System.out.println("IsBlankMiddleware：true");
        }
        return checkNext(eventType);
    }

}
