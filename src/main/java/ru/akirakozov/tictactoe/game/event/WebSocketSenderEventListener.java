package ru.akirakozov.tictactoe.game.event;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.akirakozov.tictactoe.game.GameStatus;
import ru.akirakozov.tictactoe.game.UserStep;

/**
 * @author akirakozov
 */
public class WebSocketSenderEventListener implements EventListener {
    private final SimpMessagingTemplate template;

    public WebSocketSenderEventListener(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Override
    public void onMakeStep(int gameId, UserStep userStep) {
        template.convertAndSend("/game/" + gameId, userStep);
    }

    @Override
    public void onGameStatusChanged(int gameId, GameStatus gameStatus) {
        template.convertAndSend("/game-panel", gameStatus);
    }
}
