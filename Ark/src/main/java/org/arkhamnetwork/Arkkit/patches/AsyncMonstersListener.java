package org.arkhamnetwork.Arkkit.patches;

import com.gmail.nossr50.mcMMO;
import com.google.common.cache.CacheBuilder;
import com.google.common.collect.Lists;
import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.Faction;
import org.arkhamnetwork.Arkkit.Arkkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

final class AsyncMonstersListener
        implements Listener {
    private final HashMap<Material, Material> condensable = new HashMap<Material, Material>() {
        {
            this.put(Material.IRON_INGOT, Material.IRON_BLOCK);
            this.put(Material.GOLD_INGOT, Material.GOLD_BLOCK);
            this.put(Material.DIAMOND, Material.DIAMOND_BLOCK);
            this.put(Material.EMERALD, Material.EMERALD_BLOCK);
        }
    };
    public CacheBuilder<Object, Object> deadEntitySet = CacheBuilder.newBuilder()
            .expireAfterWrite(25L, TimeUnit.SECONDS)
            .concurrencyLevel(16)
            .weakKeys();
    AsyncMonsters instance;
    private boolean stackIG = true;
    //public Set<Entity> deadEntitySet = Collections.newSetFromMap(CacheBuilder.newBuilder().expireAfterWrite(25L, TimeUnit.SECONDS).concurrencyLevel(16).weakKeys().build());
    private boolean nerfMobSpawners = Arkkit.get().getConfig().getBoolean("settings.async_monsters.nerfWildSpawners");

    AsyncMonstersListener(AsyncMonsters monsters) {
        this.instance = monsters;
        this.stackIG = Arkkit.get().getConfig().getBoolean("settings.async_monsters.stackIG");
        this.nerfMobSpawners = Arkkit.get().getConfig().getBoolean("settings.async_monsters.nerfWildSpawners");
    }

    private boolean isEmpty(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    private void clearEquipment(LivingEntity le) {
        EntityEquipment equipment = le.getEquipment();
        equipment.setHelmet(null);
        equipment.setChestplate(null);
        equipment.setLeggings(null);
        equipment.setBoots(null);
        equipment.setItemInHand(null);
    }

    public boolean shouldCreeperStack(Faction alreadyFound) {
        return alreadyFound == null || !alreadyFound.isNormal() || !this.instance.factionsWithEnemiesInThem.contains(alreadyFound.getId());
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER) {
            Zombie z;
            Ageable a;
            int xz_scan;
            LivingEntity e1 = e.getEntity();
            if (e1.getWorld().getName().equals("dungeon_destroyed_outpost")) {
                return;
            }
            Faction found = Board.getFactionAt(new FLocation(e.getLocation()));
            if (this.nerfMobSpawners && found.isNone() && Math.random() < 0.6) {
                e.setCancelled(true);
                return;
            }
            e1.setMetadata("fromMobSpawner", new FixedMetadataValue(Arkkit.get(), true));
            if (e1.getType() == EntityType.CREEPER && !AsyncMonsters.stackCreepers) {
                return;
            }
            if (e1.getType() == EntityType.IRON_GOLEM && !this.stackIG) {
                return;
            }

            if (e1.getType() == EntityType.VILLAGER) {
                return;
            }
            int y_scan = 128;
            int n = xz_scan = e1.getType() == EntityType.IRON_GOLEM ? 15 : 16;
            if (e1.getType() == EntityType.ENDERMAN || e1.getType() == EntityType.PIG_ZOMBIE || e1.getType() == EntityType.CREEPER) {
                y_scan = 32;
            }
            if (e1 instanceof Ageable && !(a = (Ageable) e1).isAdult()) {
                a.setAdult();
            }
            if (e1 instanceof Zombie && (z = (Zombie) e1).isBaby()) {
                z.setBaby(false);
            }
            if (e1 instanceof LivingEntity) {
                LivingEntity le = e1;
                this.clearEquipment(le);
            }
            CraftEntity ce = (CraftEntity) e1;
            if (ce.getHandle().mobSpawnerMultiplier > 1) {
                AMUtils.setMonsterAmount(e1, AMUtils.getMonsterAmount(e1) * ce.getHandle().mobSpawnerMultiplier);
            }
            for (Entity e2 : e1.getNearbyEntities(xz_scan, y_scan, xz_scan)) {
                ItemStack boot;
                if (e1.getType() != e2.getType() || !e2.hasMetadata("fromMobSpawner") || this.deadEntitySet.build().asMap().containsKey(e2) || (boot = ((LivingEntity) e2).getEquipment().getBoots()) != null && boot.getType() != Material.AIR || e2.getVehicle() != null)
                    continue;
                if ((e1 instanceof Creeper || e1 instanceof Enderman) && e2.isOnGround()) {
                    e2.setLastDamageCause(new EntityDamageEvent(e2, EntityDamageEvent.DamageCause.FALL, 999999));
                    e2.setMetadata("killAura", new FixedMetadataValue(Arkkit.get(), AMUtils.getMonsterAmount(e2)));
                    ((LivingEntity) e2).damage(999999.0);
                    continue;
                }
                int monster2Amount = AMUtils.getMonsterAmount(e2);
                e.setCancelled(true);
                if (monster2Amount > AsyncMonsters.MAX_KILL_STACK / 10 && Math.random() > 0.33 || monster2Amount > AsyncMonsters.MAX_KILL_STACK && Math.random() > 0.1)
                    break;
                AMUtils.setMonsterAmount(e2, monster2Amount + AMUtils.getMonsterAmount(e1));
                break;
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityDeathHIGHEST(EntityDeathEvent e) {
        if (this.deadEntitySet.build().asMap().containsKey(e.getEntity())) {
            return;
        }
        this.deadEntitySet.build().asMap().put(e.getEntity(), e.getEntity());
        if (e.getEntity().getWorld().getName().equals("dungeon_destroyed_outpost")) {
            return;
        }
        int monsterAmount = AMUtils.getMonsterAmount(e.getEntity());
        if (monsterAmount > 10000 || monsterAmount < 0) {
            e.getEntity().remove();
            e.setDroppedExp(0);
            e.getDrops().clear();
            return;
        }
        if (monsterAmount > 1) {
            int monstersToKill = 1;
            if (e.getEntity().getLastDamageCause() != null && (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA || e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FIRE)) {
                monstersToKill = (int) Math.floor((double) monsterAmount * AsyncMonsters.LAVA_KILL_PERCENT);
            }
            if (e.getEntity().getLastDamageCause() != null && e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL) {
                monstersToKill = monsterAmount;
            }
            if (monstersToKill < 1) {
                monstersToKill = 1;
            }
            if (monstersToKill > AsyncMonsters.MAX_KILL_STACK) {
                monstersToKill = AsyncMonsters.MAX_KILL_STACK;
            }
            if (e.getEntity().hasMetadata("killAura")) {
                monstersToKill = e.getEntity().getMetadata("killAura").get(0).asInt();
            }
            if (monstersToKill > monsterAmount) {
                monstersToKill = monsterAmount;
            }
            if (monstersToKill > 1) {
                if (e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.FALL || e.getEntity().getLastDamageCause().getCause() == EntityDamageEvent.DamageCause.LAVA) {
                    e.setDroppedExp(0);
                } else {
                    e.setDroppedExp(e.getDroppedExp() * monstersToKill);
                }
                for (ItemStack i : e.getDrops()) {
                    int toGive = 0;
                    int toMultiply = 0;
                    for (int x = monstersToKill; x > 0; --x) {
                        if (Math.random() > 0.33) continue;
                        ++toMultiply;
                    }
                    if (toMultiply <= 0) continue;
                    if (i.getAmount() + toGive > i.getMaxStackSize()) {
                        ItemStack item;
                        for (toGive = i.getAmount() * toMultiply; toGive >= i.getMaxStackSize(); toGive -= i.getMaxStackSize()) {
                            item = i.clone();
                            item.setAmount(i.getMaxStackSize());
                            e.getDrops().add(item);
                        }
                        if (toGive <= 0) continue;
                        if (i.getAmount() + toGive <= i.getMaxStackSize()) {
                            i.setAmount(i.getAmount() + toGive);
                            continue;
                        }
                        item = i.clone();
                        item.setAmount(toGive);
                        e.getDrops().add(item);
                        continue;
                    }
                    i.setAmount(i.getAmount() + toGive);
                }
            }
            if (monstersToKill < monsterAmount && e.getEntityType() != EntityType.CHICKEN) {
                Zombie z;
                Ageable a;
                int monstersToAdd = 0;
                for (Entity ent : e.getEntity().getLocation().getChunk().getEntities()) {
                    LivingEntity le;
                    if (!(ent instanceof LivingEntity) || (le = (LivingEntity) ent).getEntityId() == e.getEntity().getEntityId() || le.getType() != e.getEntityType() || !(le.getHealth() > 0.0) || !le.hasMetadata("fromMobSpawner"))
                        continue;
                    if (ent instanceof Creeper && ent.isOnGround()) {
                        ent.setLastDamageCause(new EntityDamageEvent(ent, EntityDamageEvent.DamageCause.FALL, 999999));
                        ent.setMetadata("killAura", new FixedMetadataValue(Arkkit.get(), AMUtils.getMonsterAmount(ent)));
                        ((LivingEntity) ent).damage(999999.0);
                        continue;
                    }
                    monstersToAdd += AMUtils.getMonsterAmount(ent);
                    ent.remove();
                }
                Entity e2 = e.getEntity().getWorld().spawnEntity(e.getEntity().getLocation(), e.getEntityType());
                AMUtils.setMonsterAmount(e2, monsterAmount - monstersToKill + monstersToAdd);
                ((CraftEntity) e2).getHandle().fromMobSpawner = true;
                e2.setMetadata("fromMobSpawner", new FixedMetadataValue(Arkkit.get(), true));
                e2.setMetadata("mcMMO: Spawned Entity", mcMMO.metadataValue);
                if (e2 instanceof Ageable && !(a = (Ageable) e2).isAdult()) {
                    a.setAdult();
                }
                if (e2 instanceof Zombie && (z = (Zombie) e2).isBaby()) {
                    z.setBaby(false);
                }
                if (e2 instanceof LivingEntity) {
                    LivingEntity le = (LivingEntity) e2;
                    this.clearEquipment(le);
                }
                if (e.getEntity().getFireTicks() > 0) {
                    e2.setFireTicks(400);
                }
                AMUtils.setMonsterAmount(e.getEntity(), 1);
            }
            if (e.getEntity() instanceof IronGolem || e.getEntity() instanceof MushroomCow || e.getEntity() instanceof MagmaCube || e.getEntity() instanceof Ghast) {
                this.convertDrops(e.getDrops());
            }
        }
        if (e.getEntity() instanceof MushroomCow) {
            for (ItemStack drop : e.getDrops()) {
                Material type = drop.getType();
                if (type != Material.RED_MUSHROOM && type != Material.BROWN_MUSHROOM) continue;
                e.getDrops().remove(drop);
            }
        }
    }

    public void convertDrops(List<ItemStack> drops) {
        if (drops.size() == 0) {
            return;
        }
        int condensableCount = 0;
        boolean condensable = false;
        Material condensableMaterial = null;
        for (ItemStack drop : Lists.newArrayList(drops)) {
            if (!this.condensable.containsKey(drop.getType())) continue;
            condensableMaterial = drop.getType();
            condensableCount += drop.getAmount();
            drops.remove(drop);
            condensable = true;
        }
        if (!condensable) {
            return;
        }
        if (condensableCount < 9) {
            drops.add(new ItemStack(condensableMaterial, condensableCount));
        } else {
            boolean giveExtra;
            int condenseToGive = condensableCount / 9;
            int remainder = condensableCount % 9;
            boolean bl = giveExtra = condenseToGive > 0 && remainder > 0 && Math.random() <= (double) remainder * 0.11;
            if (giveExtra) {
                ++condenseToGive;
            }
            int stacks = condenseToGive / 64;
            int leftOverBlocks = condenseToGive % 64;
            for (int i = 0; i < stacks; ++i) {
                drops.add(new ItemStack(this.condensable.get(condensableMaterial), 64));
            }
            if (leftOverBlocks > 0) {
                drops.add(new ItemStack(this.condensable.get(condensableMaterial), leftOverBlocks));
            }
        }
    }
}