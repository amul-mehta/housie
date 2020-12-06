package com.app.housie.core.impl;

import com.app.housie.commons.Constants;
import com.app.housie.core.GameConfig;
import com.app.housie.core.GameState;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Block;
import com.app.housie.model.Player;
import com.app.housie.model.Ticket;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


@Slf4j
public class HousieGameState implements GameState {
    private final List<WinningCombination> winningCombinations;
    private final Map<WinningCombination, Player> currentState;
    private final List<Ticket> tickets;

    public HousieGameState(List<WinningCombination> winningCombinations, List<Ticket> tickets) {
        this.winningCombinations = winningCombinations;
        this.tickets = tickets;
        this.currentState = new HashMap<>();
    }

    /**
     * @return if all the winning combinations are completed by one or more players
     */
    @Override
    public boolean isCompleted() {
        return currentState.size() == winningCombinations.size();
    }


    /**
     * Update
     *
     * @param calledNumber number called
     * @return if all the combinations have been occured (game is complete)
     */
    public boolean updateState(int calledNumber) {
        List<Ticket> matchingTickets = updateTickets(calledNumber);
        matchingTickets.forEach(this::updateCombinations);
        return isCompleted();
    }

    /**
     * updates currentState if ticket has one or more of available winning combination
     *
     * @param matchingTicket the ticket that is to be evaluated for winning combination(s)
     */
    private void updateCombinations(Ticket matchingTicket) {
        winningCombinations.forEach(c -> {
            if (!currentState.containsKey(c) && c.evaluate(matchingTicket)) {
                currentState.put(c, matchingTicket.getPlayer());
                log.info("We have a winner: {} has won '{}' winning combination.", matchingTicket.getPlayer().getName(), c.getName());
            }
        });
    }

    /**
     * TODO: fix the language
     *
     * @param calledNumber the number to be checked for the ticket
     * @return List of Tickets that have the number in their contents
     */
    private List<Ticket> updateTickets(int calledNumber) {
        List<Ticket> selectedTickets = new ArrayList<>();
        boolean found;
        for (Ticket ticket : tickets) {

            found = false;
            Block[][] contents = ticket.getContent();

            for (Block[] blocks : contents) {
                for (Block block : blocks) {

                    Integer num = block.getNumber();
                    if (Objects.nonNull(num) && num.equals(calledNumber)) {
                        block.setSelected(true);
                        selectedTickets.add(ticket);
                        found = true;
                        break;
                    }
                }

                if (found)
                    break;
            }
        }

        return selectedTickets;
    }

    /**
     * Prints the per Player summary of all the winning combinations that are present in their respective ticket
     */
    @Override
    public void printSummary() {
        Map<String, Set<String>> playerCombinationMap = new HashMap<>();

        currentState.forEach((winningCombination, player) -> {
            if (!playerCombinationMap.containsKey(player.getName()))
                playerCombinationMap.put(player.getName(), new HashSet<>());
            playerCombinationMap.get(player.getName()).add(winningCombination.getName());
        });

        tickets.stream()
                .map(Ticket::getPlayer)
                .forEach(player -> {
                    Set<String> combinationsWon = playerCombinationMap.getOrDefault(player.getName(), new HashSet<>());
                    String combinationWonString = String.join(Constants.COMBINATION_WON_DELIMITER, combinationsWon);
                    if (combinationWonString.isEmpty()) {
                        combinationWonString = Constants.NOTHING_WON;
                    }
                    log.info("{} : {}", player.getName(), combinationWonString);
                });
    }
}

