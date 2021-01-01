package org.arkhamnetwork.enchantments.listeners;

import com.rit.sucy.*;
import org.bukkit.event.*;

public class CustomEnchantListener<T extends CustomEnchantment> implements Listener
{
    protected T customEnchant;
    
    public CustomEnchantListener(final T enchant) {
        this.customEnchant = enchant;
    }
}
