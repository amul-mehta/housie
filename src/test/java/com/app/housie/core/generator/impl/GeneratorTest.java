package com.app.housie.core.generator.impl;

import com.app.housie.core.impl.ConsoleInputGameConfig;
import com.app.housie.model.Block;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class GeneratorTest {

    @Test
    public void testNumberGenerator_correctInput() {
        int min = 0;
        int max = 100;
        NumberGenerator numberGenerator = GeneratorFactory.getNumberGenerator(min, max);
        Assert.assertNotNull(numberGenerator.getRemainingNumbers());
        Assert.assertEquals(numberGenerator.getRemainingNumbers().size(), max - min + 1);
        for (int i = 0; i <= max - min; i++) {
            int generatedNumber = numberGenerator.generate();
            Assert.assertTrue(generatedNumber >= min && generatedNumber <= max);
        }
    }

    @Test
    public void testNumberGenerator_incorrectInput() {
        int min = 200;
        int max = 100;
        NumberGenerator numberGenerator = GeneratorFactory.getNumberGenerator(min, max);
        Assert.assertNotNull(numberGenerator.getRemainingNumbers());
        Assert.assertEquals(numberGenerator.getRemainingNumbers().size(), 0);
    }

    @Test
    public void testTicketGenerator() {
        ConsoleInputGameConfig consoleInputGameConfig = Mockito.mock(ConsoleInputGameConfig.class);
        when(consoleInputGameConfig.getEndRange()).thenReturn(100);
        when(consoleInputGameConfig.getBoardSize()).thenReturn(new int[]{3, 5});
        when(consoleInputGameConfig.getNumOfPlayers()).thenReturn(2);
        when(consoleInputGameConfig.getNumPerRow()).thenReturn(3);
        TicketGenerator ticketGenerator = GeneratorFactory.getTicketGenerator(consoleInputGameConfig);
        Block[][] generatedTicket = ticketGenerator.generate();
        Assert.assertNotNull(generatedTicket);
        // TODO: add more asserts
    }
}
