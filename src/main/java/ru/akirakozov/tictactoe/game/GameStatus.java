package ru.akirakozov.tictactoe.game;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Objects;

/**
 * @author akirakozov
 */
public class GameStatus {
    private final int id;
    private final int playersCount;
    private final Instant lastStepTime;
    private final GameState state;

    public GameStatus(int id, int playersCount, Instant lastStepTime, GameState state) {
        this.id = id;
        this.playersCount = playersCount;
        this.lastStepTime = lastStepTime;
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public GameState getState() {
        return state;
    }

    public Instant getLastStepTime() {
        return lastStepTime;
    }

    public LocalDateTime getLastStepDateTime() {
        return LocalDateTime.ofInstant(lastStepTime, ZoneId.of("Europe/Moscow"));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameStatus that = (GameStatus) o;
        return id == that.id &&
                playersCount == that.playersCount &&
                Objects.equals(lastStepTime, that.lastStepTime) &&
                state == that.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, playersCount, lastStepTime, state);
    }
}
