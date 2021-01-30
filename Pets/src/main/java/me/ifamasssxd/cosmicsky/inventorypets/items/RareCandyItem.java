package me.ifamasssxd.cosmicsky.inventorypets.items;

import com.cosmicpvp.cosmicutils.compat.ASound;
import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.utils.CC;
import com.cosmicpvp.cosmicutils.utils.ItemBuilder;
import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import com.cosmicpvp.cosmicutils.utils.TimeUtils;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.concurrent.TimeUnit;
import me.ifamasssxd.cosmicsky.inventorypets.struct.InventoryPetItem;
import me.ifamasssxd.cosmicskycustoms.exceptions.ItemDupedException;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.CosmicPersistentItem;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class RareCandyItem extends CosmicItem implements InventoryInteractable {
   public RareCandyItem() {
      super("rare_candy");
   }

   public ItemStack createItem(Player player, Object... objects) {
      String displayName = CC.RedB + "Rare Candy";
      List lore = Lists.newArrayList(new String[]{CC.Gray + "Magical candy imbued with", CC.Gray + "powerful growth hormones", CC.Gray + "for inventory pets.", "", CC.RedB + "Ability", CC.Gray + "Apply to any inventory pet to instantly", CC.Gray + "increase its level by +1 and trigger its", CC.Gray + "ability cooldown."});
      return this.applyItemData((new ItemBuilder(Material.RED_MUSHROOM, displayName, new String[0])).lore(lore).build()).assignIID().build();
   }

   public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
      return inventoryItemType == InventoryItemType.CURSOR;
   }

   public void onInventoryClick(Player player, ItemStack itemStack, NBTWrapper nbtWrapper, NBTWrapper clickedWrapper, ItemStack clicked, InventoryClickEvent inventoryClickEvent) throws CosmicException {
      InventoryPetItem petItem = (InventoryPetItem)CosmicPersistentItem.constructItem(clicked, clickedWrapper, InventoryPetItem.class);
      if (petItem != null && petItem.getPetType() != null) {
         if (petItem.getLevel() >= petItem.getPetType().getInternalClass().getMaxLevel()) {
            throw new PrefixedException("You cannot use Rare Candy on a max level pet!");
         }


         if (nbtWrapper.isBlacklistedIID()) {
            throw new ItemDupedException("Rare Candy already used!");
         }

         inventoryClickEvent.setCancelled(true);
         if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
         } else {
            player.setItemOnCursor((ItemStack)null);
         }

         nbtWrapper.blacklistIID("rare candy used on " + petItem + ": " + player.getName());
         petItem.setLastUsed(System.currentTimeMillis());
         petItem.setLastRareCandyUsed(System.currentTimeMillis());
         petItem.setLevel(petItem.getLevel() + 1);
         inventoryClickEvent.setCurrentItem(petItem.build());
         ASound.LEVEL_UP.play(player, 3.0F, 1.1F);
         player.sendMessage(CC.YellowB + "(!) " + petItem.getPetType().getItemName() + ": " + CC.RedB + "+1 LEVEL! " + CC.Gray + "(Rare Candy)");
         player.updateInventory();
      }

   }
}
