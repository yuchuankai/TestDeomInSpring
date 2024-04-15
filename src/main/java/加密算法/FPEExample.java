/**
 * @Project: testDeomInSpring
 * @ClassName: FPEExample
 * @Date: 2024年 04月 10日 10:46
 * @version V1.0
 * @Author: MR.Yu
 * Copyright (c) All Rights Reserved, 2024.
 **/
package 加密算法;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.symmetric.fpe.FPE;
import org.bouncycastle.crypto.util.BasicAlphabetMapper;

/**
 * @Description:
 * @Date: 2024年 04月 10日 10:46
 * @Author: MR.Yu
 **/
public class FPEExample {

    public static void main(String[] args) {

        // 映射字符表，规定了明文和密文的字符范围
        BasicAlphabetMapper numberMapper = new BasicAlphabetMapper("0123456789");

        BasicAlphabetMapper textMapper = new BasicAlphabetMapper("abcdefghijklmnopqrstuvwxyz");


        // 初始化 aes 密钥（随机），长度必须是16bytes、24bytes或32bytes
        byte[] keyBytes = RandomUtil.randomBytes(16);

        final FPE fpe = new FPE(FPE.FPEMode.FF1, keyBytes, numberMapper,
                // Tweak是为了解决因局部加密而导致结果冲突问题，通常情况下将数据的不可变部分作为Tweak，null则使用默认长度全是0的bytes
                null);

        String phone = "111111";
        // 加密
        String encrypt = fpe.encrypt(phone);
        System.out.println(encrypt);
        // 解密
        String decrypt = fpe.decrypt(encrypt);
    }

}
