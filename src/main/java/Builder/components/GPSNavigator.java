/**
 * @Project: testDeomInSpring
 * @ClassName: GPSNavigator
 * @Date: 2024年 02月 29日 14:36
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Builder.components;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:36
 * @Author: MR.Yu
 **/
public class GPSNavigator {
    private String route;

    public GPSNavigator() {
        this.route = "221b, Baker Street, London  to Scotland Yard, 8-10 Broadway, London";
    }

    public GPSNavigator(String manualRoute) {
        this.route = manualRoute;
    }

    public String getRoute() {
        return route;
    }
}
