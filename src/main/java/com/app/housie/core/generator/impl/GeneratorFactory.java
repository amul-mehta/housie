package com.app.housie.core.generator.impl;

import com.app.housie.model.HousieParams;


public class GeneratorFactory {
    /**
     * @param min minimum allowed value for the number generator
     * @param max maximum allowed value for the number generator
     * @return instance of NumberGenerator that can generate unique random numbers between given range
     */
    public static NumberGenerator getNumberGenerator(int min, int max) {
        return new NumberGenerator(min, max);
    }

    /**
     * @param housieParams Housie Game configuration parameters
     * @return instance of NumberGenerator that can generate Housie Ticket based on the parameters
     */
    public static TicketGenerator getTicketGenerator(HousieParams housieParams) {
        return new TicketGenerator(housieParams);
    }
}
