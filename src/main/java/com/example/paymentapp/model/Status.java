package com.example.paymentapp.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Status {
    STARTED_PROCESS, // при генерации рандомного статуса не учитывается этот
    IN_PROCESS,
    ERROR,
    COMPLETED;

    private static final Random RANDOM = new Random();
    private static final int NUM = 2 + RANDOM.nextInt(4-3);

    private static final List<Status> VALUES = List.of(values());

    // логика создания случайного статуса билета
    public static Status randomStatus()  {
        return VALUES.get(NUM);
    }
}
