package ru.akirakozov.tictactoe.game;

import java.util.Optional;

/**
 * @author akirakozov
 */
public class BoardState {
    public final char board[][];
    public final GameState state;
    public final Optional<String> lastUserId;
    public final int stepNumber;
    public final int boardSize;

    public BoardState(char[][] board, GameState state,
            Optional<String> lastUserId, int stepNumber, int boardSize)
    {
        this.board = board;
        this.state = state;
        this.lastUserId = lastUserId;
        this.stepNumber = stepNumber;
        this.boardSize = boardSize;
    }
}
