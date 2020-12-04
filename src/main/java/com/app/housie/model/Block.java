package com.app.housie.model;


import lombok.Builder;

@Builder
public class Block {
    int number;

    @Builder.Default
    boolean selected = false;
}
