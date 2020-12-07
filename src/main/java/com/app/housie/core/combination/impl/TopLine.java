package com.app.housie.core.combination.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Block;
import com.app.housie.model.HousieTicket;

import java.util.Objects;

/**
 *  Top Line - When a ticket has all the numbers present in the first row called/selected
 */
public class TopLine implements WinningCombination {

    /**
     * @return printable name of Top Line winning combination
     */
    @Override
    public String getName() {
        return Constants.TOP_LINE_NAME;
    }

    /**
     * @param housieTicket ticket which needs to be evaluated for Top Line combination
     * @return if the ticket has top line winning combination present in ticket
     */
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
