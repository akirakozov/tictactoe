package ru.akirakozov.tictactoe.game.event;

import ru.akirakozov.tictactoe.game.GameState;
import ru.akirakozov.tictactoe.game.UserStep;

import java.util.Objects;

/**
 * @author akirakozov
 */
public class UserStepEvent {
    public final int x;
    public final int y;
    public final char symbol;
    public final GameState state;

    public UserStepEvent(UserStep step, GameState state) {
        this.x = step.getX();
        this.y = step.getY();
        this.symbol = step.getSymbol();
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStepEvent that = (UserStepEvent) o;
        return x == that.x &&
                y == that.y &&
                symbol == that.symbol &&
                state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, symbol, state);
    }
}
