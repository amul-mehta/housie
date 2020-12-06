package com.app.housie.generator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class NumberGenerator {
    private final List<Integer> remainingNumbers;

    public NumberGenerator(int min, int max) {
        remainingNumbers =
                IntStream.rangeClosed(min, max)
                        .boxed()
                        .collect(Collectors.toList());

        Collections.shuffle(remainingNumbers);
    }

    public int getRandomInt() {
        return remainingNumbers.remove(remainingNumbers.size() - 1);
    }
}
