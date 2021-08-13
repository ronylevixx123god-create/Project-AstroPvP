package org.arkhamnetwork.enchantments.enchantments.swords;

import com.bryside.astroattributes.DragonTitanAttributesAPI;
import com.bryside.astroattributes.enums.RuneType;
import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
import com.cosmicpvp.cosmicutils.utils.WorldUtil;
import com.rit.sucy.CustomEnchantment;
import me.astropvp.astroparticles.utils.ParticleUtils;
import org.arkhamnetwork.enchantments.Enchantments;
import org.arkhamnetwork.enchantments.util.RageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class Rage extends CustomEnchantment {
    public Rage() {
        super("Rage", GeneralUtil.weapons, 2);
        this.max = 6;
        this.base = 10.0D;
        this.interval = 8.0D;
        Bukkit.getPluginManager().registerEvents(new RageListener(), Enchantments.getInstance());
    }

    public void applyEffect(LivingEntity user, LivingEntity target, int enchantLevel, EntityDamageByEntityEvent event) {
        if (target instanceof ArmorStand) {
            return;
        }
        if (!event.isCancelled() && event.getDamage(EntityDamageEvent.DamageModifier.BASE) > 0.0D && user instanceof Player) {
            Player pUser = (Player) user;
            if (!pUser.getItemInHand().getType().name().toLowerCase().contains("_sword") || !(event.getDamager() instanceof LivingEntity)) {
                return;
            }

            double mod = enchantLevel * 0.55 * RageUtils.getComboOfPlayer(pUser, target instanceof Player);

            if (ParticleUtils.isPlayerIndicating((Player) user)) {
                playRageEffect(target.getEyeLocation(), enchantLevel);
            }

            if (mod > 15) {
                mod = 15;
            }

            double damage = event.getDamage(EntityDamageEvent.DamageModifier.BASE);

            event.setDamage((damage + mod));


        }

    }

    private void playRageEffect(Location loc, int level) {
        WorldUtil.sendWorldEffect(loc.getBlock(), Material.REDSTONE_BLOCK.getId(), 2001);
    }
}
