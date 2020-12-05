package com.app.housie.core.combination;

import com.app.housie.model.Ticket;

public interface WinningCombination {
    String getName();

    boolean evaluate(Ticket ticket);
}
