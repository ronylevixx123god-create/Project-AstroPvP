package org.arkhamnetwork.enchantments.enchantments.tools;

import com.gmail.nossr50.datatypes.skills.*;
import java.util.*;
import com.gmail.nossr50.events.experience.*;
import com.rit.sucy.*;
import org.bukkit.entity.*;
import org.bukkit.event.*;

final class SkillingListener implements Listener
{
    List<SkillType> effectedSkills;
    
    SkillingListener() {
        this.effectedSkills = new ArrayList<SkillType>(SkillType.GATHERING_SKILLS);
    }
    
    @EventHandler
    public void onMcMMOPlayerXpGain(final McMMOPlayerXpGainEvent e) {
        if (this.effectedSkills.contains(e.getSkill())) {
            final Player p = e.getPlayer();
            if (p.getItemInHand() != null && EnchantmentAPI.itemHasEnchantment(p.getItemInHand(), "Skilling")) {
                final int enchLevel = EnchantmentAPI.getEnchantments(p.getItemInHand()).get(EnchantmentAPI.getEnchantment("Skilling"));
                e.setRawXpGained((float)(e.getRawXpGained() * (1.0 + enchLevel * 0.04)));
            }
        }
    }
}
