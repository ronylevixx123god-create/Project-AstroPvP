package me.ifamasssxd.cosmicbosses.bosses.intergalacticenforcer;

import me.ifamasssxd.cosmicbosses.bosses.*;
import com.cosmicpvp.CosmicSpaceChests.libs.*;
import com.cosmicpvp.CosmicSpaceChests.*;
import me.ifamasssxd.cosmicbosses.struct.enums.Difficulty;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.*;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.inventory.*;
import org.bukkit.enchantments.*;
import me.ifamasssxd.cosmicbosses.struct.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import org.bukkit.entity.*;
import java.util.concurrent.*;
import me.ifamasssxd.cosmicbosses.utils.*;
import org.bukkit.*;
import me.ifamasssxd.cosmicbosses.struct.enums.*;
import java.util.*;
import com.google.common.collect.*;

public class Exterminator extends Boss implements GuardBoss
{
    private int outsideTicks;
    
    public Exterminator(final Location location, final BossType bossType) {
        super(location, bossType);
        this.outsideTicks = 0;
        this.getLootPool().add(CosmicSpaceChestsAPI.getDropPackageChest(ItemRarity.ULTIMATE));
    }
    
    @Override
    public EntityType getEntityType() {
        return EntityType.PIG_ZOMBIE;
    }
    
    @Override
    public String getCustomName() {
        return ChatColor.LIGHT_PURPLE.toString() + ChatColor.BOLD + "Cosmic Exterminator";
    }
    
    @Override
    public LivingEntity setupBoss(final LivingEntity e) {
        final LivingEntity entity = super.setupBoss(e);
        final EntityCreature creature = ((CraftCreature)entity).getHandle();
        creature.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(16.0);
        //creature.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(1.5);
        creature.getAttributeInstance(GenericAttributes.c).setValue(0.75);
        creature.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(2.0);
        final PigZombie zombie = (PigZombie)entity;
        zombie.setBaby(true);
        zombie.setAngry(true);
        zombie.setAnger(Integer.MAX_VALUE);
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        return entity;
    }
    
    @Override
    public void onBossSpawn() {
        final PigZombie zombie = (PigZombie)this.spawnBoss();
        zombie.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 2));
        this.setHelmet(new CustomItem(new ItemStack(Material.DIAMOND_HELMET)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.PROTECTION_FIRE, 5).build());
        this.setChestplate(new CustomItem(new ItemStack(Material.DIAMOND_CHESTPLATE)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).build());
        this.setLeggings(new CustomItem(new ItemStack(Material.DIAMOND_LEGGINGS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1).addEnchant(Enchantment.THORNS, 1).build());
        this.setBoots(new CustomItem(new ItemStack(Material.DIAMOND_BOOTS)).setUnbreakable(true).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        zombie.getEquipment().setItemInHand(new CustomItem(new ItemStack(Material.DIAMOND_SWORD)).setUnbreakable(true).addEnchant(Enchantment.KNOCKBACK, 3).addEnchant(Enchantment.DAMAGE_ALL, 5).build());
        zombie.getEquipment().setItemInHandDropChance(0.0f);
        final BossMessage start = this.getStartingMessage();
        if (start != null) {
            this.sendMessage(ChatColor.ITALIC + start.getMessage());
        }
    }
    
    @Override
    public String getName() {
        return "Cosmic Exterminator";
    }
    
    @Override
    public void cleanup() {
    }
    
    @Override
    public String getTitle() {
        return null;
    }
    
    @Override
    public void onUpdate() {
        ++this.loops;
        if (this.loops % 10 == 0) {
            this.getPigZombie().setAngry(true);
            this.getPigZombie().setAnger(Integer.MAX_VALUE);
            if (this.getAntiFarm().checkStuck()) {
                this.getAntiFarm().unstickBoss();
            }
        }
        if (this.loops % 4 == 0 && this.getAntiFarm().checkStuck()) {
            this.getAntiFarm().unstickBoss();
        }
        this.checkGuardLocation(this, this.location);
        this.outsideTicks = this.checkOutsideTicks(this, this.outsideTicks, this.location);
        super.onUpdate();
    }
    
    @Override
    public void onPhaseChange(final Phase newPhase) {
    }
    
    public PigZombie getPigZombie() {
        return (PigZombie)this.getEntity();
    }
    
    @Override
    public void onMiscUpdate(final Event event) {
        if (event instanceof EntityTargetEvent) {
            final EntityTargetEvent e = (EntityTargetEvent)event;
            if (e.getReason() == EntityTargetEvent.TargetReason.PIG_ZOMBIE_TARGET) {
                e.setCancelled(true);
            }
        }
    }
    
    @Override
    public void onAttackPlayer(final EntityDamageByEntityEvent event, final Player player) {
        int percentDamage = ThreadLocalRandom.current().nextInt(10) + 5;
        if (this.random.nextInt(100) < 40) {
            percentDamage += 5;
        }
        BossUtils.dealPercentMaxHealthDamage(player, percentDamage, 25, false);
        player.playSound(player.getLocation(), Sound.ZOMBIE_WOODBREAK, 3.0f, 1.3f);
        if (player.isSprinting() && this.random.nextInt(100) < 20) {
            player.setSprinting(false);
        }
        super.onAttackPlayer(event, player);
    }
    
    @Override
    public DamageType getDamageType() {
        return DamageType.POISON;
    }
    
    @Override
    public Difficulty getDifficulty() {
        return Difficulty.LEGENDARY;
    }
    
    @Override
    public int getMaxHealth() {
        return 1000;
    }
    
    @Override
    public List<BossMessage> getGeneralMessages() {
        return (List<BossMessage>)Lists.newArrayList(new BossMessage[] { new BossMessage("Time to exterminate!", false, -1) });
    }
}
