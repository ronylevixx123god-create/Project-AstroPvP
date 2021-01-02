package org.arkhamnetwork.enchantments.enchantments.armor.boots;

import com.gmail.nossr50.datatypes.skills.SkillType;
import com.gmail.nossr50.events.experience.McMMOPlayerXpGainEvent;
import com.rit.sucy.EnchantmentAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

final class NimbleListener implements Listener {
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
                e.setRawXpGained((float) (e.getRawXpGained() * (1.0 + enchLevel * 0.1)));
            }
        }
    }
}
