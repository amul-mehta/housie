package com.app.housie.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Builder
@ToString

public class Block {
    private final Integer number;

    @Setter
    private boolean selected;

}
