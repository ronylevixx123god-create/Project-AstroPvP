// 
// Decompiled by Procyon v0.5.36
// 

package com.compilex.CosmicCrate.libs;

import com.compilex.CosmicCrate.libs.cratedata.July2019;
import com.compilex.CosmicCrate.libs.cratedata.June2019;
import com.compilex.CosmicCrate.libs.cratedata.May2019;
import com.compilex.CosmicCrate.libs.cratedata.April2019;
import com.compilex.CosmicCrate.libs.cratedata.March2019;
import com.compilex.CosmicCrate.libs.cratedata.Febuary2019;
import com.compilex.CosmicCrate.libs.cratedata.January2019;
import com.compilex.CosmicCrate.libs.cratedata.Holiday2018;
import com.compilex.CosmicCrate.libs.cratedata.December2018;
import com.compilex.CosmicCrate.libs.cratedata.Thanksgiving2018;
import com.compilex.CosmicCrate.libs.cratedata.November2018;
import com.compilex.CosmicCrate.libs.cratedata.Halloween2018;
import com.compilex.CosmicCrate.libs.cratedata.October2018;
import com.compilex.CosmicCrate.libs.cratedata.September2018;
import com.compilex.CosmicCrate.libs.cratedata.August2018;
import com.compilex.CosmicCrate.libs.cratedata.July2018;
import com.compilex.CosmicCrate.libs.cratedata.June2018;
import com.compilex.CosmicCrate.libs.cratedata.May2018;
import com.compilex.CosmicCrate.libs.cratedata.April2018;
import com.compilex.CosmicCrate.libs.cratedata.March2018;
import com.compilex.CosmicCrate.libs.cratedata.Valentine2018;
import org.bukkit.Material;
import com.compilex.CosmicCrate.api.CCAPI;
import com.compilex.CosmicCrate.utils.LocalItemUtils;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public enum Version
{
    DECEMBER("&c&l*&a&l*&c&l* &a&lCOSMIC CRATE: &c&l&nDECEMBER 2015&r &a&l*&c&l*&a&l*", CrateCategory.CC_2015), 
    JANURARY("&6&l*&e&l*&6&l* &e&lCOSMIC CRATE: &6&l&nJANUARY 2016&r &6&l*&e&l*&6&l*", CrateCategory.CC_2016), 
    FEBRUARY("&4&l*&c&l*&4&l* &c&lCOSMIC CRATE: &4&l&nFEBRUARY 2016&r &4&l*&c&l*&4&l*", CrateCategory.CC_2016), 
    MARCH("&a&l*&2&l*&a&l* &2&lCOSMIC CRATE: &a&l&nMARCH 2016&r &a&l*&2&l*&a&l*", CrateCategory.CC_2016), 
    APRIL("&e&l*&d&l*&e&l* &d&lCOSMIC CRATE: &e&l&nAPRIL 2016&r &e&l*&d&l*&e&l*", CrateCategory.CC_2016), 
    JULY("&c&l*&f&l*&9&l* &c&lCOSMIC CRATE: &f&l&nJULY &9&l&n2016&r &c&l*&f&l*&9&l*", CrateCategory.CC_2016), 
    AUGUST("&3&l*&b&l*&3&l* &b&lCOSMIC CRATE: &3&l&nAUGUST 2016&r &3&l*&b&l*&3&l*", CrateCategory.CC_2016), 
    SCHOOL("&6&l*&f&l*&6&l* &f&lCOSMIC CRATE: &6&l&nBACK TO SCHOOL&r &6&l*&f&l*&6&l*", CrateCategory.CC_2016), 
    OCTOBER("&c&l*&8&l*&c&l* &8&lCOSMIC CRATE: &c&l&nOCTOBER 2016&r &c&l*&8&l*&c&l*", CrateCategory.CC_2016), 
    HALLOWEEN("&6&l*&4&l*&6&l* &4&lCOSMIC CRATE: &6&l&nHALLOWEEN 2016&r &6&l*&4&l*&6&l*", CrateCategory.CC_2016), 
    NOVEMBER("&5&l*&d&l*&5&l* &d&lCOSMIC CRATE: &5&l&nNOVEMBER 2016&r &5&l*&d&l*&5&l*", CrateCategory.CC_2016), 
    BLACK_FRIDAY("&8&l*&f&l*&8&l* &f&lCOSMIC CRATE: &8&l&nBLACK FRIDAY 2016&r &8&l*&f&l*&8&l*", CrateCategory.CC_2016), 
    DECEMBER_2016("&9&l*&b&l*&9&l* &9&lCOSMIC CRATE: &b&l&nDECEMBER 2016&r &9&l*&b&l*&9&l*", CrateCategory.CC_2016), 
    HOLIDAY("&a&l*&4&l*&a&l* &4&lCOSMIC CRATE: &a&l&nHOLIDAY 2016&r &a&l*&4&l*&a&l*", CrateCategory.CC_2016), 
    JANUARY_2017("&f&l*&3&l*&f&l* &3&lCOSMIC CRATE: &f&l&nJANUARY 2017&r &f&l*&3&l*&f&l*", CrateCategory.CC_2017), 
    VALENTINE("&c&l*&4&l*&c&l* &4&lCOSMIC CRATE: &c&l&nVALENTINE 2017&r &c&l*&4&l*&c&l*", CrateCategory.CC_2017), 
    MARCH_2017("&b&l*&a&l*&b&l* &a&lCOSMIC CRATE: &b&l&nMARCH 2017&r &b&l*&a&l*&b&l*", CrateCategory.CC_2017), 
    APRIL_2017("&5&l*&6&l*&5&l* &6&lCOSMIC CRATE: &5&l&nAPRIL 2017&r &5&l*&6&l*&5&l*", CrateCategory.CC_2017), 
    MAY_2017("&2&l*&a&l*&2&l* &2&lCOSMIC CRATE: &a&l&nMAY 2017&r &2&l*&a&l*&2&l*", CrateCategory.CC_2017), 
    JUNE_2017("&d&l*&c&l*&d&l* &d&lCOSMIC CRATE: &c&l&nJUNE 2017&r &d&l*&c&l*&d&l*", CrateCategory.CC_2017), 
    JULY_2017("&4&l*&f&l*&b&l* &4&lCOSMIC CRATE: &f&l&nJULY &b&l&n2017&r &4&l*&f&l*&b&l*", CrateCategory.CC_2017), 
    AUGUST_2017("&9&l*&b&l*&9&l* &b&lCOSMIC CRATE: &9&l&nAUGUST 2017&r &9&l*&b&l*&9&l*", CrateCategory.CC_2017), 
    SCHOOL_2017("&6&l*&7&l*&6&l* &7&lCOSMIC CRATE: &6&l&nBACK TO SCHOOL... AGAIN!&r &6&l*&7&l*&6&l*", CrateCategory.CC_2017), 
    SEPTEMBER_2017("&9&l*&3&l*&9&l* &3&lCOSMIC CRATE: &9&l&nSEPTEMBER 2017&r &9&l*&3&l*&9&l*", CrateCategory.CC_2017), 
    OCTOBER_2017("&6&l*&8&l*&6&l* &8&lCOSMIC CRATE: &6&l&nOCTOBER 2017&r &6&l*&8&l*&6&l*", CrateCategory.CC_2017), 
    HALLOWEEN_2017("&c&l*&4&l*&c&l* &4&lCOSMIC CRATE: &c&l&nHALLOWEEN 2017&r &c&l*&4&l*&c&l*", CrateCategory.CC_2017), 
    NOVEMBER_2017("&5&l*&f&l*&5&l* &f&lCOSMIC CRATE: &5&l&nNOVEMBER 2017&r &5&l*&f&l*&5&l*", CrateCategory.CC_2017), 
    THANKSGIVING_2017("&e&l*&6&l*&e&l* &6&lCOSMIC CRATE: &e&l&nTHANKSGIVING 2017&r &e&l*&6&l*&e&l*", CrateCategory.CC_2017), 
    DECEMBER_2017("&1&l*&9&l*&1&l* &1&lCOSMIC CRATE: &9&l&nDECEMBER 2017&r &1&l*&9&l*&1&l*", CrateCategory.CC_2017), 
    HOLIDAY_2017("&4&l*&a&l*&4&l* &a&lCOSMIC CRATE: &4&l&nHOLIDAY 2017&r &4&l*&a&l*&4&l*", CrateCategory.CC_2017), 
    JANUARY_2018("&3&l*&b&l*&3&l* &b&lCOSMIC CRATE: &3&l&nJANUARY 2018&r &3&l*&b&l*&3&l*", CrateCategory.CC_2018), 
    FEBUARY_2018("&5&l*&d&l*&5&l* &d&lCOSMIC CRATE: &5&l&nFEBUARY 2018&r &5&l*&d&l*&5&l*", CrateCategory.CC_2018), 
    VALENTINE_2018("&4&l*&c&l*&4&l* &c&lCOSMIC CRATE: &4&l&nVALENTINE 2018&r &4&l*&c&l*&4&l*", (CosmicCrateData)new Valentine2018(), CrateCategory.CC_2018), 
    MARCH_2018("&2&l*&a&l*&2&l* &a&lCOSMIC CRATE: &2&l&nMARCH 2018&r &2&l*&a&l*&2&l*", (CosmicCrateData)new March2018(), CrateCategory.CC_2018), 
    APRIL_2018("&d&l*&e&l*&d&l* &e&lCOSMIC CRATE: &d&l&nAPRIL 2018&r &d&l*&e&l*&d&l*", (CosmicCrateData)new April2018(), CrateCategory.CC_2018), 
    MAY_2018("&a&l*&b&l*&a&l* &a&lCOSMIC CRATE: &b&l&nMAY 2018&r &a&l*&b&l*&a&l*", (CosmicCrateData)new May2018(), CrateCategory.CC_2018), 
    JUNE_2018("&c&l*&4&l*&c&l* &c&lCOSMIC CRATE: &4&l&nJUNE 2018&r &c&l*&4&l*&c&l*", (CosmicCrateData)new June2018(), CrateCategory.CC_2018), 
    JULY_2018("&c&l*&f&l*&9&l* &c&lCOSMIC CRATE: &f&l&nJULY &9&l&n2018&r &c&l*&f&l*&9&l*", (CosmicCrateData)new July2018(), CrateCategory.CC_2018), 
    AUGUST_2018("&c&l*&6&l*&c&l* &c&lCOSMIC CRATE: &6&l&nAUGUST &c&l&n2018&r &c&l*&6&l*&c&l*", (CosmicCrateData)new August2018(), CrateCategory.CC_2018), 
    SEPTEMBER_2018("&b&l*&1&l*&b&l* &b&lCOSMIC CRATE: &1&l&nSEPTEMBER &b&l&n2018&r &b&l*&1&l*&b&l*", (CosmicCrateData)new September2018(), CrateCategory.CC_2018), 
    OCTOBER_2018("&b&l*&6&l*&b&l* &6&lCOSMIC CRATE: &b&l&nOCTOBER 2018&r &b&l*&6&l*&b&l*", (CosmicCrateData)new October2018(), CrateCategory.CC_2018), 
    HALLOWEEN_2018("&6&l*&4&l*&6&l* &4&lCOSMIC CRATE: &6&l&nHALLOWEEN 2018&r &6&l*&4&l*&6&l*", (CosmicCrateData)new Halloween2018(), CrateCategory.CC_2018), 
    NOVEMBER_2018("&d&l*&b&l*&d&l* &b&lCOSMIC CRATE: &d&l&nNOVEMBER 2018&r &d&l*&b&l*&d&l*", (CosmicCrateData)new November2018(), CrateCategory.CC_2018), 
    THANKSGIVING_2018("&6&l*&8&l*&6&l* &8&lCOSMIC CRATE: &6&l&nTHANKSGIVING 2018&r &6&l*&8&l*&6&l*", (CosmicCrateData)new Thanksgiving2018(), CrateCategory.CC_2018), 
    DECEMBER_2018("&b&l*&9&l*&b&l* &b&lCOSMIC CRATE: &9&l&nDECEMBER 2018&r &b&l*&9&l*&b&l*", (CosmicCrateData)new December2018(), CrateCategory.CC_2018), 
    HOLIDAY_2018("&c&l*&6&l*&e&l* &f&lCOSMIC CRATE: &c&l&nH&6&l&nO&e&l&nL&a&l&nI&b&l&nD&d&l&nA&c&l&nY &f&l&n2018&r &a&l*&b&l*&d&l*", (CosmicCrateData)new Holiday2018(), 49, CrateCategory.CC_2018), 
    JANUARY_2019("&3&l*&e&l*&3&l* &e&lCOSMIC CRATE: &3&l&nJANUARY 2019&r &3&l*&e&l*&3&l*", (CosmicCrateData)new January2019(), CrateCategory.CC_2019), 
    FEBRUARY_2019("&b&l*&c&l*&b&l* &c&lCOSMIC CRATE: &b&l&nFEBRUARY 2019&r &b&l*&c&l*&b&l*", (CosmicCrateData)new Febuary2019(), CrateCategory.CC_2019), 
    MARCH_2019("&3&l*&9&l*&3&l* &3&lCOSMIC CRATE: &9&l&nMARCH 2019&r &3&l*&9&l*&3&l*", (CosmicCrateData)new March2019(), CrateCategory.CC_2019), 
    APRIL_2019("&e&l*&5&l*&e&l* &5&lCOSMIC CRATE: &e&l&nAPRIL 2019&r &e&l*&5&l*&e&l*", (CosmicCrateData)new April2019(), CrateCategory.CC_2019), 
    MAY_2019("&a&l*&9&l*&a&l* &9&lCOSMIC CRATE: &a&l&nMAY 2019&r &a&l*&9&l*&a&l*", (CosmicCrateData)new May2019(), CrateCategory.CC_2019), 
    JUNE_2019("&c&l*&6&l*&c&l* &6&lCOSMIC CRATE: &c&l&nJUNE 2019&r &c&l*&6&l*&c&l*", (CosmicCrateData)new June2019(), CrateCategory.CC_2019), 
    JULY_2019("&c&l*&f&l*&9&l* &c&lCOSMIC CRATE: &f&l&nJULY &9&l&n2019&r &c&l*&f&l*&9&l*", (CosmicCrateData)new July2019(), CrateCategory.CC_2019);
    
    public String crateName;
    public String rawName;
    public int guiSlot;
    private CrateCategory category;
    private CosmicCrateData crateData;
    
    private Version(final String crateName, final CrateCategory category) {
        this.guiSlot = -1;
        this.category = category;
        this.crateName = ChatColor.translateAlternateColorCodes('&', crateName);
        this.rawName = this.crateName;
        this.rawName = this.rawName.replace("COSMIC CRATE: ", "").replace("***", "");
    }
    
    private Version(final String crateName, final CosmicCrateData data, final CrateCategory category) {
        this(crateName, data, -1, category);
    }
    
    private Version(final String crateName, final CosmicCrateData data, final int guiSlot, final CrateCategory category) {
        this(crateName, category);
        this.guiSlot = guiSlot;
        this.crateData = data;
    }
    
    public boolean hasPermission(final Player player) {
        return player.hasPermission(this.getPermissionNode());
    }
    
    public String getPermissionNode() {
        return "ccrate.claim." + this.name().toLowerCase();
    }
    
    public ItemStack getDisplayItem(final Player player) {
        if (!player.hasPermission(this.getPermissionNode())) {
            return LocalItemUtils.getLockedPlaceholder(player, this);
        }
        if (CCAPI.hasClaimedCosmicCrate(player, this)) {
            return LocalItemUtils.changeType(CCAPI.getCosmicCrate(this, player.getName()), Material.STAINED_GLASS_PANE, (short)5);
        }
        return CCAPI.getCosmicCrate(this, player.getName());
    }
    
    public String getRawName() {
        return this.rawName;
    }
    
    public CrateCategory getCategory() {
        return this.category;
    }
    
    public CosmicCrateData getCrateData() {
        return this.crateData;
    }
}
