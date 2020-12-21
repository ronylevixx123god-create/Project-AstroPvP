/*
 * Decompiled with CFR 0.150.
 */
package com.sk89q.worldguard.protection;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.Flag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.RegionGroupFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ApplicableRegionSet
implements Iterable<ProtectedRegion> {
    private Collection<ProtectedRegion> applicable;
    private ProtectedRegion globalRegion;

    public ApplicableRegionSet(Collection<ProtectedRegion> applicable, ProtectedRegion globalRegion) {
        this.applicable = applicable;
        this.globalRegion = globalRegion;
    }

    public boolean canBuild(LocalPlayer player) {
        return this.internalGetState(DefaultFlag.BUILD, player, null);
    }

    public boolean canConstruct(LocalPlayer player) {
        RegionGroup flag = (RegionGroup)((Object)this.getFlag(DefaultFlag.CONSTRUCT, player));
        return RegionGroupFlag.isMember(this, flag, player);
    }

    @Deprecated
    public boolean canUse(LocalPlayer player) {
        return !this.allows(DefaultFlag.USE, player) && !this.canBuild(player);
    }

    public boolean allows(StateFlag flag) {
        if (flag == DefaultFlag.BUILD) {
            throw new IllegalArgumentException("Can't use build flag with allows()");
        }
        return this.internalGetState(flag, null, null);
    }

    public boolean allows(StateFlag flag, LocalPlayer player) {
        if (flag == DefaultFlag.BUILD) {
            throw new IllegalArgumentException("Can't use build flag with allows()");
        }
        return this.internalGetState(flag, null, player);
    }

    public boolean isOwnerOfAll(LocalPlayer player) {
        for (ProtectedRegion region : this.applicable) {
            if (region.isOwner(player)) continue;
            return false;
        }
        return true;
    }

    public boolean isMemberOfAll(LocalPlayer player) {
        for (ProtectedRegion region : this.applicable) {
            if (region.isMember(player)) continue;
            return false;
        }
        return true;
    }

    private boolean internalGetState(StateFlag flag, LocalPlayer player, LocalPlayer groupPlayer) {
        boolean found = false;
        boolean hasFlagDefined = false;
        boolean allowed = false;
        boolean def = flag.getDefault();
        if (this.globalRegion != null) {
            StateFlag.State globalState = (StateFlag.State)((Object)this.globalRegion.getFlag(flag));
            if (globalState != null) {
                def = player != null && this.globalRegion.hasMembersOrOwners() ? this.globalRegion.isMember(player) && globalState == StateFlag.State.ALLOW : globalState == StateFlag.State.ALLOW;
            } else if (player != null && this.globalRegion.hasMembersOrOwners()) {
                def = this.globalRegion.isMember(player);
            }
        }
        if (player == null) {
            allowed = def;
        }
        int lastPriority = Integer.MIN_VALUE;
        HashSet<ProtectedRegion> needsClear = new HashSet<ProtectedRegion>();
        HashSet<ProtectedRegion> hasCleared = new HashSet<ProtectedRegion>();
        for (ProtectedRegion region : this.applicable) {
            StateFlag.State v;
            if (hasFlagDefined && region.getPriority() < lastPriority) break;
            lastPriority = region.getPriority();
            if (player != null && region.getFlag(DefaultFlag.PASSTHROUGH) == StateFlag.State.ALLOW) continue;
            if (groupPlayer != null && flag.getRegionGroupFlag() != null) {
                RegionGroup group = (RegionGroup)((Object)region.getFlag(flag.getRegionGroupFlag()));
                if (group == null) {
                    group = flag.getRegionGroupFlag().getDefault();
                }
                if (!RegionGroupFlag.isMember(region, group, groupPlayer)) continue;
            }
            if ((v = (StateFlag.State)((Object)region.getFlag(flag))) == StateFlag.State.DENY) {
                return false;
            }
            if (v == StateFlag.State.ALLOW) {
                allowed = true;
                found = true;
                hasFlagDefined = true;
                continue;
            }
            if (player != null) {
                hasFlagDefined = true;
                if (!hasCleared.contains(region)) {
                    if (!region.isMember(player)) {
                        needsClear.add(region);
                    } else {
                        this.clearParents(needsClear, hasCleared, region);
                    }
                }
            }
            found = true;
        }
        return !found ? def : allowed || player != null && needsClear.size() == 0;
    }

    private void clearParents(Set<ProtectedRegion> needsClear, Set<ProtectedRegion> hasCleared, ProtectedRegion region) {
        for (ProtectedRegion parent = region.getParent(); parent != null; parent = parent.getParent()) {
            if (needsClear.remove(parent)) continue;
            hasCleared.add(parent);
        }
    }

    public <T extends Flag<V>, V> V getFlag(T flag) {
        return this.getFlag(flag, null);
    }

    public <T extends Flag<V>, V> V getFlag(T flag, LocalPlayer groupPlayer) {
        Object gFlag;
        int lastPriority = 0;
        boolean found = false;
        HashMap needsClear = new HashMap();
        HashSet<ProtectedRegion> hasCleared = new HashSet<ProtectedRegion>();
        for (ProtectedRegion region : this.applicable) {
            if (found && region.getPriority() < lastPriority) break;
            if (groupPlayer != null && flag.getRegionGroupFlag() != null) {
                RegionGroup group = (RegionGroup)((Object)region.getFlag(flag.getRegionGroupFlag()));
                if (group == null) {
                    group = flag.getRegionGroupFlag().getDefault();
                }
                if (!RegionGroupFlag.isMember(region, group, groupPlayer)) continue;
            }
            if (!hasCleared.contains(region) && region.getFlag(flag) != null) {
                this.clearParents(needsClear, hasCleared, region);
                needsClear.put(region, region.getFlag(flag));
                found = true;
            }
            lastPriority = region.getPriority();
        }
        if (!needsClear.isEmpty()) {
            return needsClear.values().iterator().next();
        }
        if (this.globalRegion != null && (gFlag = this.globalRegion.getFlag(flag)) != null) {
            return gFlag;
        }
        return null;
    }

    private void clearParents(Map<ProtectedRegion, ?> needsClear, Set<ProtectedRegion> hasCleared, ProtectedRegion region) {
        for (ProtectedRegion parent = region.getParent(); parent != null; parent = parent.getParent()) {
            if (needsClear.remove(parent) != null) continue;
            hasCleared.add(parent);
        }
    }

    public int size() {
        return this.applicable.size();
    }

    @Override
    public Iterator<ProtectedRegion> iterator() {
        return this.applicable.iterator();
    }
}

