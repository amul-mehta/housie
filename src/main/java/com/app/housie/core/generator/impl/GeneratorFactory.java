package com.app.housie.core.generator.impl;

import com.app.housie.core.generator.impl.NumberGenerator;
import com.app.housie.core.generator.impl.TicketGenerator;
import com.app.housie.core.impl.ConsoleInputGameConfig;

public class GeneratorFactory {
    public static NumberGenerator getNumberGenerator(int min, int max){
        return new NumberGenerator(min, max);
    }

    public static TicketGenerator getTicketGenerator(ConsoleInputGameConfig gameConfig){
        return new TicketGenerator(gameConfig);
    }
}
