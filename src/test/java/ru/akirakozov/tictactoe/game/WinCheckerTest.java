package ru.akirakozov.tictactoe.game;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author akirakozov
 */
public class WinCheckerTest {
    private WinChecker winChecker = new WinChecker();

    @Test
    public void checkVerticalsEnd() {
        char[][] board = createEmptyBoard();
        board[2][3] = Game.X;
        board[3][3] = Game.X;
        board[4][3] = Game.X;
        board[5][3] = Game.X;

        Assert.assertEquals(GameState.ACTIVE, winChecker.getNewStatus(board, 2, 3, 6));

        board[1][3] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 1, 3, 6));

        board[1][3] = ' ';
        board[6][3] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 6, 3, 6));
    }

    @Test
    public void checkVerticalsInMiddle() {
        char[][] board = createEmptyBoard();
        board[1][3] = Game.X;
        board[2][3] = Game.X;
        board[4][3] = Game.X;
        board[5][3] = Game.X;

        board[3][3] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 3, 3, 6));
    }

    @Test
    public void checkHorizontalsEnd() {
        char[][] board = createEmptyBoard();
        board[5][3] = Game.X;
        board[5][4] = Game.X;
        board[5][5] = Game.X;
        board[5][6] = Game.X;

        Assert.assertEquals(GameState.ACTIVE, winChecker.getNewStatus(board, 5, 3, 6));

        board[5][2] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 5, 2, 6));

        board[5][2] = ' ';
        board[5][7] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 5, 7, 6));
    }

    @Test
    public void checkHorizontalsInMiddle() {
        char[][] board = createEmptyBoard();
        board[0][0] = Game.O;
        board[0][2] = Game.X;
        board[0][3] = Game.X;
        board[0][4] = Game.X;
        board[0][6] = Game.X;

        board[0][5] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 0, 5, 6));
    }

    @Test
    public void checkDiagonal1() {
        char[][] board = createEmptyBoard();
        board[0][0] = Game.O;
        board[1][1] = Game.X;
        board[2][2] = Game.X;
        board[3][3] = Game.X;
        board[4][4] = Game.X;

        board[5][5] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 5, 5, 6));

        board[5][5] = Game.O;
        board[0][0] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 0, 0, 6));
    }

    @Test
    public void checkDiagonal2InTheMiddle() {
        char[][] board = createEmptyBoard();
        board[1][6] = Game.X;
        board[2][5] = Game.X;
        board[4][4] = Game.X;
        board[5][3] = Game.X;

        board[6][2] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 5, 5, 6));

        board[6][2] = Game.O;
        board[0][7] = Game.X;
        Assert.assertEquals(GameState.FINISHED, winChecker.getNewStatus(board, 0, 0, 6));
    }

    @Test
    public void checkAllCellsFilled() {
        char[][] board = createEmptyBoard();
        board[0][0] = Game.X;
        Assert.assertEquals(GameState.FINISHED,
                winChecker.getNewStatus(board, 0, 0, Game.BOARD_SIZE * Game.BOARD_SIZE));
    }

    private char[][] createEmptyBoard() {
        return new char[Game.BOARD_SIZE][Game.BOARD_SIZE];
    }
}
