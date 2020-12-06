package com.app.housie.core.combination;

import com.app.housie.model.HousieTicket;

public interface WinningCombination {
    String getName();

    boolean evaluate(HousieTicket housieTicket);
}
