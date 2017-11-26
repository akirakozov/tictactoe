package ru.akirakozov.tictactoe.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.akirakozov.tictactoe.game.Game;
import ru.akirakozov.tictactoe.game.GameManager;
import ru.akirakozov.tictactoe.game.UserStep;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @author akirakozov
 */
@Controller
public class GameController {
    public static final String COOKIE_USER_ID = "User-Id";
    public static final String REDIRECT_TO_GAMES_PATH = "redirect:games";
    private final GameManager gameManager;

    public GameController(GameManager gameManager) {
        this.gameManager = gameManager;
    }

    @GetMapping("/")
    public String index() {
        return REDIRECT_TO_GAMES_PATH;
    }

    @GetMapping("/create-game")
    public String createGame(
            @CookieValue(value = COOKIE_USER_ID, required = false) String userId)
    {
        if (userId == null) {
            return REDIRECT_TO_GAMES_PATH;
        }

        int id = gameManager.createNewGame(userId);
        return "redirect:game?id=" + id;
    }

    @GetMapping("/games")
    public String getListOfGames(
            @CookieValue(value = COOKIE_USER_ID, required = false) String userId,
            HttpServletResponse response,
            ModelMap model)
    {
        setCookie(userId, response);
        model.put("games", gameManager.getGamesStatuses());
        return "game-panel";
    }

    @GetMapping("/game")
    public String getGame(
            @CookieValue(value = COOKIE_USER_ID, required = false) String userId,
            ModelMap model, Integer id)
    {
        if (userId == null || gameManager.getGame(id) == null) {
            return REDIRECT_TO_GAMES_PATH;
        }

        gameManager.addUserToGame(id, userId);

        Game game = gameManager.getGame(id);
        model.put("gameId", id);
        model.put("boardState", game.getBoardState());
        return "game";
    }

    @ResponseBody
    @GetMapping("/make-step")
    public String makeStep(
            @CookieValue(value = COOKIE_USER_ID) String userId,
            int gameId, int x, int y, char symbol)
    {
        gameManager.makeStep(gameId, new UserStep(userId, x, y, symbol));
        return "OK";
    }

    private String generateUserId() {
        return "user_" + UserIdGenerator.generateId();
    }

    private void setCookie(String userId, HttpServletResponse response) {
        if (userId == null) {
            response.addCookie(new Cookie(COOKIE_USER_ID, generateUserId()));
        }
    }

}
