package com.app.housie.core.generator.impl;

import com.app.housie.core.generator.Generator;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Getter(AccessLevel.PACKAGE)
public class NumberGenerator implements Generator<Integer> {
    private final List<Integer> remainingNumbers;

    NumberGenerator(int min, int max) {
        remainingNumbers =
                IntStream.rangeClosed(min, max)
                        .boxed()
                        .collect(Collectors.toList());

        Collections.shuffle(remainingNumbers);
    }

    @Override
    public Integer generate() {
        return remainingNumbers.remove(remainingNumbers.size() - 1);
    }
}
