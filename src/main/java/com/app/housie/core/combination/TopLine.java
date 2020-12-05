package com.app.housie.core.combination;


import com.app.housie.model.Block;
import com.app.housie.model.Ticket;
import lombok.Getter;
import java.util.Objects;

@Getter
public class TopLine implements WinningCombination {
    String name;


    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean evaluate(Ticket ticket) {
        Block[][] blocks = ticket.getContent();
        for (Block element : blocks[0]) {
            if (Objects.isNull(element.getNumber()) || !element.isSelected())
                return false;
        }
        return true;
    }
}
