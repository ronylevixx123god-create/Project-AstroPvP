package me.astropvp.cratekeys.enums;

import com.cosmicpvp.cosmicutils.utils.CC;

public enum CrateType {
    NOVA("Nova", "&e"),
    STELLAR("Stellar", "&c"),
    BUNDLE("Bundle", "&d"),
    APOLLO("Apollo", "&b"),
    WINTER("Winter", "&3");

    private final String name;

    private final String prefix;

    CrateType(String name, String prefix) {
        this.name = name;
        this.prefix = CC.translate(prefix);
    }

    public String getName() {
        return this.name;
    }

    public String getPrefix() {
        return this.prefix;
    }

    public String getPrefixedName() {
        return CC.translate( getPrefix() + CC.Bold + getName() + " &f&lCrate Key");
    }
}
