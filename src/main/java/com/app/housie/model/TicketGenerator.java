package com.app.housie.model;

import java.util.ArrayList;
import java.util.List;

public class TicketGenerator {
    int n;
    List<Ticket> tickets;

    public TicketGenerator(int n) {
        this.n = n;
        this.tickets = new ArrayList<>();
    }

    public List<Ticket> generate() {
        return tickets;
    }

}
