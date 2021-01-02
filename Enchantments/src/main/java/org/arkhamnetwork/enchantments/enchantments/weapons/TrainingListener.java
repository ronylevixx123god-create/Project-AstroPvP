package org.arkhamnetwork.enchantments.enchantments.weapons;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

final class TrainingListener implements Listener {
    List<SkillType> effectedSkills;

    TrainingListener() {
        this.effectedSkills = new ArrayList<SkillType>(SkillType.COMBAT_SKILLS);
    }

    @EventHandler
    public void onMcMMOPlayerXpGain(final McMMOPlayerXpGainEvent e) {
        if (this.effectedSkills.contains(e.getSkill())) {
            final Player p = e.getPlayer();
            if (p.getItemInHand() != null && EnchantmentAPI.itemHasEnchantment(p.getItemInHand(), "Training")) {
                final int enchLevel = EnchantmentAPI.getEnchantments(p.getItemInHand()).get(EnchantmentAPI.getEnchantment("Training"));
                e.setRawXpGained((float) (e.getRawXpGained() * (1.0 + enchLevel * 0.035)));
            }
        }
    }
}
