/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 *  com.google.common.collect.Lists
 */
package me.astropvp.objects.skins;

import com.cosmicpvp.cosmicutils.utils.CC;
import com.google.common.collect.Lists;
import java.util.List;
import me.astropvp.objects.Backpack;
import me.astropvp.objects.BackpackType;

public class BugOutBagSkin
extends Backpack {
    @Override
    public BackpackType getType() {
        return BackpackType.BUG_OUT_BAG;
    }

    @Override
    public List<String> getLore() {
        return Lists.newArrayList(CC.Red + "10% chance to keep each equipped item on death.");
    }
}

