package com.app.housie.core.combination.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Block;
import com.app.housie.model.HousieTicket;

import java.util.Objects;

/**
 *  Early Five - When a ticket has five of its number called/selected
 */
public class EarlyFive implements WinningCombination {


    /**
     * @return the printable name of EarlyFive Combination
     */
    @Override
    public String getName() {
        return Constants.EARLY_FIVE_NAME;
    }

    /**
     * @param housieTicket ticket to be evaluated
     * @return if the ticket has the early five winning combination present in ticket
     */
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
