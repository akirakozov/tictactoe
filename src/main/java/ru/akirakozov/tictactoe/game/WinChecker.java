package ru.akirakozov.tictactoe.game;

/**
 * @author akirakozov
 */
public class WinChecker {
    private static int MAX_IN_ROW = 5;
    private static int[][] diff =
                {
                    {-1, -1, 1, 1}, // diagonal 1
                    {-1, 1, 1, -1}, // diagonal 2
                    {1, 0, -1, 0},  // horizontal
                    {0, 1, 0, -1},  // vertical
                };

    public GameState getNewStatus(char[][] board, int nextX, int nextY, int stepNumber) {
        for (int i = 0; i < diff.length; i++) {
            GameState state = checkStateInDimension(diff[i], board, nextX, nextY);
            if (GameState.ACTIVE != state) {
                return state;
            }
        }

        return isAllCellsFilled(stepNumber) ?
                GameState.FINISHED : GameState.ACTIVE;
    }

    private boolean isAllCellsFilled(int stepNumber) {
        return stepNumber >= (Game.BOARD_SIZE * Game.BOARD_SIZE);
    }

    private GameState checkStateInDimension(int[] diff, char[][] board, int nextX, int nextY) {
        int curCountInRow = calcSameInRow(board, nextX, nextY, 1, diff[0], diff[1]);
        curCountInRow = calcSameInRow(board, nextX, nextY, curCountInRow, diff[2], diff[3]);

        return curCountInRow == MAX_IN_ROW ? GameState.FINISHED : GameState.ACTIVE;
    }

    private int calcSameInRow(char[][] board, int nextX, int nextY, int curCountInRow, int diffX, int diffY) {
        char symbol = board[nextX][nextY];

        int x = nextX + diffX;
        int y = nextY + diffY;
        while (curCountInRow < MAX_IN_ROW && isValidCoord(x) && isValidCoord(y)) {
            if (board[x][y] == symbol) {
                x += diffX;
                y += diffY;
                curCountInRow++;
            } else {
                break;
            }
        }
        return curCountInRow;
    }

    private boolean isValidCoord(int val) {
        return 0 <= val && val < Game.BOARD_SIZE;
    }
}
