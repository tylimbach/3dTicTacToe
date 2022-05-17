import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClassicBoardTest {
	ClassicBoard board;
	
	@BeforeEach
	void init() {
		board = new ClassicBoard(3);
	}

    // test constructor
	@Test
	void classicBoardTest() {
		assertEquals(board.dim, 3);
	}

    @Test
    void isMoveValidTest() {
        Move m = new Move(0, 0, 0);
        assertTrue(board.isMoveValid(m));

        Move m2 = new Move(0, 3, 3);
        assertFalse(board.isMoveValid(m2));
    }

    @Test
    void isMoveValidTest2() {
        Move m = new Move(0, 0, 0);
        board.makeMove(m);
        assertFalse(board.isMoveValid(m));
    }

	@Test
	void isBoardFullTest() {
        assertFalse(board.isBoardFull());
        Move m = new Move(0, 0, 0);

        board.makeMove(m);
        assertFalse(board.isBoardFull());
	}

    @Test
    void isBoardFullTest2() {
        for (int i = 0; i < board.dim; ++i) {
            for (int j = 0; j < board.dim; ++j) {
                board.makeMove(new Move(0, i, j));
            }
        }
        assertTrue(board.isBoardFull());
    }

    // no winner
    @Test
    void hasWinnerTest() {
        assertFalse(board.hasWinner());
        board.makeMove(new Move(0, 0, 0));
        assertFalse(board.hasWinner());
        board.makeMove(new Move(0, 1, 1));
        board.makeMove(new Move(0, 1, 2));
        board.makeMove(new Move(1, 1, 0));

        assertFalse(board.hasWinner());
    }

    // horizontal winner
    @Test
    void hasWinnerTest2() {
        int i = 0, j = 0;
        for (; j < board.dim; ++j) {
            board.makeMove(new Move(0, i, j));
        }
        assertTrue(board.hasWinner());
    }

    // vertical winner
    @Test
    void hasWinnerTest3() {
        int i = 0, j = 0;
        for (; j < board.dim; ++j) {
            board.makeMove(new Move(0, j, i));
        }
        assertTrue(board.hasWinner());
    }

    // diagonal winners
    @Test
    void hasWInnerTest4() {
        board.makeMove(new Move(0, 0, 0));
        board.makeMove(new Move(0, 1, 1));
        board.makeMove(new Move(0, 2, 2));
        assertTrue(board.hasWinner());
    }
}
