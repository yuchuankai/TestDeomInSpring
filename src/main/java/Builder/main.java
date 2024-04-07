/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 02月 29日 14:45
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Builder;

import Builder.builders.CarBuilder;
import Builder.builders.CarManualBuilder;
import Builder.cars.Car;
import Builder.cars.Manual;
import Builder.director.Director;

/**
 * @Description:
 * @Date: 2024年 02月 29日 14:45
 * @Author: MR.Yu
 **/
public class main {
    public static void main(String[] args) {
        Director director = new Director();
        CarBuilder carBuilder = new CarBuilder();
        director.constructSportsCar(carBuilder);

        Car car = carBuilder.getResult();
        System.out.println("Car built:\n" + car.getCarType());

        CarManualBuilder manualBuilder = new CarManualBuilder();

        // Director may know several building recipes.
        director.constructSportsCar(manualBuilder);
        Manual carManual = manualBuilder.getResult();
        System.out.println("\nCar manual built:\n" + carManual.print());
    }
}
