import com.sun.tools.javac.code.Attribute;

public class ClassicGame extends Game {

    @Override
    public boolean makeMove(Move move) {
        if (isMoveValid(move)) {
            gameBoard.makeMove(move);
            return true;
        }
        return false;
    }

    public ClassicGame(int dimension, int players) {
        gameBoard = new ClassicBoard(dimension);
        this.players = players;
    }
}
