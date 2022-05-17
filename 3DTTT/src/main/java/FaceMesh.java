import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;

public class FaceMesh extends MeshView {
    int id = -1;
    int f, x, y;
    boolean validMove = true;
    boolean selected = false;
    int player = -1;

    boolean outer = false;

    FaceMesh() {
        super();
        f = -1;
        x = -1;
        y = -1;
    }

    FaceMesh(TriangleMesh tm) {
        super(tm);
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setOuter(boolean o) {
        this.outer = o;
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

    // get face coord
    public int getFace() {
        return f;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setFace(int f) {
        this.f = f;
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
    public boolean getSelected() {
        return selected;
    }

    // set selected
    public void setSelected(boolean s) {
        selected = s;
    }

    public int getID() {
        return id;
    }

    public boolean getOuter() {
        return outer;
    }
}
