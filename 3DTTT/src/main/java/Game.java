public abstract class Game {
    protected GameBoard gameBoard;
    int players;
    // TODO: Come up with abstract methods
    public boolean isGameOver() {
        return gameBoard.hasWinner() || gameBoard.isBoardFull();
    }
    public int getWinner() {
        return gameBoard.getWinner();
    }
    public boolean isMoveValid(Move move) {
        return gameBoard.isMoveValid(move);
    }

    public abstract boolean makeMove(Move m);
}
