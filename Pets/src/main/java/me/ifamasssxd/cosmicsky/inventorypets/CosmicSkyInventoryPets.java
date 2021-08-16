package me.ifamasssxd.cosmicsky.inventorypets;

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
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class CosmicSkyInventoryPets extends JavaPlugin implements Listener {
   private static CosmicSkyInventoryPets instance;
   private InventoryPetManager petManager;
   private InventoryPetItem petItem;
   private MysteryPetBoxItem mysteryPetBoxItem;
   private RareCandyItem rareCandyItem;
   private PetLeashItem petLeashItem;


   public void onEnable() {
      instance = this;
      (this.petManager = new InventoryPetManager(this)).onEnable(this);

      try {
         this.petItem = (InventoryPetItem)CosmicSkyCustomItems.get().getItemManager().registerPersistentItem("inventory_pet", InventoryPetItem.class);
         this.mysteryPetBoxItem = new MysteryPetBoxItem();
         this.rareCandyItem = new RareCandyItem();
         this.petLeashItem = new PetLeashItem();
         CosmicSkyCustomItems.get().getItemManager().registerCustomItem(this.mysteryPetBoxItem);
         CosmicSkyCustomItems.get().getItemManager().registerCustomItem(this.rareCandyItem);
         CosmicSkyCustomItems.get().getItemManager().registerCustomItem((CosmicItem)this.petLeashItem);
      } catch (InvocationTargetException | InstantiationException | NoSuchMethodException | IllegalAccessException var6) {
         var6.printStackTrace();
      }

      this.registerListeners();
      this.getCommand("pet").setExecutor(new CommandPet());
      InventoryPets[] inventoryPets = InventoryPets.values();
      int length = inventoryPets.length;

      for(int i = 0; i < length; ++i) {
         InventoryPets pet = inventoryPets[i];
         pet.getInternalClass().onEnable(this);
      }

      //this used to be sync
      this.getServer().getScheduler().scheduleAsyncRepeatingTask(this, new BleedTask(), 0L, 10L);
   }

   private void registerListeners() {
      Bukkit.getPluginManager().registerEvents(new AbominalSnowmanListener(), this);
      Bukkit.getPluginManager().registerEvents(new BansheeListener(), this);
      //Bukkit.getPluginManager().registerEvents(new PetCooldownListener(), this);
      Bukkit.getPluginManager().registerEvents(new TitanListener(), this);
      Bukkit.getPluginManager().registerEvents(new BloodhoundListener(), this);
      Bukkit.getPluginManager().registerEvents(new SkrrtListener(), this);
      this.getServer().getPluginManager().registerEvents(new KillTrackerListener(this.petManager), this);
      this.getServer().getPluginManager().registerEvents(new PetLogicListener(this.petManager), this);
      this.getServer().getPluginManager().registerEvents(new PetListener(), this);
      this.getServer().getPluginManager().registerEvents(new ArcaneCreeperListener(), this);
      this.getServer().getPluginManager().registerEvents(new FairyListener(), this);
      this.getServer().getPluginManager().registerEvents(new DemonicListener(), this);

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
