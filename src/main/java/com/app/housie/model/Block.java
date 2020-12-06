package com.app.housie.model;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Block {
    private Integer number;
    @Builder.Default
    private boolean selected = false;
}
