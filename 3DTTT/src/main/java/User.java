import java.util.ArrayList;

public class User {
	static int totalUsers = 0;
	private int userNum;
	private String userName;
	private String password;
	private int level;
	private double XP;
	private ArrayList<Powers> userPowers;
	
	
	public User(String name) {
		userNum = totalUsers++;
		userName = name;
		XP = 0.0;
		level = 1;
		userPowers = new ArrayList<>();
	}
	public User(String name, String pass) {
		userNum = totalUsers++;
		userName = name;
		password = pass;
		XP = 0.0;
		level = 1;
		userPowers = new ArrayList<>();
	}
	
	public User() {
		this("unk");
	}
	
	public String getName() {
		return userName;
	}
	
	public void setName(String name) {
		this.userName = name;
	}
	
	public double getXP() {
		return XP;
	}
	
	public void setXP(double newXP) {
		this.XP = newXP;
	}
	
	public void addXP(double newXP) {
		this.XP += newXP;
	}
	
	public int getLevel() {
		return level;
	}
	
	public void levelUp() {
		this.level++;
	}
	
	public int getNum() {
		return userNum;
	}
	
	public void setNum(int newNum) {
		this.userNum = newNum;
	}
	
	public ArrayList<Powers> getPowers() {
		return this.userPowers;
	}
	
	public void addPower(Powers newPow) {
		this.userPowers.add(newPow);
	}
	
	public boolean usePower(Powers pow) {
		int powerIndex = this.userPowers.indexOf(pow);
		if(powerIndex == -1) {
			return false;
		}
		userPowers.remove(powerIndex);
		return true;
	}
	
	public static int totalUsers() {
		return totalUsers;
	}

}
