package com.app.housie.core.model;

import com.app.housie.model.HousieParams;
import org.junit.Assert;
import org.junit.Test;

public class HousieParamsTest {

    @Test
    public void housieParamsTest_maxNumRangeIncorrect(){
        HousieParams housieParams =
                HousieParams.builder()
                        .maxNumRange(0)
                        .numOfPlayers(10)
                        .ticketSize(new int[]{3, 10})
                        .numPerRow(2)
                        .build();
        Assert.assertFalse(housieParams.isValid());
    }

    @Test
    public void housieParamsTest_numPlayersIncorrect(){
        HousieParams housieParams =
                HousieParams.builder()
                        .maxNumRange(100)
                        .numOfPlayers(1)
                        .ticketSize(new int[]{3, 10})
                        .numPerRow(2)
                        .build();
        Assert.assertFalse(housieParams.isValid());
    }

    @Test
    public void housieParamsTest_ticketSizeIncorrect(){
        HousieParams housieParams =
                HousieParams.builder()
                        .maxNumRange(0)
                        .numOfPlayers(10)
                        .ticketSize(new int[]{3, 0})
                        .numPerRow(2)
                        .build();
        Assert.assertFalse(housieParams.isValid());
    }

    @Test
    public void housieParamsTest_ticketSizeIncorrect2(){
        HousieParams housieParams =
                HousieParams.builder()
                        .maxNumRange(10)
                        .numOfPlayers(10)
                        .ticketSize(new int[]{3, 10,19})
                        .numPerRow(2)
                        .build();
        Assert.assertFalse(housieParams.isValid());
    }

    @Test
    public void housieParamsTest_combinationIncorrect(){
        HousieParams housieParams =
                HousieParams.builder()
                        .maxNumRange(10)
                        .numOfPlayers(2)
                        .ticketSize(new int[]{3, 10})
                        .numPerRow(6)
                        .build();
        Assert.assertFalse(housieParams.isValid());
    }

    @Test
    public void housieParamsTest_combinationIncorrect2(){
        HousieParams housieParams =
                HousieParams.builder()
                        .maxNumRange(10)
                        .numOfPlayers(2)
                        .ticketSize(new int[]{2, 3})
                        .numPerRow(2)
                        .build();
        Assert.assertFalse(housieParams.isValid());
    }
}
