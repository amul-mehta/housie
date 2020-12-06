package com.app.housie.core.combination.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Block;
import com.app.housie.model.HousieTicket;

import java.util.Objects;

public class EarlyFive implements WinningCombination {

    @Override
    public String getName() {
        return Constants.EARLY_FIVE_NAME;
    }

    @Override
    public boolean evaluate(HousieTicket housieTicket) {
        Block[][] blocks = housieTicket.getTicket();
        int count = 0;
        for (Block[] elements : blocks) {
            for (Block element : elements) {
                if (Objects.nonNull(element.getNumber()) && element.isSelected()) {
                    count++;
                    if (count == Constants.EARLY_FIVE_THRESHOLD)
                        return true;
                }
            }
        }
        return false;
    }
}
