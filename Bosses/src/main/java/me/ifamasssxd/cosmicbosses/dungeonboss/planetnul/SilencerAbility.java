package me.ifamasssxd.cosmicbosses.dungeonboss.planetnul;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ParticleEffect;
import me.ifamasssxd.cosmicbosses.dungeonboss.DungeonAbility;
import me.ifamasssxd.cosmicbosses.struct.BlockModifier;
import me.ifamasssxd.cosmicbosses.struct.Boss;
import me.ifamasssxd.cosmicbosses.struct.BossAbility;
import org.arkhamnetwork.enchantments.Enchantments;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class SilencerAbility extends DungeonAbility {
    public SilencerAbility(Boss boss) {
        super(boss);
    }

    @Override
    public void onTick() {

    }

    @Override
    public long getCooldown() {
        return TimeUnit.SECONDS.toMillis(9L);
    }

    @Override
    public String getAbilityName() {
        return "Silencer";
    }

    @Override
    public boolean shouldStopTicking() {
        return System.currentTimeMillis() - this.initTime >= this.getCooldown();
    }

    @Override
    public void onAbilityEnable() {
        this.initAbility();

        long durationTicks = 7L * 20L;

        for (Player player : this.getBoss().getNearbyEntities(250.0, EntityType.PLAYER).stream().map(e -> (Player)e).collect(Collectors.toList())) {
            this.boss.sendMessage(player, CC.translate("&5&lSilence, all of you!"));
            player.setMetadata("noDefenseProcs", new FixedMetadataValue(Enchantments.getInstance(), true));
            player.playSound(player.getLocation(), Sound.WITHER_HURT, 1.0f, 0.25f);
            player.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "* SILENCED " + ChatColor.GRAY + "[" + durationTicks / 20L + "s] " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "*");

            Bukkit.getScheduler().runTaskLater(Enchantments.getInstance(), new Runnable() {
                @Override
                public void run() {
                    player.removeMetadata("noDefenseProcs", (Plugin)Enchantments.getInstance());
                }
            }, durationTicks);
        }
    }

    @Override
    public void onAbilityDisable() {
        ((CorruptCosmonaut)this.boss).doTrickster();
    }

    @Override
    public void onAbilityEvent(Event p0) {

    }
}
