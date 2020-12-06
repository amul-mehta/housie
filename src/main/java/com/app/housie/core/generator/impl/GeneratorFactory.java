package com.app.housie.core.generator.impl;

import com.app.housie.core.impl.ConsoleInputGameConfig;
import com.app.housie.model.HousieParams;

public class GeneratorFactory {
    public static NumberGenerator getNumberGenerator(int min, int max) {
        return new NumberGenerator(min, max);
    }

    public static TicketGenerator getTicketGenerator(HousieParams housieParams) {
        return new TicketGenerator(housieParams);
    }
}
