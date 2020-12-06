package com.app.housie.model;

import com.app.housie.commons.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class HousieParams implements GameParams {
    private int numOfPlayers;
    private int numPerRow;
    private int maxNumRange;
    @Builder.Default
    private int[] ticketSize = new int[]{Constants.DEFAULT_TICKET_ROW_SIZE, Constants.DEFAULT_TICKET_COLUMN_SIZE};


    @Override
    public boolean isValid(){
        return maxNumRange > 0
                && numOfPlayers > 0
                && ticketSize.length == 2
                && ticketSize[0] > 0
                && ticketSize[1] > 0
                && numPerRow > 0
                && numPerRow * ticketSize[0] <= maxNumRange;
    }
}
