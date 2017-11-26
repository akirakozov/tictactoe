package ru.akirakozov.tictactoe.game.event;

import ru.akirakozov.tictactoe.game.GameStatus;
import ru.akirakozov.tictactoe.game.UserStep;

/**
 * @author akirakozov
 */
public interface EventListener {
    void onMakeStep(int gameId, UserStep userStep);
    void onGameStatusChanged(int gameId, GameStatus gameStatus);
}
