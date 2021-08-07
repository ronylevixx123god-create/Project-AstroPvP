package me.astropvp.astropvpskins.objects.skins.boots;

import me.astropvp.astropvpskins.enums.SkinType;
import me.astropvp.astropvpskins.objects.ItemSkin;
import org.arkhamnetwork.enchantments.mastery.MasteryDemonicGateway;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CamouflageBootsSkin extends ItemSkin {

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {
        (new MasteryDemonicGateway()).applyDefenseEffect(damaged, (LivingEntity)damager, 5, event);
    }
}
