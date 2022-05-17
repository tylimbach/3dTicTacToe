import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ClassicController implements Initializable {
	private User user;
	static Stage stage;
	GridPane grid;
    @FXML VBox vbox;
	private GameButton[][] buttonGrid;

    private ClassicGame game;
	EasyAI[] bots;

    Powers selectedPower = null;

	@FXML private ImageView bombImage;
	@FXML private ImageView randomizeImage;
	@FXML private ImageView removeImage;

    @FXML
    Label label3;
    @FXML Label label4;

    String[] styles = {"-fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 5; ",
            "-fx-background-color: #00ff00; -fx-border-color: #000000; -fx-border-width: 5; ",
            "-fx-background-color: #0000ff; -fx-border-color: #000000; -fx-border-width: 5; ",
            "-fx-background-color: #777700; -fx-border-color: #000000; -fx-border-width: 5; "};
	
	int dim;
	int aiAmt;
	int aiDifficulty;

    boolean gameOver = false;
    int winner = -1;
	
	Image img = new Image(getClass().getResource("images/red_x.png").toString());
	Image img2 = new Image(getClass().getResource("images/blueo.png").toString());
	ImageView viewPlayerSelect = new ImageView(img);
    ImageView viewX1, viewX2, viewX3, viewX4, viewX5 = new ImageView(img);
    
    ImageView viewO1 = new ImageView(img2);
    ImageView viewO2, viewO3, viewO4 = new ImageView(img2);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grid = new GridPane();

        bombImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedPower == Powers.BOMB)
                    selectedPower = null;
                else if (user.getPowers().contains(Powers.BOMB)){
                    selectedPower = Powers.BOMB;
                }
            }
        });
    }

    public void init() {
        buttonGrid = new GameButton[dim][dim];
        
        for (int row = 0; row < dim; row++) {
            for (int col = 0; col < dim; col++) {
                int xLoc = row;
                int yLoc = col;
                GameButton b = new GameButton(row, col);
                
                b.setPrefHeight(594);
                b.setPrefWidth(804);
                b.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #000000; -fx-border-width: 5; ");
                b.setOnAction(e->attemptMove(xLoc, yLoc));
                //b.setMaxHeight(1000);
                //b.setMaxWidth(1000);
                grid.add(b, col, row);

                buttonGrid[row][col] = b;
            }
        }
        vbox.setPrefWidth(400);
        vbox.getChildren().add(0, grid);

        bots = new EasyAI[aiAmt];
        for (int i = 0; i < aiAmt; ++i) {
            bots[i] = new EasyAI(i+1);
        }
        game = new ClassicGame(dim, aiAmt+1);

        if (aiAmt < 3) {
            label4.setText("");
        }
        if (aiAmt < 2) {
            label3.setText("");
        }

        user.addPower(Powers.BOMB);
        setUser(user);
    }

    public boolean attemptMove(int x, int y) {
        GameButton b = buttonGrid[x][y];
        if (!b.getValid()) {
            return false;
        }
        else if (!b.getSelected()) {
            for (GameButton[] row : buttonGrid) {
                for (GameButton button : row) {
                    if (button.getSelected()) {
                        button.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 5; ");
                        button.setSelected(false);
                    }
                }
            }
            b.setStyle("-fx-background-color: #ff0000;");
            b.setSelected(true);
        }
        else {
            b.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 5; ");
            b.setSelected(false);
        }
        return true;
    }

    public void simulateAI() throws IOException {
        System.out.println("Entering simulateAI()");
        for (int i = 0; i < aiAmt; ++i) {
            System.out.println("AI " + i);
            if (gameEndCheck()) return;
            game.makeMove(bots[i].nextMove(((ClassicBoard)game.gameBoard).getTiles()));
            updateButtons();
        }
    }

    public void updateButtons() {
        Tile[][] tiles = ((ClassicBoard)game.gameBoard).getTiles();
        for (GameButton[] row : buttonGrid) {
            for (GameButton button : row) {
                Tile tile = tiles[button.getX()][button.getY()];
                if (tile.isDestroyed()) {
                    button.setStyle("-fx-background-color: #000000; -fx-border-color: #000000; -fx-border-width: 5; ");
                    button.setValid(false);
                    button.setSelected(false);
                }
                else if (tile.getMarkedBy() == -1) {
                    button.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 5; ");
                    button.setValid(true);
                    button.setSelected(false);
                }
                else if (tile.getMarkedBy() == 0) {
                    button.setStyle("-fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 5; ");
                    button.setValid(false);
                    button.setSelected(false);
                }
                else if (tile.getMarkedBy() == 1) {
                    button.setStyle("-fx-background-color: #00ff00; -fx-border-color: #000000; -fx-border-width: 5; ");
                    button.setValid(false);
                    button.setSelected(false);
                }
                else if (tile.getMarkedBy() == 2) {
                    button.setStyle("-fx-background-color: #0000ff; -fx-border-color: #000000; -fx-border-width: 5; ");
                    button.setValid(false);
                    button.setSelected(false);
                }
                else if (tile.getMarkedBy() == 3) {
                    button.setStyle("-fx-background-color: #ffff00; -fx-border-color: #000000; -fx-border-width: 5; ");
                    button.setValid(false);
                    button.setSelected(false);
                }
            }
        }
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
	public void exitGame(ActionEvent event) throws Exception {
        System.exit(0);
	}

	public void setUser(User setUser) {
		user = setUser;
		ArrayList<Powers> powerList = user.getPowers();
		if(!powerList.contains(Powers.BOMB)) {
			this.bombImage.setOpacity(0.3);
		} else {
            this.bombImage.setOpacity(1.0);
        }
		if(!powerList.contains(Powers.RANDOMIZE)) {
			this.randomizeImage.setOpacity(0.3);
		} else {
            this.randomizeImage.setOpacity(1.0);
        }
		if(!powerList.contains(Powers.REMOVE)) {
			this.removeImage.setOpacity(0.3);
		} else {
            this.removeImage.setOpacity(1.0);
        }
	}
	public void getScene(Scene primaryScene) {
		stage.setScene(primaryScene);
		//stage.show();
	}
	public void setStage(Stage primaryStage) {
		stage = primaryStage;
		//stage.show();
	}
	public void setParams(int grids, int ais, int diff) {
		dim = grids;
		aiAmt = ais;
		aiDifficulty = diff;
		System.out.println(dim);
		System.out.println(aiAmt);
		System.out.println(aiDifficulty);
	}
	public void sendMove(ActionEvent event) throws Exception {
        boolean success = false;
        selectedPower = null;
        for (GameButton[] row : buttonGrid) {
            for (GameButton button : row) {
                if (button.getSelected()) {
                    button.setStyle("-fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 5; ");
                    success = game.makeMove(new Move(0, button.getX(), button.getY()));
                }
            }
        }
        if (success) {
            if (gameEndCheck()) return; // TODO
            updateButtons();
            simulateAI();
            if (gameEndCheck()) return; // TODO
        }
	}

    public void usePower() {
        if (selectedPower == null) {
            return;
        }
        for (GameButton[] row : buttonGrid) {
            for (GameButton button : row) {
                if (button.getSelected()) {
                    button.setStyle("-fx-background-color: #000000; -fx-border-color: #000000; -fx-border-width: 5; ");
                    ((ClassicBoard)(game.gameBoard)).getTiles()[button.getX()][button.getY()].setDestroyed(true);
                    updateButtons();
                    user.usePower(Powers.BOMB);
                }
            }
        }
        setUser(user);
    }

    public boolean gameEndCheck() throws IOException {
        if (game.isGameOver()) {
            if (game.getWinner() == -1) {
                System.out.println("Tie! No spaces left");
        		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("fxml/GameEnd.fxml"));
        		Parent menuPane = menuLoader.load();
        		GameEndController menuController = menuLoader.getController();
        		//Parent menuPane2 = menuLoader.load();
        		menuController.setUser(user);
                Scene menuScene = new Scene(menuPane);
                
                menuController.setText("Tie! No spaces left");
                
        		menuController.getScene(menuScene);
            }
            else if (game.getWinner() == 0) {
                System.out.println("Player won!");
                user.addXP(100.0);
                user.levelUp();
        		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("fxml/GameEnd.fxml"));
        		Parent menuPane = menuLoader.load();
        		GameEndController menuController = menuLoader.getController();
        		//Parent menuPane2 = menuLoader.load();
        		menuController.setUser(user);
                Scene menuScene = new Scene(menuPane);
                
                menuController.setText(user.getName()+" won!\nLevel Up!!");
                menuController.setWin();
                
        		menuController.getScene(menuScene);
        		
        		
        		
            }
            else {
                System.out.println("AI #" + game.getWinner() + " won");
                String toPrint = "AI #" + game.getWinner() + " won!";
                double currentXP = user.getXP();
                currentXP *= 0.9;
                user.setXP(currentXP);
        		FXMLLoader menuLoader = new FXMLLoader(getClass().getResource("fxml/GameEnd.fxml"));
        		Parent menuPane = menuLoader.load();
        		GameEndController menuController = menuLoader.getController();
        		//Parent menuPane2 = menuLoader.load();
        		menuController.setUser(user);
                Scene menuScene = new Scene(menuPane);
                
                menuController.setText(toPrint);
                
        		menuController.getScene(menuScene);
                
            }
            return true;
        } else {
            return false;
        }
    }
}
