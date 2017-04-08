import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class Encryption {
	/*public static void main(String[] args) throws UnsupportedEncodingException {
		byte[] toEncrypt = ("This is a string.").getBytes();
		//SecretKey key = getKey("pass");
		byte[] encrypted = encryptAES(toEncrypt, getKey("pass"));
		byte[] decrypted = decryptAES(encrypted, getKey("pass"));
		if(toEncrypt == decrypted) return;
		System.out.println(new String(decrypted, "UTF-8"));
	}*/
	
	public static byte[] encryptAES(byte[] data, SecretKey key) {
		try {
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		    IvParameterSpec ivs = new IvParameterSpec(iv);
			c.init(Cipher.ENCRYPT_MODE, key, ivs);
			
			byte[] encrypted = c.doFinal(data);
			
			return encrypted;
		} catch(Exception e) {
			return new byte[0];
		}
	}
	
	public static byte[] decryptAES(byte[] data, SecretKey key) {
		try {
			Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] iv = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		    IvParameterSpec ivs = new IvParameterSpec(iv);
			c.init(Cipher.DECRYPT_MODE, key, ivs);
			
			byte[] decrypted = c.doFinal(data);
			
			
			return decrypted;
		} catch(Exception e) {
			e.printStackTrace(System.out);
			return new byte[0];
		}
	}
	
	public static SecretKey getKey(String password) {
		try {
			byte[] key = password.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			return new SecretKeySpec(key, "AES");
		} catch(Exception e) {
			return null;
		}
	}
}
