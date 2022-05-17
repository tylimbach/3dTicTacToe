public class CubeGame extends Game {

    @Override
    public boolean makeMove(Move move) {
        if (isMoveValid(move)) {
            gameBoard.makeMove(move);
            return true;
        }
        return false;
    }

    CubeGame(int dimension, int players) {
        gameBoard = new CubeBoard(dimension);
        this.players = players;
    }
}
