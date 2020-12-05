package com.app.housie.generator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class NumberGenerator {
    List<Integer> allowedNums;

    public NumberGenerator(int min, int max) {
        allowedNums =
                IntStream.rangeClosed(min, max)
                        .boxed()
                        .collect(Collectors.toList());
        Collections.shuffle(allowedNums);
    }

    public int getRandomInt() {
        return allowedNums.remove(allowedNums.size() - 1);
    }


}
