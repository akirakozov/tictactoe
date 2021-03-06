package ru.akirakozov.tictactoe.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.akirakozov.tictactoe.game.event.EventListener;
import ru.akirakozov.tictactoe.game.event.UserStepEvent;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * @author akirakozov
 */
public class Game {
    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private static final char EMPTY_SYMBOL = ' ';
    public static final char X = 'x';
    public static final char O = 'o';

    public static int BOARD_SIZE = 10;

    private final int id;
    private final char board[][];
    private final Set<String> activeUserIds;
    private final EventListener eventListener;
    private final WinChecker winChecker;

    private GameState state;
    private Instant lastStepTimestamp;
    private Optional<String> lastUserId;
    private int stepNumber;

    public Game(int id, EventListener listener) {
        this.id = id;
        this.board = createEmptyBoard();
        this.state = GameState.ACTIVE;
        this.stepNumber = 0;
        this.winChecker = new WinChecker();
        this.lastStepTimestamp = Instant.now();
        this.lastUserId = Optional.empty();
        this.activeUserIds = new HashSet<>();
        this.eventListener = listener;
    }

    public synchronized BoardState getBoardState() {
        return new BoardState(copyBoard(), state,
                lastUserId, stepNumber, BOARD_SIZE);
    }

    public synchronized GameStatus getStatus() {
        return new GameStatus(id, activeUserIds.size(), lastStepTimestamp, state);
    }

    public synchronized boolean addUser(String userId) {
        boolean wasAdded = activeUserIds.add(userId);
        if (wasAdded) {
            eventListener.onGameStatusChanged(id, getStatus());
        }
        return wasAdded;
    }

    public synchronized void makeStep(UserStep step) {
        if (isValidStep(step)) {
            board[step.getX()][step.getY()] = step.getSymbol();
            lastStepTimestamp = Instant.now();
            lastUserId = Optional.of(step.getUserId());
            stepNumber++;
            state = winChecker.getNewStatus(board, step.getX(), step.getY(), stepNumber);
            eventListener.onMakeStep(id, new UserStepEvent(step, state, stepNumber));
            eventListener.onGameStatusChanged(id, getStatus());
        }
    }

    boolean isValidStep(UserStep step) {
        if (lastUserId.isPresent() && step.getUserId().equals(lastUserId.get())) {
            logger.info("User couldn't do two steps in row, step: {}", step);
            return false;
        }

        if (GameState.ACTIVE != state) {
            logger.info("The game is over");
            return false;
        }

        if (!isValidCoord(step.getX()) || !isValidCoord(step.getY())) {
            logger.info("Invalid coordinates in step: {}", step);
            return false;
        }

        if (!isValidSymbol(step.getSymbol())) {
            logger.info("Invalid symbol in step: {}", step);
            return false;
        }

        if (board[step.getX()][step.getY()] != EMPTY_SYMBOL) {
            logger.info("Field is filled, step: {}", step);
            return false;
        }

        return true;
    }

    private char[][] copyBoard() {
        char[][] newBoard = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < board.length; i++) {
            System.arraycopy(board[i], 0, newBoard[i], 0, board[i].length);
        }
        return newBoard;
    }

    private boolean isValidSymbol(char symbol) {
        return stepNumber % 2 == 0 ?
                symbol == X : symbol == O;
    }

    private boolean isValidCoord(int val) {
        return 0 <= val && val < BOARD_SIZE;
    }

    private char[][] createEmptyBoard() {
        char[][] board;
        board = new char[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = EMPTY_SYMBOL;
            }
        }
        return board;
    }
}
