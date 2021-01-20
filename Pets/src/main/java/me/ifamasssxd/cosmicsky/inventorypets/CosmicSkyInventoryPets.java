package me.ifamasssxd.cosmicsky.inventorypets;

import lombok.SneakyThrows;
import me.ifamasssxd.cosmicsky.inventorypets.commands.CommandPet;
import me.ifamasssxd.cosmicsky.inventorypets.items.MysteryPetBoxItem;
import me.ifamasssxd.cosmicsky.inventorypets.items.PetLeashItem;
import me.ifamasssxd.cosmicsky.inventorypets.items.RareCandyItem;
import me.ifamasssxd.cosmicsky.inventorypets.listeners.*;
import me.ifamasssxd.cosmicsky.inventorypets.managers.BlockModifier;
import me.ifamasssxd.cosmicsky.inventorypets.managers.InventoryPetManager;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicsky.inventorypets.struct.tasks.BleedTask;
import me.ifamasssxd.cosmicskycustoms.CosmicSkyCustomItems;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class CosmicSkyInventoryPets extends JavaPlugin implements Listener {
   private static CosmicSkyInventoryPets instance;
   private InventoryPetManager petManager;
   private InventoryPetItem petItem;
   private MysteryPetBoxItem mysteryPetBoxItem;
   private RareCandyItem rareCandyItem;
   private PetLeashItem petLeashItem;


   @SneakyThrows
   public void onEnable() {
      instance = this;
      (this.petManager = new InventoryPetManager(this)).onEnable(this);
      try {
         this.petItem = (InventoryPetItem)CosmicSkyCustomItems.get().getItemManager().registerPersistentItem("inventory_pet", (Class)InventoryPetItem.class);
         this.mysteryPetBoxItem = new MysteryPetBoxItem();
         this.rareCandyItem = new RareCandyItem();
         this.petLeashItem = new PetLeashItem();
         CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)this.mysteryPetBoxItem);
         CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)this.rareCandyItem);
         CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)this.petLeashItem);
      }
      catch (InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException e) {
         e.printStackTrace();
      }
      this.registerListeners();
      this.getCommand("pet").setExecutor((CommandExecutor)new CommandPet());
      for (final InventoryPets pet : InventoryPets.values()) {
         pet.getInternalClass().onEnable(this);
      }
      this.getServer().getScheduler().scheduleSyncRepeatingTask((Plugin)this, (Runnable)new BleedTask(), 0L, 10L);
   }

   private void registerListeners() {
      Bukkit.getPluginManager().registerEvents(new AbominalSnowmanListener(), this);
      Bukkit.getPluginManager().registerEvents(new BansheeListener(), this);
      Bukkit.getPluginManager().registerEvents(new TitanListener(), this);
      Bukkit.getPluginManager().registerEvents(new BloodhoundListener(), this);
      Bukkit.getPluginManager().registerEvents(new SkrrtListener(), this);
      this.getServer().getPluginManager().registerEvents(new KillTrackerListener(this.petManager), this);
      this.getServer().getPluginManager().registerEvents(new PetLogicListener(this.petManager), this);
      this.getServer().getPluginManager().registerEvents(new PetListener(), this);
      this.getServer().getPluginManager().registerEvents(new ArcaneCreeperListener(), this);
      Bukkit.getPluginManager().registerEvents(new PetLeashListener(), this);

      try {
         Class.forName("org.arkhamnetwork.Arkkit.patches.combatlog.CombatLoggerDeathEvent");
      } catch (ClassNotFoundException var2) {
         Bukkit.getLogger().info("Unable to find CombatLoggerDeathEvent");
      }

   }

   public void onDisable() {
      BlockModifier.removeAllBlocks(true);
   }

   public static CosmicSkyInventoryPets get() {
      return instance;
   }

   public InventoryPetManager getPetManager() {
      return this.petManager;
   }

   public InventoryPetItem getPetItem() {
      return this.petItem;
   }

   public MysteryPetBoxItem getMysteryPetBoxItem() {
      return this.mysteryPetBoxItem;
   }

   public RareCandyItem getRareCandyItem() {
      return this.rareCandyItem;
   }

}
