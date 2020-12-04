package com.app.housie.model;

public interface Combination {
    void setName(String name);

    String getName();

    boolean evaluate(Ticket ticket);
}
