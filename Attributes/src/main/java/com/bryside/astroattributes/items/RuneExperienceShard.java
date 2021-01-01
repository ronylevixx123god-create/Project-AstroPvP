package com.bryside.astroattributes.items;

import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RuneExperienceShard {
   public static Material material;
   public static String displayName;

   public static ItemStack createNewExperienceShard(int experience) {
      ItemStack item = new ItemStack(material);
      ItemMeta itemMeta = item.getItemMeta();
      List lore = Arrays.asList("§7Apply this Experience orb to", "§7a heroic sword with a titan attribute", "§7to increase the amount of exp.", "", CC.Green + "Value: " + CC.White + experience + " XP");
      itemMeta.setLore(lore);
      itemMeta.setDisplayName(displayName);
      item.setItemMeta(itemMeta);
      return item;
   }

   public static boolean isRuneExperienceShard(ItemStack item) {
      if (item != null && item.hasItemMeta() && item.getItemMeta().hasLore() && item.getItemMeta().hasDisplayName() && item.getType() == material) {
         ItemMeta itemMeta = item.getItemMeta();
         String display = itemMeta.getDisplayName();
         return display.contentEquals(displayName);
      } else {
         return false;
      }
   }

   public static int getExperienceOnShard(ItemStack item) {
      if (!isRuneExperienceShard(item)) {
         return -1;
      } else {
         ItemMeta itemMeta = item.getItemMeta();
         List lore = itemMeta.getLore();
         Iterator var3 = lore.iterator();

         String l;
         do {
            if (!var3.hasNext()) {
               return -1;
            }

            l = (String)var3.next();
         } while(!l.startsWith(CC.Green + "Value: "));

         l = l.substring(l.indexOf(" ") + 3, l.indexOf("XP") - 1);
         int exp = Integer.valueOf(l);
         return exp;
      }
   }

   public static Material getMaterial() {
      return material;
   }

   static {
      material = Material.MAGMA_CREAM;
      displayName = CC.GreenB + "Attribute Experience " + CC.Gray + "(Drag n' Drop)";
   }
}
