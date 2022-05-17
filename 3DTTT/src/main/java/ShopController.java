import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ShopController implements Initializable {
	
	private User user;
	private Powers currentPower = Powers.ERROR;
	
	@FXML private ImageView i1;
	@FXML private ImageView i2;
	@FXML private ImageView i3;
	@FXML private Text t1;
	@FXML private Text powerList;
	@FXML private Text usernameText;
	@FXML private Text userLevel;
	@FXML private Text xpText;
	@FXML private Button purchaseButton;
	
	//MainController mainController = 
	
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
	public void buttonPress(MouseEvent event) throws Exception {
		Object node = event.getSource();
		ImageView b = (ImageView)node;
		double itemValue = Double.parseDouble(b.getAccessibleRoleDescription());
		if(user.getXP() >= itemValue) {
			purchaseButton.setDisable(false);
			t1.setText(b.getId() + "\nCost: " + b.getAccessibleRoleDescription());
		}
		else {
			purchaseButton.setDisable(true);
			t1.setText(b.getId() + "\nCost: " + b.getAccessibleRoleDescription() + "\nNot Enough XP!");
		}
		this.currentPower = currentPower(b.getId());
	}
	
	private Powers currentPower(String s) {
		Powers returnPower;
		switch(s) {
			case "BOMB": returnPower = Powers.BOMB;
				break;
			case "RANDOMIZE": returnPower = Powers.RANDOMIZE;
				break;
			case "REMOVE": returnPower = Powers.REMOVE;
				break;
			default: returnPower = Powers.ERROR;
		}
		return returnPower;
	}
	
	private void displayPowers() {
		int bomb = 0, randomize = 0, remove = 0, total = 0;
		ArrayList<Powers> powerList = this.user.getPowers();
		for(Powers e : powerList) {
			if(e.equals(Powers.BOMB)) {
				bomb++;
			}
			else if(e.equals(Powers.RANDOMIZE)) {
				randomize++;
			}
			else if(e.equals(Powers.REMOVE)) {
				remove++;
			}
		}
		total = bomb + randomize + remove;
		this.powerList.setText("Total Powers "+total+"\nBombs: "+bomb+"\nRandomize: "+randomize+"\nRemove: "+remove+"\n");
	}
	
	public void purchaseItem(ActionEvent event) throws Exception {
		double deductAmt = 0.0;
		double currentXP = this.user.getXP();
		switch(this.currentPower) {
			case BOMB: deductAmt = 250.0;
				break;
			default: deductAmt = -5.0;
		}
		this.user.addPower(currentPower);
		this.user.setXP(currentXP - deductAmt);
		this.xpText.setText("XP: " + user.getXP());
		if(this.user.getXP() < deductAmt) {
			this.purchaseButton.setDisable(true);
		}
		this.displayPowers();
	}
	
	public void setUser(User setUser) {
		user = setUser;
		usernameText.setText("Username: " + user.getName());
		userLevel.setText("Level: " + user.getLevel());
		xpText.setText("XP: " + user.getXP());
		this.displayPowers();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		
	}
}
