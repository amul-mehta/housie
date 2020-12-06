package com.app.housie.core.generator.impl;

import com.app.housie.core.impl.ConsoleInputGameConfig;
import com.app.housie.model.Block;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Objects;

import static org.mockito.Mockito.when;

public class GeneratorTest {

    @Test
    public void testNumberGenerator_correctInput() {
        int min = 0;
        int max = 100;
        NumberGenerator numberGenerator = GeneratorFactory.getNumberGenerator(min, max);
        Assert.assertNotNull(numberGenerator.getRemainingNumbers());
        Assert.assertEquals(numberGenerator.getRemainingNumbers().size(), max - min + 1);
        for (int i = 0; i < numberGenerator.getRemainingNumbers().size(); i++) {
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
        int rowCount = 3;
        int columnCount = 5;
        int rowNumCount = 3;
        when(consoleInputGameConfig.getTicketSize()).thenReturn(new int[]{rowCount, columnCount});
        when(consoleInputGameConfig.getNumPerRow()).thenReturn(rowNumCount);
        TicketGenerator ticketGenerator = GeneratorFactory.getTicketGenerator(consoleInputGameConfig);
        Block[][] generatedTicket = ticketGenerator.generate();
        Assert.assertNotNull(generatedTicket);
        Assert.assertEquals(generatedTicket.length, rowCount);
        Assert.assertEquals(generatedTicket[0].length, columnCount);

        for(Block[] blocks : generatedTicket){
            int count=0;
            for(Block block: blocks){
                if(Objects.nonNull(block.getNumber())){
                    count++;
                }
            }
            Assert.assertEquals(count, rowNumCount);
        }



        // TODO: add more asserts
    }
}
