package com.example.paymentapp.model;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Status {
    IN_PROCESS,
    ERROR,
    COMPLETED;

    private static final List<Status> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();
    // логика создания случайного статуса платежа
    public static Status randomStatus()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
