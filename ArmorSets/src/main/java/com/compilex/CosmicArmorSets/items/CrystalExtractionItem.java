/*    */ package com.compilex.CosmicArmorSets.items;
/*    */ import com.compilex.CosmicArmorSets.enums.AbilityArmorSet;
/*    */ import com.compilex.CosmicArmorSets.enums.ArmorSet;
/*    */ import com.cosmicpvp.cosmicutils.compat.ASound;
/*    */ import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
/*    */ import com.cosmicpvp.cosmicutils.exceptions.PrefixedException;
import com.cosmicpvp.cosmicutils.itembuilder.CosmicItemBuilder;
import com.cosmicpvp.cosmicutils.nbt.NBTTagCompoundBuilder;
/*    */ import com.cosmicpvp.cosmicutils.utils.CC;
/*    */ import com.cosmicpvp.cosmicutils.utils.GeneralUtil;
/*    */ import com.cosmicpvp.cosmicutils.utils.ItemUtils;
/*    */ import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
/*    */ import com.cosmicpvp.cosmicutils.utils.NumberUtils;
/*    */ import com.cosmicpvp.cosmicutils.utils.PlayerUtil;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import me.ifamasssxd.cosmicskycustoms.struct.CosmicItem;
import me.ifamasssxd.cosmicskycustoms.struct.CustomItemBuilder;
/*    */ import me.ifamasssxd.cosmicskycustoms.struct.InventoryInteractable;
/*    */ import me.ifamasssxd.cosmicskycustoms.struct.InventoryItemType;
/*    */ import net.minecraft.server.v1_7_R4.NBTBase;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CrystalExtractionItem extends CosmicItem implements InventoryInteractable {
/*    */   public CrystalExtractionItem() {
/* 26 */     super("extractCrystal");
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack createItem(Player player, Object... objects) {
/* 31 */     Integer successRate = (objects != null && objects.length >= 1) ? NumberUtils.parseIntOrNull(objects[0].toString()) : null;
/* 32 */     if (successRate == null) successRate = Integer.valueOf(1);
/*    */     
/* 34 */     return applyItemData((new CosmicItemBuilder(Material.GHAST_TEAR, CC.GoldB + "Crystal Extractor", new String[] { CC.Gray + "Removes 1 random crystal from", CC.Gray + "an armor piece and converts it", CC.Gray + "into its applicable form:", CC.WhiteB + " * " + CC.GoldB + "Anti M-Kit Crystal (" + CC.White + successRate + "% Success" + CC.GoldB + ")", CC.WhiteB + " " + CC.WhiteB + CC.Underline + "OR", CC.WhiteB + " * " + CC.GoldB + "Armor Crystal (" + CC.White + successRate + "% Success" + CC.GoldB + ")"
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */           
/* 40 */           })).build())
/* 41 */       .setNBTBase("cosmicData", (NBTBase)(new NBTTagCompoundBuilder()).setInt("success", successRate).get()).build();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean acceptsInventoryClickType(InventoryItemType inventoryItemType) {
/* 46 */     return (inventoryItemType == InventoryItemType.CURSOR);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInventoryClick(Player player, ItemStack itemStack, NBTWrapper crystal, NBTWrapper clicked, ItemStack other, InventoryClickEvent e) throws CosmicException {
/* 51 */     if (!GeneralUtil.isArmor(other))
/* 52 */       return;  List<ArmorSet> armorCrystalApplied = ArmorSet.getFromArmorBonusCrystal(clicked);
/* 53 */     NBTTagCompoundBuilder data = crystal.getCompound("cosmicData");
/* 54 */     if (data == null)
/* 55 */       return;  Integer crystalExtractionSuccess = data.getInt("success");
/*    */     
/* 57 */     Set<AbilityArmorSet> antiMkitCrystalApplied = AbilityArmorSet.getAntiMkitCrystal(clicked);
/* 58 */     if (armorCrystalApplied == null && (antiMkitCrystalApplied == null || antiMkitCrystalApplied.isEmpty())) {
/* 59 */       throw new PrefixedException("No armor crystals to extract from this armor piece!");
/*    */     }
/* 61 */     ItemStack updated = null;
/* 62 */     ItemStack returnCursor = null;
/* 63 */     if (armorCrystalApplied != null && !armorCrystalApplied.isEmpty() && (antiMkitCrystalApplied == null || Math.random() <= 0.5D)) {
/* 64 */       updated = ArmorSet.removeCrystalFromItem(clicked);
/* 65 */       returnCursor = ArmorSet.createCrystal(armorCrystalApplied, crystalExtractionSuccess.intValue());
/*    */     } else {
/*    */       
/* 68 */       updated = AbilityArmorSet.removeAntiMkitCrystalFromItem(clicked);
/* 69 */       returnCursor = (new CustomItemBuilder("ANTI_MKIT", new Object[] { crystalExtractionSuccess, antiMkitCrystalApplied })).build();
/*    */     } 
/*    */     
/* 72 */     if (updated != null) {
/* 73 */       e.setCancelled(true);
/* 74 */       e.setCurrentItem(updated);
/* 75 */       if (itemStack.getAmount() > 1) {
/* 76 */         itemStack.setAmount(itemStack.getAmount() - 1);
/* 77 */         player.setItemOnCursor(itemStack);
/* 78 */         PlayerUtil.giveOrDropProtectedItem(player, returnCursor, -1);
/*    */       } else {
/* 80 */         player.setItemOnCursor(returnCursor);
/*    */       } 
/* 82 */       player.sendMessage(CC.GreenB + "(!) " + ItemUtils.getDisplayName(returnCursor) + CC.Green + " extracted from " + ItemUtils.getDisplayName(updated) + CC.Green + "!");
/* 83 */       ASound.LEVEL_UP.play(player, 10.0F, 1.1F);
/* 84 */       player.updateInventory();
/* 85 */       ASound.WITHER_IDLE.play(player, 1.0F, 3.0F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\tucke\Desktop\CosmicArmorSets.jar!\com\compilex\CosmicArmorSets\items\CrystalExtractionItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */