/*
 * Decompiled with CFR 0.150.
 */
package org.arkhamnetwork.Arkkit;

import java.util.HashMap;
import org.arkhamnetwork.Arkkit.CustomPV;

class CustomPlayerVaults
extends HashMap<Integer, CustomPV> {
    public boolean isCustomPV(int i) {
        return this.containsKey(i);
    }

    public void storePV(int pvNumber, CustomPV customPV) {
        this.put(pvNumber, customPV);
    }
}

