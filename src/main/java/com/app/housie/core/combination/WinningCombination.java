package com.app.housie.core.combination;

import com.app.housie.model.HousieTicket;

/**
 * This interface defines the methods needed to evaluate and identify a winning combination
 * Winning Combination defines a pattern present in ticket that results in winning in a Housie Game
 */
public interface WinningCombination {
    /**
     * @return Printable Name to identify a winning combination
     */
    String getName();

    /**
     * @param housieTicket the ticket that needs to be evaluated
     * @return if the ticket has the pattern eligible for winning this combination
     */
    boolean evaluate(HousieTicket housieTicket);
}
