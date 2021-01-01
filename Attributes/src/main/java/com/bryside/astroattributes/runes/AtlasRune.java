package com.bryside.astroattributes.runes;

import com.bryside.astroattributes.DragonRunesPlugin;
import com.bryside.astroattributes.objects.Rune;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Maps;
import org.arkhamnetwork.enchantments.util.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;


public class AtlasRune extends Rune implements Listener {

    HashMap<Player, Integer> atlases = Maps.newHashMap();
    HashMap<Player, Integer> hits = Maps.newHashMap();

    public String[] getLore(int level, int chance) {
        if (level > this.getMaxLevel()) {
            level = this.getMaxLevel();
        }

        return new String[]{CC.RedB + "Level: " + CC.DarkGray + level, CC.RedB + "Apply: " + CC.DarkGray + chance + "%", "", CC.RedB + "''Compound Punishment''", CC.Red + "% of damage from attacks is combined", CC.Red + "every 2-5 attacks, depending on level.", "", CC.Red + "Example: if you deal 10 DMG", "§cper swing, you might deal 2 DMG", "§cfor 2 swings, followed by 26 DMG", "§con your 3rd swing.", "", CC.Gray + "Titan Attributes can " + CC.Underline + "ONLY" + CC.Gray + " be applied", CC.Gray + "to " + CC.Red + "Heroic Weapons " + CC.Gray + "and only", CC.Gray + CC.Underline + "1 attribute" + CC.Gray + " can be imbued per weapon.", "", CC.Gray + "To learn more about Titan Attributes, use " + CC.Red + "/titan"};
    }

    public String getDisplayName() {
        return CC.RedB + "Titan Attribute (" + CC.DarkGrayB + "Atlas" + CC.RedB + ")";
    }

    public Material getMaterial() {
        return Material.SPIDER_EYE;
    }

    public ItemStack getItemStack(int level, int success) {
        ItemStack item = new ItemStack(this.getMaterial());
        ItemMeta itemMeta = item.getItemMeta();
        List lore = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList();
        String[] lore2;
        int length = (lore2 = this.getLore(level, success)).length;

        for (int i = 0; i < length; ++i) {
            String l = lore2[i];
            ((List) lore).add(l);
        }

        itemMeta.setLore((List) lore);
        itemMeta.setDisplayName(this.getDisplayName());
        item.setItemMeta(itemMeta);
        return item;
    }

    public void setNeededHits(Player player, Integer integer) {
        hits.put(player, integer);
    }

    @Override
    public void procDamageEvent(final EntityDamageByEntityEvent e, final int level, final Player damager) {

        if (atlases.containsKey(damager)) {
            double amt = 1 + (atlases.get(damager) / 100);
            ParticleEffect.playEffect(ParticleEffect.LARGE_EXPLODE, e.getEntity().getLocation().add(0, 1f, 0), 1.0f, 3);

            atlases.remove(damager, atlases.get(damager));
            e.setDamage((e.getDamage(EntityDamageEvent.DamageModifier.BASE)) * amt);

            atlases.remove(damager);

        }
        if (hits.get(damager) != null && hits.get(damager) - 1 > 0) {
            setNeededHits(damager, hits.get(damager) - 1);
            return;
        }
        if (hits.get(damager) == null) {
            setNeededHits(damager, ThreadLocalRandom.current().nextInt(2, 5));
            return;
        }
        final int amount = (10 * level) < 80 ? 10 * level : 70;
        damager.sendMessage(CC.translate("&4&l*ATLAS* &cNext attack: +&f" + (100 + amount) + "% &cDMG"));
        e.setDamage(e.getDamage(EntityDamageEvent.DamageModifier.BASE) * 0.25);
        Bukkit.getScheduler().runTask(DragonRunesPlugin.getInstance(), () -> {
            atlases.put(damager, amount);

            setNeededHits(damager, ThreadLocalRandom.current().nextInt(2, 7));

        });
    }


}