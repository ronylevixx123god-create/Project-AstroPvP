package org.arkhamnetwork.enchantments.enchantments.tools;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

final class SkillingListener implements Listener {
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
                e.setRawXpGained((float) (e.getRawXpGained() * (1.0 + enchLevel * 0.04)));
            }
        }
    }
}
