package me.astropvp.astropvpskins.objects;

import me.astropvp.astropvpskins.enums.SkinType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ItemSkin {

    public void onEquip(Player player) {}

    public void onUnequip(Player player) {}

    public void onAttack(Player damager, Entity damaged, EntityDamageByEntityEvent event) {}

    public void onAttacked(Player damaged, Entity damager, EntityDamageByEntityEvent event) {}


}
