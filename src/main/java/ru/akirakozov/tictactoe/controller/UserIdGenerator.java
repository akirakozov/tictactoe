package ru.akirakozov.tictactoe.controller;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author akirakozov
 */
public class UserIdGenerator {
    private static final String alphabite =
            "abcdefjhijklmnopqrstuvwxyzABCDEFJHIJKLMNOPQRSTUVWXYZ0123456789";

    public char nextChar(CharSequence alphabet) {
        return alphabet.charAt(ThreadLocalRandom.current().nextInt(alphabet.length()));
    }

    public String generateId(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; ++i) {
            sb.append(nextChar(alphabite));
        }
        return sb.toString();
    }

    public static String generateId() {
        return new UserIdGenerator().generateId(8);
    }
}
