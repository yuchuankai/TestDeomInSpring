package FPE;


import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.fpe.FPEFF1Engine;
import org.bouncycastle.crypto.params.FPEParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.encoders.Hex;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @CreateTime: 2025年 04月 21日 14:54
 * @Description: 格式保留加密
 * @Author: MR.YU
 */
public class Demo {

    private static final String ALPHABET_NUMERIC = "0123456789"; // 定义数字字符集

    public static void main(String[] args) throws Exception {
        // 原始明文数字（例如身份证号、手机号）
        String plaintext = "1234567890";

        if (!validateInput(plaintext, ALPHABET_NUMERIC)) {
            throw new IllegalArgumentException("Input data outside of radix");
        }

        // 加密密钥（128 位，16 字节）
        byte[] key = Hex.decode("12345678901234567890123456789012");

//        System.out.println(key.length);

        /**
         *  设置加密使用的 radix（基数，10 表示数字 0-9）  输出的byte[]数组，代表的是【0,1,2,3,4,5,6,7,8,9】的下标
         *  radix = 36 表示数字 0-9 和字母 a-z，输出的byte[]数组，代表的是【0,1,2,3,4,5,6,7,8,9,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,A,B,...,Z】的下标
         */
        int radix = 36;

        // 填充 tweak（可为空或任意字节数组）
        byte[] tweak = new byte[0];

        // 初始化加密引擎（FF1）
        FPEFF1Engine engine = new FPEFF1Engine();

        // 设置参数
        FPEParameters params = new FPEParameters(new KeyParameter(key), radix, tweak);

        byte[] encrypted = new byte[plaintext.length()];
        // 加密
        engine.init(true, params);
        char[] charArray = plaintext.toCharArray();
        byte[] input = new byte[charArray.length];
        for (int i = 0; i < charArray.length; i++) {
            input[i] = (byte) (charArray[i] - '0');
//            System.out.println(input[i]);
        }


//        plaintext.chars()
//                .map(c -> c - '0')
//                .mapToObj(c -> (byte) c)
//                .collect(Collectors.toList())
//                .stream()
//                .collect(ByteArrayCollector.toByteArray())


        engine.processBlock(input, 0, plaintext.length(), encrypted, 0);

        // 输出加密结果
        StringBuilder encryptedStr = new StringBuilder();
        for (byte b : encrypted) {
            System.out.println(b);
            encryptedStr.append((char) (b + '0'));
        }
        System.out.println(encryptedStr);


        // 解密
        engine.init(false, params);

        byte[] decrypted = new byte[charArray.length];
        engine.processBlock(encrypted, 0, plaintext.length(), decrypted, 0);

        // 输出解密结果
        StringBuilder decryptedStr = new StringBuilder();
        for (byte b : decrypted) {
            decryptedStr.append((char) (b + '0'));
        }
        System.out.println(decryptedStr);
    }


//    public static void main(String[] args) {
//        try {
//            // 配置参数
//            byte[] key = Hex.decode("00112233445566778899aabbccddeeff"); // 128-bit AES key
//            byte[] tweak = new byte[0]; // 可选的关联数据（无tweak）
//            String plaintext = "123456789012"; // 12位数字（例如信用卡号）
//            int radix = 10; // 数字基数
//
//            // 加密
//            String ciphertext = encrypt(key, tweak, plaintext, radix);
//            System.out.println("加密结果: " + ciphertext);
//
//            // 解密
//            String decrypted = decrypt(key, tweak, ciphertext, radix);
//            System.out.println("解密结果: " + decrypted);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static String encrypt(byte[] key, byte[] tweak, String plaintext, int radix) {
        BlockCipher cipher = new AESEngine();
        FPEFF1Engine fpeEngine = new FPEFF1Engine(cipher);

        fpeEngine.init(true, new FPEParameters(new KeyParameter(key), radix, tweak));

//        char[] plainChars = plaintext.toCharArray();
//
//        char[] cipherChars = new char[plainChars.length];


        byte[] plainChars = plaintext.getBytes();

        byte[] cipherChars = new byte[plainChars.length];

        fpeEngine.processBlock(plainChars, 1, 1, cipherChars, 1);

        return new String(cipherChars);
    }

    public static String decrypt(byte[] key, byte[] tweak, String ciphertext, int radix) {
        BlockCipher cipher = new AESEngine();
        FPEFF1Engine fpeEngine = new FPEFF1Engine(cipher);
        fpeEngine.init(false, new FPEParameters(new KeyParameter(key), radix, tweak));

//        char[] cipherChars = ciphertext.toCharArray();
//        char[] plainChars = new char[cipherChars.length];

        byte[] plainChars = ciphertext.getBytes();

        byte[] cipherChars = new byte[plainChars.length];

        fpeEngine.processBlock(cipherChars, 0, plainChars.length, cipherChars, cipherChars.length);

        return new String(plainChars);
    }

    // 可选：验证输入字符的有效性
    private static boolean validateInput(String input, String alphabet) {
        return input.chars().allMatch(c -> alphabet.indexOf(c) >= 0);
    }
}
