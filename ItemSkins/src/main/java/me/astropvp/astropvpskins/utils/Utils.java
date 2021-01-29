/*    */ package me.astropvp.astropvpskins.utils;
/*    */ import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.astropvp.astropvpskins.enums.SkinType;
/*    */ import net.minecraft.server.v1_8_R3.NBTBase;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class Utils {
/*    */   public static boolean isInventoryFull(Player player) {
/*    */     byte b;
/*    */     int i;
/*    */     ItemStack[] arrayOfItemStack;
/* 18 */     for (i = (arrayOfItemStack = player.getInventory().getContents()).length, b = 0; b < i; ) { ItemStack x = arrayOfItemStack[b];
/* 19 */       if (x == null)
/* 20 */         return false; 
/*    */       b++; }
/*    */     
/* 23 */     return true;
/*    */   }
/*    */   
/*    */   public static ItemStack addSkinNBT(String line, ItemStack item) {
/* 27 */     ItemStack finalItem = item;
/* 28 */     NBTWrapper wrapper = new NBTWrapper(item);
/* 29 */     NBTTagCompoundBuilder compoundBuilder = wrapper.hasKey("cosmicData") ?
/* 30 */       new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData")) :
/* 31 */       new NBTTagCompoundBuilder();
/* 32 */     compoundBuilder.setString("itemSkinType", line);
/* 33 */     finalItem = wrapper.setNBTBase("cosmicData", (NBTBase)compoundBuilder.get()).build();
/* 34 */     return finalItem;
/*    */   }
/*    */   
/*    */   public static ItemStack addSkinLore(SkinType type, ItemStack item) {
/* 38 */     ItemMeta meta = item.getItemMeta();
/* 39 */     List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
/* 40 */     lore.add(type.getAppliedLore());
/* 41 */     meta.setLore(lore);
/* 42 */     item.setItemMeta(meta);
/* 43 */     return item;
/*    */   }
/*    */   
/*    */   public static ItemStack removeSkinNBT(String line, ItemStack item) {
/* 47 */     ItemStack finalItem = item;
/* 48 */     NBTWrapper wrapper = new NBTWrapper(item);
/* 49 */     if (wrapper.hasKey("cosmicData")) {
/* 50 */       NBTTagCompoundBuilder builder = new NBTTagCompoundBuilder(wrapper.getNBTBase("cosmicData"));
/* 51 */       builder.remove("itemSkinType");
/* 52 */       finalItem = wrapper.setNBTBase("cosmicData", (NBTBase)builder.get()).build();
/*    */     } 
/* 54 */     return finalItem;
/*    */   }
/*    */   
/*    */   public static ItemStack removeSkinLore(ItemStack item) {
/* 58 */     ItemMeta meta = item.getItemMeta();
/* 59 */     List<String> lore = meta.hasLore() ? meta.getLore() : new ArrayList<>();
/* 60 */     for (int i = lore.size() - 1; i >= 0; i--) {
/* 61 */       String l = lore.get(i);
/* 62 */       if (l.startsWith(String.valueOf(CC.WhiteB) + "ITEM SKIN:")) {
/* 63 */         lore.remove(i);
/*    */       }
/*    */     } 
/* 66 */     meta.setLore(lore);
/* 67 */     item.setItemMeta(meta);
/* 68 */     return item;
/*    */   }
/*    */ }


/* Location:              C:\Users\bryan\Downloads\GalacticItemSkins.jar!\me\galacticpvp\galacticitemskin\\utils\Utils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */