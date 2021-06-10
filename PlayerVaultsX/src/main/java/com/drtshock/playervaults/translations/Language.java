package com.drtshock.playervaults.translations;

public enum Language
{
    ENGLISH("english"), 
    BULGARIAN("bulgarian"), 
    TURKISH("turkish"), 
    DUTCH("dutch");
    
    private String friendlyName;
    
    private Language(final String friendlyName) {
        this.friendlyName = friendlyName;
    }
    
    public String getFriendlyName() {
        return this.friendlyName;
    }
}
