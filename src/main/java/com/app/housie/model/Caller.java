package com.app.housie.model;


import com.app.housie.core.generator.Generator;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Caller implements Person {
    private final String name;
    private final Generator<Integer> randomNumberGenerator;

    public int callNumber(){
        return getRandomNumberGenerator().generate();
    }
}
