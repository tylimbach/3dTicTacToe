public abstract class GameBoard {
    public abstract boolean isBoardFull();
    public abstract boolean hasWinner();
    public abstract boolean isMoveValid(Move m);
    public abstract void makeMove(Move m);
    public abstract int getWinner();
}
