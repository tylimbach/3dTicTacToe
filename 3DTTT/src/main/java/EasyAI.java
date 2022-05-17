import java.util.Random;

public class EasyAI {
    int player;

    public EasyAI(int p) {
        player = p;
    }

    Move nextMove(Tile[][] tiles) {
        Random rand = new Random();
        int x = 0;
        int y = 0;

        while (true) {
            x = rand.nextInt(tiles.length);
            y = rand.nextInt(tiles.length);
            if (tiles[x][y].isEmpty() && !tiles[x][y].isDestroyed()) {
                return new Move(player, x, y);
            }
        }
    }

    Move nextMove(Tile[][][] tiles) {
        Random rand = new Random();
        int x = 0;
        int y = 0;
        int f = 0;
        while (true) {
            x = rand.nextInt(tiles[0].length);
            y = rand.nextInt(tiles[0].length);
            f = rand.nextInt(6);
            if (tiles[f][x][y].isEmpty() && !tiles[f][x][y].isDestroyed()) {
                return new Move(player, x, y, f);
            }
        }
    }
}
