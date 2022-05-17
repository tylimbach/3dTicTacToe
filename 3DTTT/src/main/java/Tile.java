public class Tile {
    private boolean empty;
    private boolean destroyed;
    private int markedBy;

    public Tile() {
        empty = true;
        destroyed = false;
        markedBy = -1;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) { this.empty = empty; }

    public boolean isDestroyed() { return destroyed; }

    public void setDestroyed(boolean destroyed) { this.destroyed = destroyed; }

    public int getMarkedBy() {
        return markedBy;
    }

    public void setMarkedBy(int markedBy) {
        this.markedBy = markedBy;
    }
}
