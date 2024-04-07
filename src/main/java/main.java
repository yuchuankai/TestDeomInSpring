/**
 * @Project: testDeomInSpring
 * @ClassName: main
 * @Date: 2024年 02月 23日 10:07
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/

import canal.testMigrateMap;
import collect.MigrateMap;
import org.apache.commons.cli.*;
import sun.net.dns.ResolverConfiguration;

import java.util.*;

/**
 * @Description:
 * @Date: 2024年 02月 23日 10:07
 * @Author: MR.Yu
 **/
public class main {

    public static void main(String[] args) {
        System.out.println(Arrays.asList(args));
        Options options = new Options();
        options.addOption("h", "help", false, "print help information");
        options.addOption("job",true, "吼吼吼");

        BasicParser parser = new BasicParser();
        try {
            CommandLine cl = parser.parse(options, args);

            System.out.println(cl.getOptionValue("job"));
            if (cl.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("MyApp", options);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
