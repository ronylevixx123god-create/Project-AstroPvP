package com.compilex.CosmicDungeons.utils;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u0000 \u00032\u00020\u0001:\u0001\u0003B\u0005\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0004"}, d2={"Lcom/compilex/CosmicDungeons/utils/WorldUtils;", "", "()V", "Companion", "AstroDungeons"})
public final class WorldUtils {
    public static final Companion Companion = new Companion(null);

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b\u00a8\u0006\t"}, d2={"Lcom/compilex/CosmicDungeons/utils/WorldUtils$Companion;", "", "()V", "scanBlocks", "", "start", "Lorg/bukkit/Location;", "radius", "", "AstroDungeons"})
    public static final class Companion {
        public Companion(Object o) {
        }

        public final void scanBlocks(@NotNull Location start, int radius) {
            Intrinsics.checkParameterIsNotNull((Object)start, (String)"start");
            int x = -radius;
            int var5 = radius;
            if (x <= radius) {
                while (true) {
                    int z = -radius;
                    int var7 = radius;
                    if (z <= radius) {
                        while (true) {
                            int y = -radius;
                            int var9 = radius;
                            if (y <= radius) {
                                while (true) {
                                    Block var10001 = start.getBlock().getRelative(x, y, z);
                                    if (y == var9) break;
                                    ++y;
                                }
                            }
                            if (z == var7) break;
                            ++z;
                        }
                    }
                    if (x == var5) break;
                    ++x;
                }
            }
        }
    }
}

