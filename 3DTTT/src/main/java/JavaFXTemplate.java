import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {

	static Stage stage;
	public static void main(String[] args) {
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		// intialization of member nodes
		primaryStage.setTitle("3D Tic-Tac-Toe");
		stage = primaryStage;
        FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("fxml/Login.fxml"));
        FXMLLoader menuLoader2 = new FXMLLoader(getClass().getResource("fxml/Menu1.fxml"));
        // FXMLLoader menuLoader3 = new FXMLLoader(getClass().getResource("fxml/GameModeClassic.fxml"));
        FXMLLoader menuLoader4 = new FXMLLoader(getClass().getResource("fxml/GameModeCube.fxml"));
        FXMLLoader menuLoader5 = new FXMLLoader(getClass().getResource("fxml/GameEnd.fxml"));
        Parent menuPane = menuLoader.load();
        Parent menuPane2 = menuLoader2.load();
        // Parent menuPane3 = menuLoader3.load();
        Parent menuPane4 = menuLoader4.load();
        Parent menuPane5 = menuLoader5.load();
		
		MainController menuController = menuLoader2.getController();
		menuController.setStage(stage);
		
		LoginController loginController = menuLoader.getController();
		loginController.setStage(stage);
		
//		ClassicController classicController = menuLoader3.getController();
//		classicController.setGrid();
//		classicController.setStage(stage);
		
		
		CubeController cubeController = menuLoader4.getController();
		cubeController.setStage(stage);
		
		GameEndController endController = menuLoader5.getController();
		endController.setStage(stage);
		
        Scene introScene = new Scene(menuPane);
		primaryStage.setScene(introScene);
		primaryStage.show();
		
	}
	
	

}
