import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	static Stage stage;
	private User user;
	@FXML private TextField tf1;
	@FXML private TextField tf2;
	@FXML private Text errorMsg;
	
	public void getToMenu(ActionEvent event) throws Exception {
		boolean userFound = false;
		
		try {
			File file = new File(getClass().getResource("textfiles/accounts.txt").getFile());
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = reader.readLine();
			while (line != null) {
				String[] arr = line.split(" : ");
				String tempUser = arr[0];
				String tempPass = arr[1];
				if (tf1.getText().equals(tempUser) && tf2.getText().equals(tempPass)) {
					userFound = true;
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			errorMsg.setOpacity(1);
			errorMsg.setText("Text file for accounts not found");
			System.out.println("didnt work");
		}
		
		if (userFound) {
			user = new User(tf1.getText(), tf2.getText());
	        FXMLLoader singleLoader = new FXMLLoader(getClass().getResource("fxml/Menu1.fxml"));
	        System.out.println(getClass());
	        Parent menuPane2 = singleLoader.load();
	        MainController menuController = singleLoader.getController();
	        Scene menuScene = new Scene(menuPane2);
			stage.setScene(menuScene);
			menuController.setUser(user);
		} else {
			errorMsg.setOpacity(1);
			errorMsg.setText("User not found");
			//user = new User("testUser", "testPass");
		}
	}
	public void registerUser(ActionEvent event) throws Exception {
		try {
			if (tf1.getText().equals("") || tf2.getText().equals("")) {
				errorMsg.setFill(Color.RED);
				errorMsg.setOpacity(1);
				errorMsg.setText("No username/password input");
			} else {
				System.out.println("whoops");
				boolean userExists = false;
				File file = new File(getClass().getResource("textfiles/accounts.txt").getFile());
				System.out.println(getClass().getResource("textfiles/accounts.txt").getFile());
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				
				if (file.length() != 0) {
					while (line != null) {
						String[] arr = line.split(" : ");
						String tempUser = arr[0];
						if (tf1.getText().equals(tempUser)) {
							userExists = true;
						}
						line = reader.readLine();
					}
				}
				if (userExists) {
					errorMsg.setFill(Color.RED);
					errorMsg.setOpacity(1);
					errorMsg.setText("Username already exists");
				} else {
					BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
					writer.append(tf1.getText() + " : " +  tf2.getText() + "\n");
					writer.close();
					errorMsg.setOpacity(1);
					errorMsg.setText("User registered!");
					errorMsg.setFill(Color.GREEN);
					
				}
				reader.close();
			}
		} catch (IOException e) {
			System.out.println("didnt work");
		}
	}
	public void registerUser2(ActionEvent event) throws Exception {
		user = new User("guestUser", "guestPass");
		System.out.println(getClass().getResource("images/red_x.png").getFile());
        FXMLLoader singleLoader = new FXMLLoader(getClass().getResource("fxml/Menu1.fxml"));
        System.out.println(getClass());
        Parent menuPane2 = singleLoader.load();
        MainController menuController = singleLoader.getController();
        Scene menuScene = new Scene(menuPane2);
		stage.setScene(menuScene);
		menuController.setUser(user);
	}
	public void setStage(Stage primaryStage) {
		stage = primaryStage;
		//stage.show();
	}
	public void getScene(Scene primaryScene) {
		stage.setScene(primaryScene);
		stage.show();
	}
}
