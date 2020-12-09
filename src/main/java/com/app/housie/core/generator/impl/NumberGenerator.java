package com.app.housie.core.generator.impl;

import com.app.housie.core.generator.Generator;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Generates Unique Random Numbers between a given range
 */
@Slf4j
@Getter(AccessLevel.PACKAGE)
public class NumberGenerator implements Generator<Integer> {
    private final List<Integer> remainingNumbers;

    /**
     * initializes an List of shuffled valid numbers to pick from
     * @param min minimum allowed value for the numbers to be generated
     * @param max maximum allowed value for the numbers to be generated
     */
    NumberGenerator(int min, int max) {
        remainingNumbers =
                IntStream.rangeClosed(min, max)
                        .boxed()
                        .collect(Collectors.toList());

        Collections.shuffle(remainingNumbers);
    }

    /**
     * @return unique integer within the initialized range
     */
    @Override
    public Integer generate() {
        int lastIndex = getRemainingNumbers().size() - 1;
        log.debug("Last index of list {}", lastIndex);
        return getRemainingNumbers().remove(lastIndex);
    }
}
