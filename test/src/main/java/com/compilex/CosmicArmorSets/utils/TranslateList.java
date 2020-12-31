/*
 * Decompiled with CFR 0.150.
 *
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.CC
 */
package com.compilex.CosmicArmorSets.utils;

import com.cosmicpvp.cosmicutils.utils.CC;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TranslateList {
    public static List<String> translateList(String... string) {
        return Arrays.stream(string).map(CC::translate).collect(Collectors.toList());
    }
}

