package com.app.housie.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class Ticket {
    Player player;
    Block[][] content;

}
