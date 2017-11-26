package ru.akirakozov.tictactoe.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.tictactoe.game.event.UserStepEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

/**
 * @author akirakozov
 */
public class GameManagerTest {
    private static final String USER_ID = "userId";

    private GameManager gameManager;
    private RememberLastEventListener eventListener;

    @Before
    public void init () {
        eventListener = new RememberLastEventListener();
        gameManager = new GameManager(eventListener);
    }

    @Test
    public void createGetNewGame() {
        int id = gameManager.createNewGame(USER_ID);

        Game game = gameManager.getGame(id);
        Assert.assertEquals(1, game.getStatus().getPlayersCount());
        Assert.assertEquals(GameState.ACTIVE, game.getStatus().getState());
    }

    @Test
    public void getGamesStatuses() {
        int id1 = gameManager.createNewGame(USER_ID);
        int id2 = gameManager.createNewGame(USER_ID);

        Assert.assertEquals(
                new HashSet<>(Arrays.asList(id1, id2)),
                gameManager.getGamesStatuses().stream().map(s -> s.getId()).collect(Collectors.toSet()));
    }

    @Test
    public void addUserToGame() {
        int id = gameManager.createNewGame(USER_ID);

        gameManager.addUserToGame(id, "user2");
        Assert.assertEquals(2, gameManager.getGame(id).getStatus().getPlayersCount());

        gameManager.addUserToGame(id, USER_ID);
        Assert.assertEquals(2, gameManager.getGame(id).getStatus().getPlayersCount());

    }

    @Test
    public void makeStep() {
        int id = gameManager.createNewGame(USER_ID);
        UserStep step = new UserStep(USER_ID, 3, 4, Game.O);
        gameManager.makeStep(id, step);

        Assert.assertEquals(null, eventListener.getLastUserStep(id));

        step = new UserStep(USER_ID, 5, 4, Game.X);
        gameManager.makeStep(id, step);

        Assert.assertEquals(new UserStepEvent(step, GameState.ACTIVE, 1), eventListener.getLastUserStep(id));
    }

}
