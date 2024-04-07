/**
 * @Project: testDeomInSpring
 * @ClassName: testMigrateMap
 * @Date: 2024年 02月 23日 10:16
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package canal;

import collect.MigrateMap;


import java.util.Map;


/**
 * @Description:
 * @Date: 2024年 02月 23日 10:16
 * @Author: MR.Yu
 **/
public class testMigrateMap {

    private String name;

    public testMigrateMap() {
        initMain();
        this.name = "testMigrateMap";
    }
    public void initMain() {
        System.out.println("initMain");
//        Map<String, String> computingMap = MigrateMap.makeComputingMap(this::loadFile);
        Map<String, String> computingMap = MigrateMap.makeComputingMap(fileName -> {
            System.out.println("loadFile: " + fileName);
            return "loadFile";
        });
        computingMap.get("meta.bat");
    }

    private String loadFile(String fileName) {
        System.out.println("fileName: " + fileName);
        return "loadFile";
    }
}
