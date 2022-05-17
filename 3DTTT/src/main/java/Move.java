public class Move {
    int x;
    int y;
    int face;
    int playerID;

    public Move(int playerID, int x, int y) {
        this.x = x;
        this.y = y;
        this.face = -1;
        this.playerID = playerID;
    }
    public Move(int playerID, int x, int y, int face) {
        this.x = x;
        this.y = y;
        this.face = face;
        this.playerID = playerID;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getFace() {
        return face;
    }

    public int getPlayerID() { return playerID; }
}
