import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import javafx.scene.shape.MeshView;
// import org.fxyz3d.shapes.primitives.CuboidMesh;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CubeController implements Initializable {
    private User user;
    static Stage stage;
    @FXML
    VBox vbox;
    Group g3d;
    private SubScene scene3d;

    private CubeGame game;
    EasyAI[] bots;

    Powers selectedPower = null;

    @FXML private ImageView bombImage;
    @FXML private ImageView randomizeImage;
    @FXML private ImageView removeImage;

    @FXML private Button moveButton;
    @FXML private Button powerButton;

    private static double VIEWPORT_SIZE = 540;
    private static double MODEL_SCALE_FACTOR = 50;
    private static double MODEL_X_OFFSET = 0;
    private static double MODEL_Y_OFFSET = 0;
    private static double MODEL_Z_OFFSET = VIEWPORT_SIZE;

    int dim;
    int aiAmt;
    int aiDifficulty;

    @FXML
    Label label3;
    @FXML Label label4;

    RotateTransition rotate;

    boolean gameOver = false;
    int winner = -1;

    long startTime;
    long elapsedTime;

    boolean rotating = false;
    RotateTransition rt;

    FaceMesh queued;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bombImage.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (selectedPower == Powers.BOMB) {
                    selectedPower = null;
                    powerButton.setDisable(true);
                    bombImage.setStyle("");

                }
                else if (user.getPowers().contains(Powers.BOMB)){
                    selectedPower = Powers.BOMB;
                    powerButton.setDisable(false);
                    bombImage.setStyle("");
                }
            }
        });
    }

    class SmartGroup extends Group {
        Rotate rotate;
        Transform transform = new Rotate();

        SmartGroup(Group g) {
            super(g);
            for (Transform t : g.getTransforms()){
                transform = transform.createConcatenation(t);
            }
        }

        void rotateByX(int ang) {
            Point3D rotAxis;
            try {
                rotAxis = transform.inverseDeltaTransform(Rotate.X_AXIS);
                float center = ((CubeBoardView) g3d.getChildren().get(0)).getCenter();
                rotate = new Rotate(ang, center, center, center, rotAxis);

                transform = transform.createConcatenation(rotate);

                this.getTransforms().clear();
                this.getTransforms().addAll(transform);
            } catch (NonInvertibleTransformException ex) {
                throw new IllegalStateException(ex);
            }
        }

        void rotateByY(int ang) {
            Point3D rotAxis;
            try {
                rotAxis = transform.inverseDeltaTransform(Rotate.Y_AXIS);
                float center = ((CubeBoardView) g3d.getChildren().get(0)).getCenter();
                rotate = new Rotate(ang, center, center, center, rotAxis);

                transform = transform.createConcatenation(rotate);
                this.getTransforms().clear();
                this.getTransforms().addAll(transform);
            } catch (NonInvertibleTransformException ex) {
                throw new IllegalStateException(ex);
            }
        }
    }

    private SubScene createScene3D(Group group) {
        scene3d = new SubScene(g3d, VIEWPORT_SIZE * 1.5, VIEWPORT_SIZE, true, SceneAntialiasing.BALANCED);
        scene3d.setFill(Color.BLACK);
        scene3d.setCamera(new PerspectiveCamera());
        return scene3d;
    }

    public void init() {
        // g3d = buildScene();
        g3d = new SmartGroup(new CubeBoardView(dim));

        float center = ((CubeBoardView) g3d.getChildren().get(0)).getCenter();

        g3d.setTranslateX(VIEWPORT_SIZE*1.5 / 2 + MODEL_X_OFFSET - center * MODEL_SCALE_FACTOR/2);
        g3d.setTranslateY(VIEWPORT_SIZE / 2 + MODEL_Y_OFFSET - center * MODEL_SCALE_FACTOR/2);
        g3d.setTranslateZ(VIEWPORT_SIZE / 2 + MODEL_Z_OFFSET - center * MODEL_SCALE_FACTOR/2);
        g3d.setScaleX(MODEL_SCALE_FACTOR);
        g3d.setScaleY(MODEL_SCALE_FACTOR);
        g3d.setScaleZ(MODEL_SCALE_FACTOR);

        moveButton.setDisable(true);
        powerButton.setDisable(true);

        bots = new EasyAI[aiAmt];
        for (int i = 0; i < aiAmt; ++i) {
            bots[i] = new EasyAI(i+1);
        }
        game = new CubeGame(dim, aiAmt+1);

        user.addPower(Powers.BOMB);
        setUser(user);

        scene3d = createScene3D(g3d);

        vbox.getScene().getRoot().requestFocus();
        vbox.getScene().getRoot().setFocusTraversable(true);

        stage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case UP:    ((SmartGroup) g3d).rotateByX(10); System.out.println("up"); break;
                case DOWN:  ((SmartGroup) g3d).rotateByX(-10); System.out.println("down"); break;
                case LEFT:  ((SmartGroup) g3d).rotateByY(-10); System.out.println("left"); break;
                case RIGHT: ((SmartGroup) g3d).rotateByY(10); System.out.println("right"); break;
            }
        });

        scene3d.setOnMouseClicked((event)->{
            PickResult res = event.getPickResult();
            if (res.getIntersectedNode() instanceof FaceMesh) {
//                ((MeshView)res.getIntersectedNode()).setMaterial(
//                        new PhongMaterial(Color.BLACK)
//                );
                if (((FaceMesh) res.getIntersectedNode()).getOuter())
                    attemptMove((FaceMesh) res.getIntersectedNode());
            }
            scene3d.getRoot().requestFocus();
        });

        g3d.getChildren().add(new AmbientLight(Color.LIGHTGREY));
        vbox.getChildren().add(0, scene3d);

        if (aiAmt < 3) {
            label4.setText("");
        }
        if (aiAmt < 2) {
            label3.setText("");
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

    public boolean attemptMove(FaceMesh m) {
        if (queued == m) {
            return true;
        }
        if (queued != null) {
            queued.setMaterial(new PhongMaterial());
        }

        int x = m.getX();
        int y = m.getY();
        int f = m.getFace();

        Move newMove = new Move(0, x, y, f);

        if (game.gameBoard.isMoveValid(newMove)) {
            moveButton.setDisable(false);
            queued = m;
            Image glowImage = generateImage(1, 0, 0, 1);
            PhongMaterial mat = new PhongMaterial();
            mat.setSelfIlluminationMap(glowImage);
            m.setMaterial(mat);
            return true;
        } else {
            moveButton.setDisable(true);
        }
        return false;
    }

    public Image generateImage(double red, double green, double blue, double opacity) {
        WritableImage img = new WritableImage(2, 2);
        PixelWriter pw = img.getPixelWriter();

        Color color = Color.color(red, green, blue, opacity);
        Color color2 = Color.color(red/3, green/3, blue/3, opacity);

        pw.setColor(0, 0, color);
        pw.setColor(1,0, color2);
        pw.setColor(0, 1, color2);
        pw.setColor(1,1, color);
        return img ;
    }

    public void simulateAI() throws IOException {
        System.out.println("Entering simulateAI()");
        for (int i = 0; i < aiAmt; ++i) {
            System.out.println("AI " + i);
            if (gameEndCheck()) return;
            Move next = bots[i].nextMove(((CubeBoard)game.gameBoard).getTiles());
            FaceMesh m = ((CubeBoardView)(g3d.getChildren().get(0))).findFaceAtCoords(next.getFace(), next.getX(), next.getY());
            game.makeMove(next);

            int player = next.getPlayerID();
            if (player == 1) {
                m.setMaterial(new PhongMaterial(Color.BLUE));
            } else if (player == 2) {
                m.setMaterial(new PhongMaterial(Color.GREEN));
            } else if (player == 3) {
                m.setMaterial(new PhongMaterial(Color.YELLOW));
            }
            //            updateButtons();
        }
    }
//
//    public void updateButtons() {
//        Tile[][] tiles = ((ClassicBoard)game.gameBoard).getTiles();
//        for (GameButton[] row : buttonGrid) {
//            for (GameButton button : row) {
//                Tile tile = tiles[button.getX()][button.getY()];
//                if (tile.isDestroyed()) {
//                    button.setStyle("-fx-background-color: #000000; -fx-border-color: #000000; -fx-border-width: 5; ");
//                    button.setValid(false);
//                    button.setSelected(false);
//                }
//                else if (tile.getMarkedBy() == -1) {
//                    button.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width: 5; ");
//                    button.setValid(true);
//                    button.setSelected(false);
//                }
//                else if (tile.getMarkedBy() == 0) {
//                    button.setStyle("-fx-background-color: #ff0000; -fx-border-color: #000000; -fx-border-width: 5; ");
//                    button.setValid(false);
//                    button.setSelected(false);
//                }
//                else if (tile.getMarkedBy() == 1) {
//                    button.setStyle("-fx-background-color: #00ff00; -fx-border-color: #000000; -fx-border-width: 5; ");
//                    button.setValid(false);
//                    button.setSelected(false);
//                }
//                else if (tile.getMarkedBy() == 2) {
//                    button.setStyle("-fx-background-color: #0000ff; -fx-border-color: #000000; -fx-border-width: 5; ");
//                    button.setValid(false);
//                    button.setSelected(false);
//                }
//                else if (tile.getMarkedBy() == 3) {
//                    button.setStyle("-fx-background-color: #ffff00; -fx-border-color: #000000; -fx-border-width: 5; ");
//                    button.setValid(false);
//                    button.setSelected(false);
//                }
//            }
//        }
//    }
//
    public void sendMove(ActionEvent event) throws Exception {
        selectedPower = null;

        int x = queued.getX();
        int y = queued.getY();
        int f = queued.getFace();
        game.gameBoard.makeMove(new Move(0, x, y, f));
        queued.setMaterial(new PhongMaterial(Color.RED));
        moveButton.setDisable(true);
        queued = null;
        if (gameEndCheck()) return; // TODO
//            updateButtons();
        simulateAI();
        if (gameEndCheck()) return; // TODO
        scene3d.requestFocus();
    }
//
    public void usePower() throws Exception {
        if (selectedPower == null) {
            return;
        }

        int x = queued.getX();
        int y = queued.getY();
        int f = queued.getFace();
        ((CubeBoard)(game.gameBoard)).getTiles()[f][x][y].setDestroyed(true);
        queued.setMaterial(new PhongMaterial(Color.BLACK));

        queued = null;
        //updateButtons();
        user.usePower(Powers.BOMB);
        scene3d.requestFocus();

        powerButton.setDisable(true);
        moveButton.setDisable(true);
        if (gameEndCheck()) return;

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

//    public void rotateCube(char direction, boolean active) {
//        if (active) {
//            if (!rotating) {
//                startTime = System.currentTimeMillis();
//                rotating = true;
//            }
//            else {
//                elapsedTime = System.currentTimeMillis() - startTime;
//                if (elapsedTime > 100) {
//
//                    ((CubeView) g3d).rotateByX();
//                }
//            }
//        rotate = new RotateTransition(Duration.seconds(4), g3d);
//
//        switch (direction) {
//            case 'd':
//                rotate.setAxis(Rotate.X_AXIS);
//                rotate.setByAngle(360);
//                break;
//            case 'r':
//                rotate.setAxis(Rotate.Y_AXIS);
//                rotate.setByAngle(-360);
//                break;
//            case 'l':
//                rotate.setAxis(Rotate.Y_AXIS);
//                rotate.setByAngle(360);
//                break;
//            case 'u':
//                rotate.setAxis(Rotate.X_AXIS);
//                rotate.setByAngle(-360);
//                break;
//        }
//        rotate.setAxis(g3d.getRotationAxis());
//        rotate.setFromAngle(g3d.getRotate());
//        rotate.setInterpolator(Interpolator.LINEAR);
//        rotate.setCycleCount(RotateTransition.INDEFINITE);
//
//        rotate.play();
//    }

//    public void rotateCube() {
//        rotating = !rotating;
//        if (rotating) {
//            rt.play();
//        } else {
//            rt.pause();
//        }
//    }

    public void setParams(int grids, int ais, int diff) {
        dim = grids;
        aiAmt = ais;
        aiDifficulty = diff;

        MODEL_Y_OFFSET = dim * MODEL_SCALE_FACTOR / 2;
        MODEL_X_OFFSET = dim * MODEL_SCALE_FACTOR / 2;

        System.out.println(dim);
        System.out.println(aiAmt);
        System.out.println(aiDifficulty);
    }
}
