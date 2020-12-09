package com.app.housie.model;

import com.app.housie.commons.Constants;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class HousieParams implements GameParams {
    private final int numOfPlayers;
    private final int numPerRow;
    private final int maxNumRange;
    @Builder.Default
    private int[] ticketSize = new int[]{Constants.DEFAULT_TICKET_ROW_SIZE, Constants.DEFAULT_TICKET_COLUMN_SIZE};


    /**
     * @return true if the initialized parameters are valid for a housie game
     */
    @Override
    public boolean isValid(){
        return maxNumRange > 0
                && numOfPlayers > 1
                && ticketSize.length == 2
                && ticketSize[0] > 0
                && ticketSize[1] > 0
                && numPerRow > 0
                && numPerRow <= ticketSize[1]
                && numPerRow * ticketSize[0] <= maxNumRange
                && numPerRow * ticketSize[0] >= Constants.EARLY_FIVE_THRESHOLD;
    }
}
