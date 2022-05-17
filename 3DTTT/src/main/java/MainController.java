import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainController implements Initializable{
	//this will be one of our controllers
	private MainLogic logic;
	static Stage stage;
	static private User user;
	@FXML private Text usernameText;
	
	public void singleplayerClick(ActionEvent event) throws Exception {
		
		FXMLLoader singleLoader = new FXMLLoader(getClass().getResource("fxml/MenuSingleplayer.fxml"));
        Parent menuPane1 = singleLoader.load();
        Scene singleScene = new Scene(menuPane1);
        GameController menuController = singleLoader.getController();
        menuController.setUser(user);
        menuController.setStage(stage);
		stage.setScene(singleScene);
	}
	public void multiplayerClick(ActionEvent event) throws Exception {
        FXMLLoader multiLoader = new FXMLLoader(getClass().getResource("fxml/MenuMultiplayer.fxml"));
        Parent menuPane = multiLoader.load();
        Scene multiScene = new Scene(menuPane);
        MainController menuController = multiLoader.getController();
        menuController.setUser2(user);
		stage.setScene(multiScene);
	}
	public void shopClick(ActionEvent event) throws Exception {
        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("fxml/MenuShop.fxml"));
        Parent menuPane = shopLoader.load();
        ShopController shopController = shopLoader.getController();
        shopController.setUser(user);
        Scene shopScene = new Scene(menuPane);
		stage.setScene(shopScene);
	}
	public void menuSettingsClick(ActionEvent event) throws Exception {
        FXMLLoader setLoader = new FXMLLoader(getClass().getResource("fxml/MenuSettings.fxml"));
        Parent menuPane = setLoader.load();
        Scene setScene = new Scene(menuPane);
        MainController menuController = setLoader.getController();
        menuController.setUser2(user);
		stage.setScene(setScene);
	}
	public void hostClick(ActionEvent event) throws Exception {
        FXMLLoader hostLoader = new FXMLLoader(getClass().getResource("fxml/MenuMultiplayerHost.fxml"));
        Parent menuPane = hostLoader.load();
        Scene hostScene = new Scene(menuPane);
        MainController menuController = hostLoader.getController();
        menuController.setUser2(user);
		stage.setScene(hostScene);
	}
	public void lobbyClick(ActionEvent event) throws Exception {
        FXMLLoader lobbyLoader = new FXMLLoader(getClass().getResource("fxml/MenuMultiplayerLobby.fxml"));
        Parent menuPane = lobbyLoader.load();
        Scene lobbyScene = new Scene(menuPane);
        MainController menuController = lobbyLoader.getController();
        menuController.setUser2(user);
		stage.setScene(lobbyScene);
	}
	
	public void returnToMenu(ActionEvent event) throws Exception {
        FXMLLoader singleLoader = new FXMLLoader(getClass().getResource("fxml/Menu1.fxml"));
        Parent menuPane2 = singleLoader.load();
        Scene menuScene = new Scene(menuPane2);
        MainController menuController = singleLoader.getController();
        menuController.setUser(user);
		stage.setScene(menuScene);
	}
	public void exitGame(ActionEvent event) throws Exception {
        Platform.exit();
		System.exit(0);
	}
	public void getScene(Scene primaryScene) {
		stage.setScene(primaryScene);
		//stage.show();
	}
	public void setStage(Stage primaryStage) {
		stage = primaryStage;
		//stage.show();
	}
	public void setUser(User setUser) {
		user = setUser;
		usernameText.setText("Username: " + user.getName());
		//stage.show();
	}
	public void setUser2(User setUser) {
		user = setUser;
		//usernameText.setText("Username: " + user.getName());
		//stage.show();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}