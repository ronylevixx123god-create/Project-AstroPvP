package org.arkhamnetwork.enchantments.enchantments.armor.boots;

import com.gmail.nossr50.datatypes.skills.*;
import java.util.*;
import com.gmail.nossr50.events.experience.*;
import com.rit.sucy.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

final class NimbleListener implements Listener
{
    List<SkillType> effectedSkills;
    
    NimbleListener() {
        this.effectedSkills = new ArrayList<SkillType>(Arrays.asList(SkillType.ACROBATICS));
    }
    
    @EventHandler
    public void onMcMMOPlayerXpGain(final McMMOPlayerXpGainEvent e) {
        if (this.effectedSkills.contains(e.getSkill())) {
            final Player p = e.getPlayer();
            if (p.getEquipment().getBoots() != null && EnchantmentAPI.itemHasEnchantment(p.getEquipment().getBoots(), "Nimble")) {
                final int enchLevel = EnchantmentAPI.getEnchantments(p.getEquipment().getBoots()).get(EnchantmentAPI.getEnchantment("Nimble"));
                e.setRawXpGained((float)(e.getRawXpGained() * (1.0 + enchLevel * 0.1)));
            }
        }
    }
}
