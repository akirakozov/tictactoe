package ru.akirakozov.tictactoe;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import ru.akirakozov.tictactoe.game.GameManager;
import ru.akirakozov.tictactoe.game.event.WebSocketSenderEventListener;

/**
 * @author akirakozov
 */
@Configuration
public class GameContextConfiguration {
    @Bean
    public GameManager gameManager(SimpMessagingTemplate template) {
        return new GameManager(new WebSocketSenderEventListener(template));
    }

}
