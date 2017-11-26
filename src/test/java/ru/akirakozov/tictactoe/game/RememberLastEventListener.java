package ru.akirakozov.tictactoe.game;

import ru.akirakozov.tictactoe.game.event.EventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akirakozov
 */
public class RememberLastEventListener implements EventListener {
    private final Map<Integer, UserStep> lastUserSteps = new HashMap<>();
    private final Map<Integer, GameStatus> lastGameStatuses = new HashMap<>();

    @Override
    public void onMakeStep(int gameId, UserStep userStep) {
        lastUserSteps.put(gameId, userStep);
    }

    @Override
    public void onGameStatusChanged(int gameId, GameStatus gameStatus) {
        lastGameStatuses.put(gameId, gameStatus);
    }

    public UserStep getLastUserStep(int gameId) {
        return lastUserSteps.get(gameId);
    }

    public GameStatus getLastGameStatus(int gameId) {
        return lastGameStatuses.get(gameId);
    }
}
