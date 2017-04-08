import java.io.File;

import javax.crypto.SecretKey;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class PasswordPrompt extends Application {
	private static String[] args;
	
	public static void main(String[] args) {
		PasswordPrompt.args = args;
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		HBox main = new HBox();
		TextField passField = new TextField();
		passField.setPromptText("Password");
		Button submit = new Button("Submit Password");
		passField.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
           	 	runAES(passField.getText());
           	 	stage.close();
           }
		});
		submit.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
           	 	runAES(passField.getText());
           	 	stage.close();
           }
		});
		
		passField.setAlignment(Pos.CENTER);
		submit.setAlignment(Pos.CENTER);
		
		main.getChildren().add(passField);
		main.getChildren().add(submit);
		
		stage.setTitle("AES Password");
        stage.setScene(new Scene(main, 260, 25));
        stage.show();
		
	}
	
	public static void runAES(String password) {
		try {
			if(args[0] == null || args[1] == null) {
				System.out.println("Missing Arguments");
				return;
			}
		} catch(ArrayIndexOutOfBoundsException e) {
			System.out.println("Missing Arguments");
			return;
		}
		String func = args[0];
		String targ = args[1];
		String pass = password;		
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
