package com.drtshock.playervaults.converters;

import org.bukkit.command.*;

public interface Converter
{
    int run(final CommandSender p0);
    
    boolean canConvert();
    
    String getName();
}
