package com.drtshock.playervaults.translations;

import org.bukkit.configuration.file.*;
import org.bukkit.*;

public enum Lang
{
    TITLE("title-name", "&4[&fPlayerVaults&4]:"), 
    OPEN_VAULT("open-vault", "&fOpening vault &a%v"), 
    OPEN_OTHER_VAULT("open-other-vault", "&fOpening vault &a%v &fof &a%p"), 
    INVALID_ARGS("invalid-args", "&cInvalid args!"), 
    DELETE_VAULT("delete-vault", "&fDeleted vault &a%v"), 
    DELETE_OTHER_VAULT("delete-other-vault", "&fDeleted vault &a%v &fof &a%p"), 
    PLAYER_ONLY("player-only", "&cSorry but that can only be run by a player!"), 
    MUST_BE_NUMBER("must-be-number", "&cYou need to specify a valid number."), 
    DELETE_VAULT_ERROR("delete-vault-error", "&cError deleting vault :("), 
    NO_PERMS("no-permissions", "&cYou don''t have permission for that!"), 
    INSUFFICIENT_FUNDS("insufficient-funds", "&cYou don''t have enough money for that!"), 
    REFUND_AMOUNT("refund-amount", "&fYou were refunded &a%price &ffor deleting that vault."), 
    COST_TO_CREATE("cost-to-create", "&fYou were charged &c%price &ffor creating a vault."), 
    COST_TO_OPEN("cost-to-open", "&fYou were charged &c%price &ffor opening that vault."), 
    VAULT_DOES_NOT_EXIST("vault-does-not-exist", "&cThat vault does not exist!"), 
    CLICK_A_SIGN("click-a-sign", "&fNow click a sign!"), 
    NOT_A_SIGN("not-a-sign", "&cYou must click a sign!"), 
    SET_SIGN("set-sign-success", "&fYou have successfully set a PlayerVault access sign!"), 
    EXISTING_VAULTS("existing-vaults", "&f%p has vaults: &a%v"), 
    VAULT_TITLE("vault-title", "&4Vault #%number"), 
    OPEN_WITH_SIGN("open-with-sign", "&fOpening vault &a%v &fof &a%p"), 
    NO_PLAYER_FOUND("no-player-found", "&cCannot find player &a%p"), 
    CONVERT_PLUGIN_NOT_FOUND("plugin-not-found", "&cNo converter found for that plugin"), 
    CONVERT_COMPLETE("conversion-complete", "&aConverted %converted players to PlayerVaults"), 
    CONVERT_BACKGROUND("conversion-background", "&fConversion has been forked to the background. See console for updates."), 
    LOCKED("vaults-locked", "&cVaults are currently locked while conversion occurs. Please try again in a moment!"), 
    HELP("help", "/pv <number>"), 
    BLOCKED_ITEM("blocked-item", "&6%m &cis blocked from vaults");
    
    private final String path;
    private final String def;
    private static YamlConfiguration LANG;
    
    private Lang(final String path, final String def) {
        this.path = path;
        this.def = def;
    }
    
    public static void setFile(final YamlConfiguration lang) {
        Lang.LANG = lang;
    }
    
    @Override
    public String toString() {
        if (this == Lang.TITLE) {
            return ChatColor.translateAlternateColorCodes('&', Lang.LANG.getString(this.path, this.def)) + " ";
        }
        return ChatColor.translateAlternateColorCodes('&', Lang.LANG.getString(this.path, this.def));
    }
    
    public String getDefault() {
        return this.def;
    }
    
    public String getPath() {
        return this.path;
    }
}
