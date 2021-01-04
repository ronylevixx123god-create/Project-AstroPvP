/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.exceptions.CosmicException
 *  com.cosmicpvp.cosmicutils.utils.CC
 */
package me.ifamasssxd.cosmicskycustoms.exceptions;

import com.cosmicpvp.cosmicutils.exceptions.CosmicException;
import com.cosmicpvp.cosmicutils.utils.CC;

public class PrefixedItemException
extends CosmicException {
    public PrefixedItemException(String message) {
        super(CC.RedB + "(!) " + CC.Red + message);
    }
}

