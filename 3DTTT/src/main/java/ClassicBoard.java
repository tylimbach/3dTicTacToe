public class ClassicBoard extends GameBoard {
    Tile[][] tiles;
    int dim, winner;

    public ClassicBoard(int dimension) {
        this.dim = dimension;
        this.winner = -1;
        tiles = new Tile[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tiles[i][j] = new Tile();
            }
        }
    }

    @Override
    public boolean isBoardFull() {
        for (int i=0; i < this.dim; ++i) {
            for (int j=0; j < this.dim; ++j) {
                if (tiles[i][j].getMarkedBy() == -1 && !tiles[i][j].isDestroyed()) return false;
            }
        }
        return true;
    }

    @Override
    public boolean hasWinner() {
        boolean bWinner;
        int player;
        // horizontal checks
        for (int i=0; i < this.dim; ++i) {
            player = tiles[i][0].getMarkedBy();
            if (player == -1) continue;
            bWinner = true;
            for (int j=1; j < this.dim; ++j) {
                if (tiles[i][j].getMarkedBy() != player) {
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
        for (int i=0; i < this.dim; ++i) {
            player = tiles[0][i].getMarkedBy();
            if (player == -1) continue;
            bWinner = true;
            for (int j=1; j < this.dim; ++j) {
                if (tiles[j][i].getMarkedBy() != player) {
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
        player = tiles[0][0].getMarkedBy();
        if (player == -1) bWinner = false;
        for (int i=1, j=1; i < this.dim; ++i, ++j) {
            if (tiles[i][j].getMarkedBy() != player) {
                bWinner = false;
                break;
            };
        }
        if (bWinner) {
            winner = player;
            return true;
        }

        bWinner = true;
        player = tiles[0][dim-1].getMarkedBy();
        if (player == -1) bWinner = false;
        for (int i=1, j=dim-2; i < this.dim; ++i, --j) {
            if (tiles[i][j].getMarkedBy() != player) {
                bWinner = false;
                break;
            };
        }
        if (bWinner) {
            winner = player;
            return true;
        }
        return false;
    }

    public int getWinner() {
        return winner;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public boolean isMoveValid(Move move) {
        if (move.getX() < 0 || move.getX() > dim-1 || move.getY() < 0 || move.getY() > dim-1) return false;
        return tiles[move.getX()][move.getY()].isEmpty() && !tiles[move.getX()][move.getY()].isDestroyed();
    }

    @Override
    public void makeMove(Move m) {
        tiles[m.getX()][m.getY()].setEmpty(false);
        tiles[m.getX()][m.getY()].setMarkedBy(m.getPlayerID());
    }
}
