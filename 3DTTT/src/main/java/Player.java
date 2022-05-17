public class Player {
    private final int playerID;
    private User user;

    public int getPlayerID() {
        return playerID;
    }
    
    public User getUser() {
    	return user;
    }
    
    public void setUser(User newUser) {
    	this.user = newUser;
    }

    public Player(int id) {
        this.playerID = id;
        this.user = new User();
    }
    
    public Player(int id, User playerUser) {
    	this.playerID = id;
    	this.user = playerUser;
    }
    
    public Player(User playerUser) {
    	this.playerID = playerUser.getNum();
    	user = playerUser;
    }

    // TODO:
}
