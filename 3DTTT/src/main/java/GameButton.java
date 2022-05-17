import javafx.scene.control.Button;

public class GameButton extends Button {
    int x, y;
    boolean validMove = true;
    boolean selected = false;
    int player = -1;

    public GameButton(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // return valid bool
    public boolean getValid() {
        return validMove;
    }

    // set valid bool
    public void setValid(boolean v) {
        validMove = v;
    }

    // get x coord
    public int getX() {
        return x;
    }

    // get y coord
    public int getY() {
        return y;
    }

    // get player value
    public int getPlayer() {
        return player;
    }

    // set player value
    public void setPlayer(int p) {
        player = p;
    }

    // get selected
    public boolean getSelected() { return selected; }

    // set selected
    public void setSelected(boolean s) { selected = s; }
}
