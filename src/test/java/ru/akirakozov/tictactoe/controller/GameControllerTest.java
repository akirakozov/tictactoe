package ru.akirakozov.tictactoe.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ModelMap;
import ru.akirakozov.tictactoe.game.BoardState;
import ru.akirakozov.tictactoe.game.Game;
import ru.akirakozov.tictactoe.game.GameManager;
import ru.akirakozov.tictactoe.game.GameState;
import ru.akirakozov.tictactoe.game.GameStatus;
import ru.akirakozov.tictactoe.game.RememberLastEventListener;
import ru.akirakozov.tictactoe.game.UserStep;
import ru.akirakozov.tictactoe.game.event.EventListener;

import javax.servlet.http.HttpServletResponse;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

/**
 * @author akirakozov
 */
public class GameControllerTest {

    public static final String USER_ID = "userId";
    private GameController gameController;
    private EventListener eventListener;

    @Mock
    private GameManager gameManager;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        gameController = new GameController(gameManager);
        eventListener = new RememberLastEventListener();
    }

    @Test
    public void index() {
        Assert.assertEquals(GameController.REDIRECT_TO_GAMES_PATH, gameController.index());
    }

    @Test
    public void createGameWithoutCookie() {
        Assert.assertEquals(GameController.REDIRECT_TO_GAMES_PATH, gameController.createGame(null));
    }

    @Test
    public void createGame() {
        when(gameManager.createNewGame(USER_ID)).thenReturn(123);

        Assert.assertEquals("redirect:game?id=123", gameController.createGame(USER_ID));
    }

    @Test
    public void getListOfGames() {
        HttpServletResponse response = Mockito.mock(HttpServletResponse.class);
        ModelMap map = new ModelMap();

        when(gameManager.getGamesStatuses()).thenReturn(createGameStatuses());
        Assert.assertEquals("game-panel", gameController.getListOfGames(null, response, map));
        Mockito.verify(response).addCookie(any());
        Assert.assertEquals(2, ((List) map.get("games")).size());
    }

    @Test
    public void getGameWithoutCookie() {
        Assert.assertEquals(GameController.REDIRECT_TO_GAMES_PATH, gameController.createGame(null));
    }

    @Test
    public void getGame() {
        int id = 34;
        ModelMap model = new ModelMap();

        when(gameManager.getGame(id)).thenReturn(createNewGameForTest(id));
        Assert.assertEquals("game", gameController.getGame(USER_ID, model, 34));

        Assert.assertEquals(34, model.get("gameId"));
        BoardState state = (BoardState) model.get("boardState");

        Assert.assertEquals(state.state, GameState.ACTIVE);
    }

    @Test
    public void makeStep() {
        gameController.makeStep(23, 1, 3, Game.X);

        Mockito.verify(gameManager).makeStep(23, new UserStep(1, 3, Game.X));
    }

    private Game createNewGameForTest(int id) {
        return new Game(id, eventListener);
    }

    private List<GameStatus> createGameStatuses() {
        return Arrays.asList(
                new GameStatus(1, 10, Instant.now(), GameState.ACTIVE),
                new GameStatus(2, 12, Instant.now(), GameState.FINISHED));
    }

}
