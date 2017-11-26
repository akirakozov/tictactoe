package ru.akirakozov.tictactoe.game;

import java.util.Objects;

/**
 * @author akirakozov
 */
public class UserStep {
    private final int x;
    private final int y;
    private final char symbol;
    private final String userId;

    public UserStep(String userId, int x, int y, char symbol) {
        this.x = x;
        this.y = y;
        this.symbol = symbol;
        this.userId = userId;
    }

    public char getSymbol() {
        return symbol;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStep userStep = (UserStep) o;
        return x == userStep.x &&
                y == userStep.y &&
                symbol == userStep.symbol &&
                Objects.equals(userId, userStep.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, symbol, userId);
    }

    @Override
    public String toString() {
        return "UserStep{" +
                "x=" + x +
                ", y=" + y +
                ", symbol=" + symbol +
                ", userId='" + userId + '\'' +
                '}';
    }
}
