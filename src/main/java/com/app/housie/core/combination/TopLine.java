package com.app.housie.core.combination;


import com.app.housie.commons.Constants;
import com.app.housie.model.Block;
import com.app.housie.model.Ticket;
import lombok.Getter;
import java.util.Objects;

@Getter
public class TopLine implements WinningCombination {

    @Override
    public String getName() {
        return Constants.TOP_LINE_NAME;
    }

    @Override
    public boolean evaluate(Ticket ticket) {
        Block[][] blocks = ticket.getContent();
        for (Block element : blocks[0]) {
            if (Objects.nonNull(element.getNumber()) || !element.isSelected())
                return false;
        }
        return true;
    }
}
