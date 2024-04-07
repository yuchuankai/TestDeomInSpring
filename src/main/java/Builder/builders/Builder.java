/**
 * @Project: testDeomInSpring
 * @ClassName: Builder
 * @Date: 2024年 02月 29日 14:34
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package Builder.builders;

import Builder.cars.CarType;
import Builder.components.Engine;
import Builder.components.GPSNavigator;
import Builder.components.Transmission;
import Builder.components.TripComputer;

/**
 * @Description:
 * @Date: 2024年02月29日 14:34
 * @Author: MR.Yu
 **/
public interface Builder {

    void setCarType(CarType type);
    void setSeats(int seats);
    void setEngine(Engine engine);
    void setTransmission(Transmission transmission);
    void setTripComputer(TripComputer tripComputer);
    void setGPSNavigator(GPSNavigator gpsNavigator);
}
