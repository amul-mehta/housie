package com.app.housie.core.combination.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Block;
import com.app.housie.model.HousieTicket;

import java.util.Objects;

/**
 * Full House - When all the numbers in the ticket are being called/selected
 */
public class FullHouse implements WinningCombination {

    /**
     * @return printable name of Full House winning combination
     */
    @Override
    public String getName() {
        return Constants.FULL_HOUSE_NAME;
    }

    /**
     * @param housieTicket ticket which needs to be evaluated for Full House combination
     * @return if the ticket has full house winning combination present in ticket
     */
    @Override
    public boolean evaluate(HousieTicket housieTicket) {
        Block[][] blocks = housieTicket.getTicket();
        for (Block[] elements : blocks) {
            for (Block element : elements) {
                if (Objects.nonNull(element.getNumber()) && !element.isSelected()) {
                    return false;
                }
            }
        }
        return true;
    }
}
