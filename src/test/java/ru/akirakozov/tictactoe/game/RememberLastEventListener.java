package ru.akirakozov.tictactoe.game;

import ru.akirakozov.tictactoe.game.event.EventListener;
import ru.akirakozov.tictactoe.game.event.UserStepEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akirakozov
 */
public class RememberLastEventListener implements EventListener {
    private final Map<Integer, UserStepEvent> lastUserSteps = new HashMap<>();
    private final Map<Integer, GameStatus> lastGameStatuses = new HashMap<>();

    @Override
    public void onMakeStep(int gameId, UserStepEvent userStep) {
        lastUserSteps.put(gameId, userStep);
    }

    @Override
    public void onGameStatusChanged(int gameId, GameStatus gameStatus) {
        lastGameStatuses.put(gameId, gameStatus);
    }

    public UserStepEvent getLastUserStep(int gameId) {
        return lastUserSteps.get(gameId);
    }

    public GameStatus getLastGameStatus(int gameId) {
        return lastGameStatuses.get(gameId);
    }
}
