package FPE;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @CreateTime: 2025年 04月 21日 17:02
 * @Description:
 * @Author: MR.YU
 */
public class ByteArrayCollector {

    public static Collector<Byte, ?, byte[]> toByteArray() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                ByteArrayCollector::convertListToByteArray
        );
    }

    private static byte[] convertListToByteArray(List<Byte> list) {
        byte[] result = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }
        return result;
    }
}
