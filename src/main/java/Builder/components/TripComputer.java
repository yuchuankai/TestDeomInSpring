/**
 * @Project: testDeomInSpring
 * @ClassName: TripComputer
 * @Date: 2024年 02月 29日 14:37
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Builder.components;

import Builder.cars.Car;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:37
 * @Author: MR.Yu
 **/
public class TripComputer {
    private Car car;

    public void setCar(Car car) {
        this.car = car;
    }

    public void showFuelLevel() {
        System.out.println("Fuel level: " + car.getFuel());
    }

    public void showStatus() {
        if (this.car.getEngine().isStarted()) {
            System.out.println("Car is started");
        } else {
            System.out.println("Car isn't started");
        }
    }
}
