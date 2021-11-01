package me.ifamasssxd.cosmicsky.inventorypets.items;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.ItemUtils;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
import com.cosmicpvp.cosmicutils.utils.RandomFireworks;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPets;
import me.ifamasssxd.cosmicskycustoms.exceptions.ItemDupedException;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
import me.ifamasssxd.cosmicskycustoms.struct.PlayerInteractable;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class MysteryPetBoxItem extends CosmicItem implements PlayerInteractable {
   public List common_inventoryPets;
   public List rare_inventoryPets;

   public MysteryPetBoxItem() {
      super("mystery_pet_box");
      this.common_inventoryPets = Lists.newArrayList(new String[]{InventoryPets.LAVA.name(), InventoryPets.WATER.name(), InventoryPets.XP_BOOSTER.name(), InventoryPets.ANTI_TELEBLOCK.name(), InventoryPets.BANNER.name()});
      this.rare_inventoryPets = Lists.newArrayList(new String[]{InventoryPets.ALCHEMIST.name(), InventoryPets.FEIGN_DEATH.name(), InventoryPets.ENCHANTER.name(), InventoryPets.GAIA.name(), InventoryPets.BLACKSCROLL.name(), InventoryPets.TESLA.name(), InventoryPets.EVOLUTION.name()});
   }

   public ItemStack createItem(Player player, Object... objects) {
      String displayName = CC.GoldB + "Mystery Pet Box " + CC.Gray + "(Right Click)";
      List lore = Lists.newArrayList(new String[]{CC.Gray + "A mysterious, magical box", CC.Gray + "that contains one of the", CC.Gray + "following me.astropvp.cratekeys.inventory pets:"});
      InventoryPets[] var5 = InventoryPets.values();
      InventoryPets[] array = var5;
      int length = var5.length;

      for(int i = 0; i < length; ++i) {
         InventoryPets pet = array[i];
         lore.add(CC.GoldB + " * " + CC.Gray + pet.getItemName());
      }

      return this.applyItemData((new ItemBuilder(Material.ENDER_CHEST, displayName, new String[0])).lore(lore).build()).assignIID().build();
   }

   public void onPlayerInteractable(Player player, ItemStack itemStack, NBTWrapper nbtWrapper, PlayerInteractEvent playerInteractEvent) throws CosmicException {
      if (playerInteractEvent.getAction() == Action.RIGHT_CLICK_BLOCK || playerInteractEvent.getAction() == Action.RIGHT_CLICK_AIR) {
         playerInteractEvent.setCancelled(true);
         playerInteractEvent.setUseItemInHand(Result.DENY);
         playerInteractEvent.setUseInteractedBlock(Result.DENY);
         if (nbtWrapper.isBlacklistedIID()) {
            throw new ItemDupedException("Mystery Pet Box already claimed!");
         }

         if (player.getWorld().getName().contains("duels")) {
            throw new PrefixedException("You cannot use this item in this world!");
         }

         nbtWrapper.blacklistIID("mystery pet box claimed " + player.getName());
         if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
         } else {
            player.setItemInHand((ItemStack)null);
         }

         InventoryPets pet = InventoryPets.valueOf(Math.random() < 0.2D ? (String)this.rare_inventoryPets.get(ThreadLocalRandom.current().nextInt(this.rare_inventoryPets.size())) : (String)this.common_inventoryPets.get(ThreadLocalRandom.current().nextInt(this.common_inventoryPets.size())));
         ItemStack is = (new CustomItemBuilder("inventory_pet", new Object[]{pet.name()})).build();
         PlayerUtil.giveOrDropProtectedItem(player, is, -1);
         player.sendMessage(CC.GoldB + "(!) " + CC.Gold + "You have received a " + ItemUtils.getDisplayName(is) + CC.Gold + " from your Mystery Pet Box!");
         player.updateInventory();
         ASound.LEVEL_UP.play(player, 2.0F, 3.0F);
         RandomFireworks.getManager().launchRandomFirework(player.getLocation());
      }

   }
}
