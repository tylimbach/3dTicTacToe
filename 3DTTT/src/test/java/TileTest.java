import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TileTest {
    Tile tile;

    @BeforeEach
    void init() {
        tile = new Tile();
    }

    // test constructor
    @Test
    void tileTest() {
        assertEquals(tile.getMarkedBy(), -1);
        assertTrue(tile.isEmpty());
    }
}
