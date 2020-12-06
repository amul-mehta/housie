package com.app.housie.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class HousieTicket {
    private final Player player;
    private final Block[][] ticket;
}
