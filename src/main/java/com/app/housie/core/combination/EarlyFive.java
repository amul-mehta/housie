package com.app.housie.core.combination;

import com.app.housie.commons.Constants;
import com.app.housie.model.Block;
import com.app.housie.model.Ticket;

import java.util.Objects;

public class EarlyFive implements WinningCombination {

    public static final int EARLY_FIVE_THRESHOLD = 5;

    @Override
    public String getName() {
        return Constants.EARLY_FIVE_NAME;
    }

    @Override
    public boolean evaluate(Ticket ticket) {
        Block[][] blocks = ticket.getContent();
        int count = 0;
        for (Block[] elements : blocks) {
            for (Block element : elements) {
                if (Objects.nonNull(element.getNumber()) && element.isSelected()) {
                    count++;
                    if (count == EARLY_FIVE_THRESHOLD)
                        return true;
                }
            }
        }
        return false;
    }
}
