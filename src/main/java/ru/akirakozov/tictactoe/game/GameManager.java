package ru.akirakozov.tictactoe.game;

import ru.akirakozov.tictactoe.game.event.EventListener;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class GameManager {
    private final AtomicInteger nextGameId = new AtomicInteger(1);
    private final ConcurrentHashMap<Integer, Game> gamesMap = new ConcurrentHashMap<>();
    private final EventListener gameEventListener;

    public GameManager(EventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
    }

    public List<GameStatus> getGamesStatuses() {
        return gamesMap.values().stream()
                .map(Game::getStatus)
                .collect(Collectors.toList());
    }

    public int createNewGame(String userId) {
        int id = nextGameId.getAndIncrement();
        Game game = new Game(id, gameEventListener);
        gamesMap.put(id, game);
        game.addUser(userId);
        return id;
    }

    public boolean addUserToGame(int gameId, String userId) {
        return gamesMap.get(gameId).addUser(userId);
    }

    public Game getGame(int id) {
        return gamesMap.get(id);
    }

    public void makeStep(int gameId, UserStep step) {
        gamesMap.get(gameId).makeStep(step);
    }
}
