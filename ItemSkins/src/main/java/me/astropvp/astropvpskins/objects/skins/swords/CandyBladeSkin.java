package me.astropvp.astropvpskins.objects.skins.swords;

import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.mastery.MasteryLifesteal;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CandyBladeSkin extends ItemSkin {
    @Override
    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {
        if(damaged instanceof Player) {
            new MasteryLifesteal().applyDamagedOtherPlayer(damager, (Player) damaged, event, 10);
        }
    }
}
