import java.io.File;

import javax.crypto.SecretKey;


public class Console {
	public static void main(String[] args) {
		try {
			if(args[0] == null || args[1] == null || args[2] == null) {
				System.out.println("Missing Arguments");
				return;
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Missing Arguments");
			return;
		}
		String func = args[0];
		String pass = args[1];
		String targ = args[2];
		File target = new File(targ);
		
		SecretKey key = Encryption.getKey(pass);
		
		if(func.equals("e")) {
			File output = new File(target.getAbsolutePath() + ".aes");
			if(output.exists()) return;
			
			FileUtil.store(FileUtil.readPlainFile(target), output, key);
		} else if(func.equals("d")) {
			if(!target.getAbsolutePath().endsWith(".aes")) return;
			String nonAES = target.getAbsolutePath().substring(0, target.getAbsolutePath().length() - 4);
			String[] brokenTarget = nonAES.split("\\.");
			StringBuilder filePathMaker = new StringBuilder();
			for(int i = 0; i < brokenTarget.length; i++) {
				if(i == brokenTarget.length - 1) filePathMaker.append("dec." + brokenTarget[i]);
				else filePathMaker.append(brokenTarget[i] + ".");
			}
			
			
			File decryptedFile = new File(filePathMaker.toString());
			
			if(decryptedFile.exists()) return;
			FileUtil.storePlain(FileUtil.read(target, key), decryptedFile);
		} else {
			System.out.println("Invalid Function");
		}
	}
}
