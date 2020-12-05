package com.app.housie.core.combination;

import com.app.housie.commons.Constants;
import com.app.housie.model.Block;
import com.app.housie.model.Ticket;

import java.util.Objects;

public class FullHouse implements WinningCombination {
    @Override
    public String getName() {
        return Constants.FULL_HOUSE_NAME;
    }

    @Override
    public boolean evaluate(Ticket ticket) {
        Block[][] blocks = ticket.getContent();
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
