package com.app.housie.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Ticket {
    private final Player player;
    private final Block[][] content;

}
