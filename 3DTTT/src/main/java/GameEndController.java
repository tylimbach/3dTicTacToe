import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameEndController implements Initializable {
	private User user;
	@FXML private Text t1;
	static Stage stage;
	
	public void getScene(Scene primaryScene) {
		stage.setScene(primaryScene);
		//stage.show();
	}
	public void setStage(Stage primaryStage) {
		stage = primaryStage;
		//stage.show();
	}
	public void setText(String toSet) {
		t1.setText(toSet);
	}
	public void setWin() {
		t1.setFill(Color.GOLD);
		t1.setFont(Font.font("Verdana", FontWeight.BOLD, 20));
	}
	public void startNewGame(ActionEvent event) throws Exception {
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("fxml/GameModeClassic.fxml"));
		Parent menuPane = menuLoader.load();
		ClassicController menuController = menuLoader.getController();
        Scene menuScene = new Scene(menuPane);
        menuController.setUser(user);
        menuController.setStage(stage);
        menuController.setParams(3, 1, 0);
        menuController.init();
        stage.setScene(menuScene);
		
	}
	public void goToShop(ActionEvent event) throws Exception {
        FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("fxml/MenuShop.fxml"));
        Parent menuPane = shopLoader.load();
        ShopController shopController = shopLoader.getController();
        shopController.setUser(user);
        Scene shopScene = new Scene(menuPane);
		stage.setScene(shopScene);
	}
	public void returnToMenu(ActionEvent event) throws Exception {
		
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("fxml/Menu1.fxml"));
		Parent menuPane = menuLoader.load();
		MainController menuController = menuLoader.getController();
		//Parent menuPane2 = menuLoader.load();
		menuController.setUser(user);
        Scene menuScene = new Scene(menuPane);
		menuController.getScene(menuScene);
	}
	public void setUser(User setUser) {
		user = setUser;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
