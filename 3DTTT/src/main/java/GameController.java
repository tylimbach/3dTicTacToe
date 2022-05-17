import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class GameController implements Initializable{
	
	private MainLogic logic;
	static Stage stage;
	private User user;
	Game game;
	@FXML private MenuButton gameMode;
	@FXML private MenuButton gridSize;
	@FXML private MenuButton aiNum;
	@FXML private MenuButton aiDiff;
	@FXML private MenuItem g1;
	@FXML private MenuItem g2;
	
	int gameplayMode = 0;
	int gridDim = 3;
	int aiAmt = 1;
	int aiScalar = 0;

    public void setStage(Stage s) {
        stage = s;
    }

	public void returnToMenu(ActionEvent event) throws Exception {
		
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("fxml/Menu1.fxml"));
		Parent menuPane = menuLoader.load();
		MainController menuController = menuLoader.getController();
		menuController.setUser(user);
		//Parent menuPane2 = menuLoader.load();
        Scene menuScene = new Scene(menuPane);
		menuController.getScene(menuScene);
	}
	public void classicGame() throws Exception {
		
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("fxml/GameModeClassic.fxml"));
		Parent menuPane = menuLoader.load();
		ClassicController menuController = menuLoader.getController();
        Scene menuScene = new Scene(menuPane);
        menuController.setUser(user);
        menuController.setStage(stage);
        menuController.setParams(gridDim, aiAmt, aiScalar);
        menuController.init();
        // menuController.setGrid();
        // menuController.getScene(menuScene);
        stage.setScene(menuScene);
    //    this.powerInventory(); issue with not working
	}
	
	public void cubeGame() throws Exception {
		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("fxml/GameModeCube.fxml"));
		Parent menuPane = menuLoader.load();
		CubeController menuController = menuLoader.getController();
        Scene menuScene = new Scene(menuPane);
        menuController.setUser(user);
        menuController.setStage(stage);
        menuController.setParams(gridDim, aiAmt, aiScalar);
        menuController.init();
		menuController.getScene(menuScene);
	}
	public void playGame(ActionEvent event) throws Exception {
		if (gameplayMode == 0) {
			classicGame();
		} else if (gameplayMode == 1) {
			cubeGame();
		}
	}
	
	public void changeMode(ActionEvent event) throws Exception {
		Object node = event.getSource();
		MenuItem b = (MenuItem)node;
		if (b.getText().equals("Classic")) {
			gameplayMode = 0;
			gameMode.setText("Gameplay: Classic");
		} else if (b.getText().equals("3D")){
			gameplayMode = 1;
			gameMode.setText("Gameplay: 3D");
		}
	}
	
	public void changeGrid(ActionEvent event) throws Exception {
		Object node = event.getSource();
		MenuItem b = (MenuItem)node;
		if (b.getText().equals("3x3")) {
			gridDim = 3;
			gridSize.setText("Grid Size: 3x3");
		} else if (b.getText().equals("4x4")){
			gridDim = 4;
			gridSize.setText("Grid Size: 4x4");
		} else if (b.getText().equals("5x5")){
			gridDim = 5;
			gridSize.setText("Grid Size: 5x5");
		}
	}
	
	public void changeAiNum(ActionEvent event) throws Exception {
		Object node = event.getSource();
		MenuItem b = (MenuItem)node;
		if (b.getText().equals("1")) {
			aiAmt = 1;
			aiNum.setText("AI Number: 1");
		} else if (b.getText().equals("2")){
			aiAmt = 2;
			aiNum.setText("AI Number: 2");
		} else if (b.getText().equals("3")){
			aiAmt = 3;
			aiNum.setText("AI Number: 3");
		}
	}
	
	public void changeAiDiff(ActionEvent event) throws Exception {
		Object node = event.getSource();
		MenuItem b = (MenuItem)node;
		if (b.getText().equals("Easy")) {
			aiScalar = 0;
			aiDiff.setText("AI Difficulty: Easy");
		} else if (b.getText().equals("Normal")){
			aiScalar = 1;
			aiDiff.setText("AI Difficulty: Normal");
		} else if (b.getText().equals("Hard")){
			aiScalar = 2;
			aiDiff.setText("AI Difficulty: Hard");
		}
	}

	public void exitGame(ActionEvent event) throws Exception {
        Platform.exit();
		System.exit(0);
	}
	
	public void setUser(User setUser) {
		user = setUser;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
