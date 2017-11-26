package ru.akirakozov.tictactoe.game.event;

import ru.akirakozov.tictactoe.game.GameStatus;

/**
 * @author akirakozov
 */
public interface EventListener {
    void onMakeStep(int gameId, UserStepEvent userStep);
    void onGameStatusChanged(int gameId, GameStatus gameStatus);
}
