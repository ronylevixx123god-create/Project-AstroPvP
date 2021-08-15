package me.ifamasssxd.cosmicsky.inventorypets.listeners;

import com.cosmicpvp.cosmicutils.utils.CC;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicsky.inventorypets.struct.pets.InventoryBansheePet;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class BansheeListener implements Listener {
    private HashMap<String, List<ItemStack>> savedItems = new HashMap<>();

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER)) {
            Player damager = (Player) event.getDamager();

            if (damager.hasMetadata("banshee_active")) {
                if (!event.getEntity().hasMetadata("bloodHoundNoBless")) {
                    event.setDamage(event.getDamage() * 1.40D);

                }
                damager.sendMessage(CC.translate("&4&l** BANSHEE PET &7[&f1.5x DMG&7] &4&l**"));
            }
        }
    }


    @EventHandler
    public void onEntityDamage2(EntityDamageByEntityEvent event) {
        if (event.getEntity().getType().equals(EntityType.PLAYER) && event.getDamager().getType().equals(EntityType.PLAYER)) {
            Player damager = (Player) event.getDamager();

            if (AstroItemSkinsAPI.hasSkinEquipped((Player)event.getEntity(), SkinType.THANKSGIVING_HELMET_WITCH) && Math.random() < 0.005) {
                event.getEntity().sendMessage(CC.translate("&e&l* WITCH HAT &7Activated &4Banshee Pet &7on&f " +damager.getName()+ " &e&l*"));
                damager.sendMessage(CC.translate("&e&l* &f" + event.getEntity().getName() +" WITCH HAT &4Banshee Pet &7Ability &e&l*"));
                InventoryBansheePet.startBansheeEffect((Player) event.getEntity(), damager);
            }
        }
    }
}
