package com.app.housie.core;

import com.app.housie.model.Block;
import com.app.housie.core.combination.WinningCombination;
import com.app.housie.model.Player;
import com.app.housie.model.Ticket;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class GameState {
    // status of game, is it completed, keep track of numbers that are generated, remaining, etc.
    List<WinningCombination> winningCombinations;
    Map<WinningCombination, Set<Player>> currentState;
    List<Ticket> tickets;

    public GameState(List<WinningCombination> winningCombinations, List<Ticket> tickets) {
        this.winningCombinations = winningCombinations;
        this.tickets = tickets;
        currentState = new HashMap<>();
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
            if (c.evaluate(matchingTicket)) {
                if (!currentState.containsKey(c))
                    currentState.put(c, new HashSet<>());
                currentState.get(c).add(matchingTicket.getPlayer());
            }
        });
    }

    private List<Ticket> updateTickets(int number) {
        List<Ticket> selectedtickets = new ArrayList<>();
        boolean found;
        for (Ticket ticket : tickets) {
            found = false;
            Block[][] contents = ticket.getContent();
            for (Block[] blocks : contents) {
                for (Block block : blocks) {
                    Integer num = block.getNumber();
                    if (Objects.nonNull(num) && num.equals(number)) {
                        block.setSelected(true);
                        selectedtickets.add(ticket);
                        found = true;
                        break;
                    }
                }
                if (found)
                    break;
            }
        }

        return selectedtickets;
    }

    public void printSummary() {
        Map<String, Set<String>> playerCombinationMap = new HashMap<>();
        currentState.forEach((winningCombination, players) -> {
            players.forEach(player -> {
                if (!playerCombinationMap.containsKey(player.getName()))
                    playerCombinationMap.put(player.getName(), new HashSet<>());
                playerCombinationMap.get(player.getName()).add(winningCombination.getName());

            });
        });
        tickets.stream().map(Ticket::getPlayer).forEach(player -> {
            Set<String> combinationsWon = playerCombinationMap.getOrDefault(player.getName(), new HashSet<>());

            String combinationWonString = String.join(" and ", combinationsWon);
            if (combinationWonString.length() == 0) {
                combinationWonString = "Nothing";
            }
            log.info("{} : {}", player.getName(), combinationWonString);
        });

    }
}

