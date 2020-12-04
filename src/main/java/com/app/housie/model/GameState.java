package com.app.housie.model;

import java.util.*;


public class GameState {
    // status of game, is it completed, keep track of numbers that are generated, remaining, etc.
    List<Combination> combinations;
    Map<Combination, Set<Player>> currentState;
    Map<Integer, List<Block>> numberBlockMap;
    List<Ticket> tickets;

    public GameState(List<Combination> combinations, List<Ticket> tickets){
        this.combinations = combinations;
        this.tickets = tickets;
        currentState = new HashMap<>();
        populateMap();
    }

    private void populateMap() {
        tickets.stream().map(Ticket::getContent).forEach(blocks -> {
            for(Block[] block : blocks){
                for(Block b: block){
                    if(Objects.nonNull(b.getNumber())){
                        if(!numberBlockMap.containsKey(b.getNumber()))
                            numberBlockMap.put(b.getNumber(), new ArrayList<>());
                        numberBlockMap.get(b.getNumber()).add(b);
                    }
                }
            }
        });

    }

    boolean isCompleted() {
        return currentState.size() == combinations.size();
    }

    public boolean updateState(int number){
        // update tickets
        // evaluate combinations
        updateTickets(number);
        updateCombinations();
        return isCompleted();
    }

    private void updateCombinations() {
    }

    private void updateTickets(int number){
        List<Block> matchingBlocks = numberBlockMap.getOrDefault(number, new ArrayList<>());
        matchingBlocks.stream().map(b -> {
            b.setSelected(true);
            return b;
        });
    }
}

