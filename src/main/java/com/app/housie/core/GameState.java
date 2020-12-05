package com.app.housie.core;

import com.app.housie.commons.Constants;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Block;
import com.app.housie.model.Player;
import com.app.housie.model.Ticket;
import lombok.extern.slf4j.Slf4j;

import java.util.*;


@Slf4j
public class GameState {
    private final List<WinningCombination> winningCombinations;
    private final Map<WinningCombination, Player> currentState;
    private final List<Ticket> tickets;

    public GameState(List<WinningCombination> winningCombinations, List<Ticket> tickets) {
        this.winningCombinations = winningCombinations;
        this.tickets = tickets;
        this.currentState = new HashMap<>();
    }

    boolean isCompleted() {
        return currentState.size() == winningCombinations.size();
    }

    public boolean updateState(int number) {
        List<Ticket> matchingTickets = updateTickets(number);
        matchingTickets.forEach(this::updateCombinations);
        return isCompleted();
    }

    private void updateCombinations(Ticket matchingTicket) {
        winningCombinations.forEach(c -> {
            if (!currentState.containsKey(c) && c.evaluate(matchingTicket)) {
                currentState.put(c, matchingTicket.getPlayer());
                log.info("We have a winner: {} has won '{}' winning combination.", matchingTicket.getPlayer().getName(), c.getName());
            }
        });
    }

    private List<Ticket> updateTickets(int number) {
        List<Ticket> selectedTickets = new ArrayList<>();
        boolean found;
        for (Ticket ticket : tickets) {
            found = false;
            Block[][] contents = ticket.getContent();
            for (Block[] blocks : contents) {
                for (Block block : blocks) {
                    Integer num = block.getNumber();
                    if (Objects.nonNull(num) && num.equals(number)) {
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

    public void printSummary() {
        Map<String, Set<String>> playerCombinationMap = new HashMap<>();
        currentState.forEach((winningCombination, player) -> {
            if (!playerCombinationMap.containsKey(player.getName()))
                playerCombinationMap.put(player.getName(), new HashSet<>());
            playerCombinationMap.get(player.getName()).add(winningCombination.getName());
        });
        tickets.stream().map(Ticket::getPlayer).forEach(player -> {
            Set<String> combinationsWon = playerCombinationMap.getOrDefault(player.getName(), new HashSet<>());

            String combinationWonString = String.join(Constants.COMBINATION_WON_DELIMITER, combinationsWon);
            if (combinationWonString.isEmpty()) {
                combinationWonString = Constants.NOTHING_WON;
            }
            log.info("{} : {}", player.getName(), combinationWonString);
        });

    }
}

