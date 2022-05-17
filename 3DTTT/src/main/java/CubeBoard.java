public class CubeBoard extends GameBoard {
    Tile[][][] tiles;
    int dim, winner;
    // static final char[] FACE_LETTERS = {'U', 'L', 'F', 'R', 'B', 'D'};
    static final int FACE_COUNT = 6;

    public CubeBoard(int dimension) {
        this.dim = dimension;
        this.winner = -1;
        tiles = new Tile[FACE_COUNT][dimension][dimension];
        for (int i = 0; i < FACE_COUNT; ++i) {
            for (int j = 0; j < dimension; j++) {
                for (int k = 0; k < dimension; k++) {
                    tiles[i][j][k] = new Tile();
                }
            }
        }
    }

    @Override
    public boolean isBoardFull() {
        for (int k=0; k < FACE_COUNT; ++k) {
            for (int i = 0; i < this.dim; ++i) {
                for (int j = 0; j < this.dim; ++j) {
                    if (tiles[k][i][j].getMarkedBy() == -1) return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean hasWinner() {
        boolean bWinner;
        int player;
        for (int k=0; k < FACE_COUNT; ++k) {
            // horizontal checks
            for (int i = 0; i < this.dim; ++i) {
                player = tiles[k][i][0].getMarkedBy();
                if (player == -1) continue;
                bWinner = true;
                for (int j = 1; j < this.dim; ++j) {
                    if (tiles[k][i][j].getMarkedBy() != player) {
                        bWinner = false;
                        break;
                    }
                }
                if (bWinner) {
                    winner = player;
                    return true;
                }
            }
            // vertical checks
            for (int i = 0; i < this.dim; ++i) {
                player = tiles[k][0][i].getMarkedBy();
                if (player == -1) continue;
                bWinner = true;
                for (int j = 1; j < this.dim; ++j) {
                    if (tiles[k][j][i].getMarkedBy() != player) {
                        bWinner = false;
                        break;
                    }
                }
                if (bWinner) {
                    winner = player;
                    return true;
                }
            }
            // diagonal checks
            bWinner = true;
            player = tiles[k][0][0].getMarkedBy();
            if (player == -1) bWinner = false;
            for (int i = 1, j = 1; i < this.dim; ++i, ++j) {
                if (tiles[k][i][j].getMarkedBy() != player) {
                    bWinner = false;
                    break;
                }
                ;
            }
            if (bWinner) {
                winner = player;
                return true;
            }

            bWinner = true;
            player = tiles[k][0][dim - 1].getMarkedBy();
            if (player == -1) bWinner = false;
            for (int i = 1, j = dim - 2; i < this.dim; ++i, --j) {
                if (tiles[k][i][j].getMarkedBy() != player) {
                    bWinner = false;
                    break;
                }
                ;
            }
            if (bWinner) {
                winner = player;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isMoveValid(Move move) {
        if (move.getFace() < 0 || move.getFace() >= FACE_COUNT || move.getX() < 0
                || move.getX() > dim-1 || move.getY() < 0 || move.getY() > dim-1) return false;
        return tiles[move.getFace()][move.getX()][move.getY()].isEmpty() &&
                !tiles[move.getFace()][move.getX()][move.getY()].isDestroyed();
    }

    @Override
    public void makeMove(Move m) {
        tiles[m.getFace()][m.getX()][m.getY()].setEmpty(false);
        tiles[m.getFace()][m.getX()][m.getY()].setMarkedBy(m.getPlayerID());
    }

    @Override
    public int getWinner() {
        return winner;
    }

    public Tile[][][] getTiles() {
        return tiles;
    }
}