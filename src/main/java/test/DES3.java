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
 * @version ����ʱ��2017��5��23��
 *
 * ��������3DES���ܹ�����
 */
public class DES3 {
	private final static String encoding = "UTF-8";

	/**
	 * ����������3DES����
	 * @author guoxk
	 * @createTime 2017��5��23�� ����9:03:44
	 *
	 * @param plainText  ����
	 * @param secretKey  ��Կ
	 * @param iv         ��������
	 * @return String    ����
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
	 * ���������� 3DES����
	 * @author guoxk
	 * @createTime 2017��5��23�� ����9:04:37
	 *
	 * @param encryptText ����
	 * @param secretKey   ��Կ
	 * @param iv          ��������
	 * @return String     ����
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
	 * �������������Է���
	 * @author guoxk
	 * @createTime 2017��5��23�� ����9:12:24
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			String key = "4SF6BJ3D8TDOT8NOCZ8T7P1K";
			String iv = "13002542";//IV length must be 8 bytes long
			//����
			String s = "����";
			byte[] bytes = s.getBytes();
			String encryptStr = DES3.encode(bytes, key, iv);
			System.out.println(encryptStr);
			//����
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