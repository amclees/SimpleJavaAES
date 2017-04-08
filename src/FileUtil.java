import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.crypto.SecretKey;

import org.apache.commons.io.IOUtils;



public class FileUtil {
	protected static byte[] readPlainFile(File file) {
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(file));
			return IOUtils.toByteArray(in);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void store(byte[] data, File location, SecretKey key) {
		byte[] encryptedData = Encryption.encryptAES(data, key);
		
		storePlain(encryptedData, location);
	}
	
	public static void storePlain(byte[] data, File location) {
		BufferedOutputStream out = null;
		try {
			out = new BufferedOutputStream(new FileOutputStream(location));
			
			out.write(data);
			
			out.close();
		} catch(IOException e) {
			System.out.println("Failed to write file.");
		}
	}
	
	public static byte[] read(File file, SecretKey key) {
		
		byte[] fileBytes = readPlainFile(file);
			
		byte[] decrypted = Encryption.decryptAES(fileBytes, key);
			
		return decrypted;
		
	}
}
