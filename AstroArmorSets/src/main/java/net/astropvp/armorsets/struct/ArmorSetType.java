package net.astropvp.armorsets.struct;

import com.cosmicpvp.cosmicutils.utils.CC;
import lombok.Getter;
import net.astropvp.armorsets.struct.ArmorSet;
import net.astropvp.armorsets.struct.sets.PhantomArmorSet;

@Getter
public enum ArmorSetType {
    PHANTOM(CC.translate("&c&lPhantom"), new PhantomArmorSet());

    private final String display;
    private final ArmorSet armorSet;

    private ArmorSetType(String display, ArmorSet armorSet) {
        this.display = display;
        this.armorSet = armorSet;
    }


}
