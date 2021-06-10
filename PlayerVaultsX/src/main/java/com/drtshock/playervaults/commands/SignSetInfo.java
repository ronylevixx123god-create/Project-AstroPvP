package com.drtshock.playervaults.commands;

public class SignSetInfo
{
    private String owner;
    private final int number;
    private boolean self;
    
    public SignSetInfo(final String owner, final int number) {
        this.self = false;
        this.owner = owner;
        this.number = number;
    }
    
    public SignSetInfo(final int number) {
        this.self = false;
        this.number = number;
        this.self = true;
    }
    
    public boolean isSelf() {
        return this.self;
    }
    
    public String getOwner() {
        return this.owner;
    }
    
    public int getChest() {
        return this.number;
    }
}
