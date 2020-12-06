package com.app.housie.model;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Block {
    private final Integer number;
    @Builder.Default
    private boolean selected = false;
}
