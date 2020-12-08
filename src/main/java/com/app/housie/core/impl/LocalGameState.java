package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.GameState;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Block;
import com.app.housie.model.HousieTicket;
import com.app.housie.model.Player;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


/**
 * this class stores the state of game in memory. The state includes:
 *  - All the winning combinations that are valid in this game
 *  - All the tickets in their latest state
 * this class is also responsible for keeping track if the game is completed (i.e. all the winning combinations are
 * claimed by one or more players)
 */
@Slf4j
@Getter(AccessLevel.PRIVATE)
public class LocalGameState implements GameState<Integer> {
    private final List<WinningCombination> winningCombinations;
    @Getter(AccessLevel.PACKAGE)
    private final Map<WinningCombination, Player> currentState;
    private final List<HousieTicket> housieTickets;

    public LocalGameState(List<WinningCombination> winningCombinations,
                          List<HousieTicket> housieTickets) {
        this.winningCombinations = winningCombinations;
        this.housieTickets = housieTickets;
        this.currentState = new HashMap<>();
    }

    /**
     * @return if all the winning combinations are completed by one or more players
     */
    @Override
    public boolean isCompleted() {
        return getCurrentState().size() == getWinningCombinations().size();
    }


    /**
     * when a number is called, check each ticket if the ticket has the number, if it does, then mark
     * the number as selected, and check if the ticket is eligible to win one or more of the un-claimed
     * winning combinations
     *
     * In case of tie, the winner is selected based on the order when tickets were initialized.
     *
     * @param calledNumber number called which can result in state update
     */
    @Override
    public void updateState(Integer calledNumber) {
        List<HousieTicket> matchingHousieTickets = updateTickets(calledNumber);
        matchingHousieTickets.forEach(this::updateCombinations);
    }

    /**
     * updates currentState if ticket has one or more of available winning combination
     *
     * @param matchingHousieTicket the ticket that is to be evaluated for winning combination(s)
     */
    private void updateCombinations(HousieTicket matchingHousieTicket) {
        getWinningCombinations().forEach(combination -> {
            if (!getCurrentState().containsKey(combination) && combination.evaluate(matchingHousieTicket)) {
                getCurrentState().put(combination, matchingHousieTicket.getPlayer());
                log.info("We have a winner: {} has won '{}' winning combination.",
                        matchingHousieTicket.getPlayer().getName(), combination.getName());
            }
        });
    }

    /**
     * for each ticket, mark if the number is present in the ticket
     *
     * @param calledNumber the number to be searched
     * @return tickets that have the number present
     */
    private List<HousieTicket> updateTickets(int calledNumber) {
        List<HousieTicket> selectedHousieTickets = new ArrayList<>();
        boolean found;
        for (HousieTicket housieTicket : getHousieTickets()) {

            found = false;
            Block[][] contents = housieTicket.getTicket();

            for (Block[] blocks : contents) {
                for (Block block : blocks) {
                    if (Objects.nonNull(block) && block.getNumber() == calledNumber) {
                        block.setSelected(true);
                        selectedHousieTickets.add(housieTicket);
                        found = true;
                        break;
                    }
                }

                if (found)
                    break;
            }
        }

        return selectedHousieTickets;
    }

    /**
     * Prints the per {@link Player} summary of all the winning combinations
     * that are present in each {@link Player}'s respective ticket
     */
    @Override
    public void printSummary() {
        Map<String, Set<String>> playerCombinationMap = new HashMap<>();

        getCurrentState().forEach((winningCombination, player) -> {
            if (!playerCombinationMap.containsKey(player.getName()))
                playerCombinationMap.put(player.getName(), new HashSet<>());
            playerCombinationMap.get(player.getName()).add(winningCombination.getName());
        });

        log.info("=====================================");
        log.info("Summary");
        getHousieTickets().stream()
                .map(HousieTicket::getPlayer)
                .forEach(player -> {
                    Set<String> combinationsWon = playerCombinationMap.getOrDefault(player.getName(), new HashSet<>());
                    String combinationWonString = String.join(Constants.COMBINATION_WON_DELIMITER, combinationsWon);
                    if (combinationWonString.isEmpty()) {
                        combinationWonString = Constants.NOTHING_WON;
                    }
                    log.info("{} : {}", player.getName(), combinationWonString);
                });
        log.info("=====================================");
    }
}
