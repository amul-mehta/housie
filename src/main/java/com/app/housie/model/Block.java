package com.app.housie.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Block {
    Integer number;
    @Builder.Default
    boolean selected = false;
}
