package ru.akirakozov.tictactoe.game.event;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.akirakozov.tictactoe.game.Game;
import ru.akirakozov.tictactoe.game.GameState;
import ru.akirakozov.tictactoe.game.GameStatus;
import ru.akirakozov.tictactoe.game.UserStep;

import java.time.Instant;


/**
 * @author akirakozov
 */
public class WebSocketSenderEventListenerTest {
    private WebSocketSenderEventListener eventListener;

    @Mock
    private SimpMessagingTemplate template;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        eventListener = new WebSocketSenderEventListener(template);
    }

    @Test
    public void onMakeStep() {
        UserStepEvent event = new UserStepEvent(new UserStep("userId", 2, 3, Game.X), GameState.ACTIVE, 23);
        eventListener.onMakeStep(1, event);

        Mockito.verify(template).convertAndSend("/game/1", event);
    }

    @Test
    public void onGameStatusChanged() {
        GameStatus status = new GameStatus(2, 10, Instant.now(), GameState.ACTIVE);
        eventListener.onGameStatusChanged(2, status);

        Mockito.verify(template).convertAndSend("/game-panel", status);
    }

}
