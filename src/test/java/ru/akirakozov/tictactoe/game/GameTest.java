package ru.akirakozov.tictactoe.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.akirakozov.tictactoe.game.event.UserStepEvent;

/**
 * @author akirakozov
 */
public class GameTest {
    private static int GAME_ID = 1;
    private static String USER_ID = "userId";

    private Game game;
    private RememberLastEventListener eventListener;

    @Before
    public void init() {
        eventListener = new RememberLastEventListener();
        game = new Game(GAME_ID, eventListener);
    }

    @Test
    public void checkStepBounds() {
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, Game.BOARD_SIZE + 2, 1, Game.X)));
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, 1, Game.BOARD_SIZE + 1, Game.O)));
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, -1, 4, Game.X)));
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, 1, -4, Game.X)));
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, 1, 3, 'a')));

        Assert.assertTrue(game.isValidStep(new UserStep(USER_ID, 1, 3, Game.X)));
    }

    @Test
    public void checkNextStepSymbolAndUser() {
        Assert.assertTrue(game.isValidStep(new UserStep(USER_ID, 1, 1, Game.X)));
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, 1, 1, Game.O)));

        game.makeStep(new UserStep(USER_ID, 1, 1, Game.X));
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, 1, 3, Game.O)));
        Assert.assertTrue(game.isValidStep(new UserStep("user2", 1, 3, Game.O)));
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, 1, 3, Game.X)));
    }

    @Test
    public void makeStep() {
        UserStep step = new UserStep(USER_ID, 3, 5, Game.X);
        game.makeStep(step);
        Assert.assertEquals(new UserStepEvent(step, GameState.ACTIVE), eventListener.getLastUserStep(GAME_ID));
        Assert.assertEquals(GameState.ACTIVE, eventListener.getLastGameStatus(GAME_ID).getState());
    }

    @Test
    public void checkCellIsFilled() {
        game.makeStep(new UserStep(USER_ID, 1, 3, Game.X));
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, 1, 3, Game.O)));
        Assert.assertFalse(game.isValidStep(new UserStep(USER_ID, 1, 3, Game.X)));
    }

    @Test
    public void addUser() {
        game.addUser("user1");
        game.addUser("user2");
        Assert.assertEquals(2, game.getStatus().getPlayersCount());
        Assert.assertEquals(2, eventListener.getLastGameStatus(GAME_ID).getPlayersCount());

        game.addUser("user1");
        Assert.assertEquals(2, game.getStatus().getPlayersCount());
        Assert.assertEquals(2, eventListener.getLastGameStatus(GAME_ID).getPlayersCount());

        game.addUser("user3");
        Assert.assertEquals(3, game.getStatus().getPlayersCount());
        Assert.assertEquals(3, eventListener.getLastGameStatus(GAME_ID).getPlayersCount());
    }
}
