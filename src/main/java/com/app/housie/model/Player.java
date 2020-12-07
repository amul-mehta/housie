package com.app.housie.model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Player implements Person {
    private final String name;
}
