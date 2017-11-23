package test;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.lang.StringUtils;

import com.sun.org.apache.xml.internal.security.utils.Base64;

/**
 * @author guoxk
 *
 * @version 创建时间2017年5月23日
 *
 * 类描述：3DES加密工具类
 */
public class DES3 {
	private final static String encoding = "UTF-8";

	/**
	 * 方法描述：3DES加密
	 * @author guoxk
	 * @createTime 2017年5月23日 上午9:03:44
	 *
	 * @param plainText  明文
	 * @param secretKey  密钥
	 * @param iv         加密向量
	 * @return String    密文
	 * @throws Exception
	 */
	public static String encode(String plainText, String secretKey, String iv)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/PKCS5Padding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
		byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
		return Base64.encode(encryptData);
	}

	/**
	 * 方法描述： 3DES解密
	 * @author guoxk
	 * @createTime 2017年5月23日 上午9:04:37
	 *
	 * @param encryptText 密文
	 * @param secretKey   密钥
	 * @param iv          加密向量
	 * @return String     明文
	 * @throws Exception
	 */
	public static String decode(String encryptText, String secretKey, String iv)
			throws Exception {
		if (StringUtils.isBlank(encryptText) || StringUtils.isBlank(secretKey)) {
			return "";
		}
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("desede");
		deskey = keyfactory.generateSecret(spec);
		Cipher cipher = Cipher.getInstance("desede/CBC/NoPadding");
		IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
		cipher.init(Cipher.DECRYPT_MODE, deskey, ips);
		byte[] decryptData = cipher.doFinal(Base64.decode(encryptText));
		return new String(decryptData, encoding).trim();
	}
	/**
	 * 方法描述：测试方法
	 * @author guoxk
	 * @createTime 2017年5月23日 上午9:12:24
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String key = "4SF6BJ3D8TDOT8NOCZ8T7P1K";
			String iv = "13002542";//IV length must be 8 bytes long
			//加密
			String s = "明文";
			byte[] bytes = s.getBytes();
			String encryptStr = DES3.encode(bytes, key, iv);
			System.out.println(encryptStr);
			//解密
			String decryptStr = DES3.decode(encryptStr, key, iv);
//			byte[] bytes2 = decryptStr.getBytes();
//			System.out.println(new String (bytes2));
			System.out.println(decryptStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String encode(byte[] bytes, String key, String iv) throws Exception {
		String string = new String(bytes);
		
		return DES3.encode(string, key, iv);
	}
}