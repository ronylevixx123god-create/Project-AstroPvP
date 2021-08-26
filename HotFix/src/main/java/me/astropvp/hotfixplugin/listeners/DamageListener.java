package me.astropvp.hotfixplugin.listeners;

import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import me.astropvp.astropvpskins.AstroItemSkinsAPI;
import me.astropvp.astropvpskins.enums.SkinType;
import me.ifamasssxd.cosmicsky.inventorypets.CosmicSkyInventoryPets;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class DamageListener implements Listener {
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (((Player) e.getDamager()).getItemInHand().getType().name().endsWith("_AXE"))
                e.setDamage(e.getDamage() * 1.50);
        }
    }


    @EventHandler
    public void fixSlots(InventoryClickEvent e) {
        if (e.getWhoClicked() instanceof Player) {
            if (e.getCursor() != null && e.getCursor().getType() != null) {
                NBTWrapper wrapper = new NBTWrapper(e.getCursor());
                if (wrapper.hasKey("xpShopPrice")) {
                    wrapper.remove("xpShopPrice");
                    NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));


                    e.setCursor(wrapper.setNBTBase("cosmicData", builder.get()).build());
                }

            }
        }
    }
}

