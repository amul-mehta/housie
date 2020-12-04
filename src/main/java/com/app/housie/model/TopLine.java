package com.app.housie.model;


import lombok.Getter;

@Getter
public class TopLine implements Combination {
    String name;


    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void getName() {
        return name;
    }

    @Override
    public boolean evaluate(Ticket ticket) {
        return false;
    }
}
