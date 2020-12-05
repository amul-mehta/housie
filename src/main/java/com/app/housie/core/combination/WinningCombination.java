package com.app.housie.core.combination;

import com.app.housie.model.Ticket;

public interface WinningCombination {
    void setName(String name);

    String getName();

    boolean evaluate(Ticket ticket);
}
