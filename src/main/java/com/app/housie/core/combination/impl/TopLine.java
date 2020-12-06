package com.app.housie.core.combination.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Block;
import com.app.housie.model.HousieTicket;

import java.util.Objects;


public class TopLine implements WinningCombination {

    @Override
    public String getName() {
        return Constants.TOP_LINE_NAME;
    }

    @Override
    public boolean evaluate(HousieTicket housieTicket) {
        Block[][] blocks = housieTicket.getTicket();
        for (Block element : blocks[0]) {
            if (Objects.nonNull(element.getNumber()) && !element.isSelected())
                return false;
        }
        return true;
    }
}
