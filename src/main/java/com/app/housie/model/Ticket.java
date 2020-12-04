package com.app.housie.model;

import lombok.Builder;

@Builder
public class Ticket {
    Player player;
    Block[][] content;

}
