package 解密;

import com.bjsasc.security.core.SM4Util;
import org.bouncycastle.pqc.math.linearalgebra.ByteUtils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @CreateTime: 2025年 07月 18日 11:18
 * @Description:
 * @Author: MR.YU
 */
public class Decrypt {

    public static String hexKey = "c6a685d830d27cbcacd2ae658763b1f8";

    public static void main(String[] args) throws Exception {
        System.out.println(decrypt("9eb6e83e3ce2a94f985c067aad4ee7c423549c8c158995a3ceb9a6877bd24d3b"));
    }


    public static String decrypt( String encryptDataHex) throws Exception {
        byte[] encryptDataByte = ByteUtils.fromHexString(encryptDataHex);
        byte[] plainTextByte = decryptByte(encryptDataByte);
        return new String(plainTextByte, "UTF-8");
    }

    public static byte[] decryptByte(byte[] encryptDataByte) throws Exception, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException, InvalidAlgorithmParameterException {
        byte[] keyData = ByteUtils.fromHexString(hexKey);
        byte[] iv = new byte[16];
        byte[] splitEncryptDataByte = new byte[encryptDataByte.length - 16];
        System.arraycopy(encryptDataByte, 0, iv, 0, 16);
        System.arraycopy(encryptDataByte, 16, splitEncryptDataByte, 0, splitEncryptDataByte.length);
        return SM4Util.decrypt_Cbc_Padding(keyData, iv, splitEncryptDataByte);
    }
}
