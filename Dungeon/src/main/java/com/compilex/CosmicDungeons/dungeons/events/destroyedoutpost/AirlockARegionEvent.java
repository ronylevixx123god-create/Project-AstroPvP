package com.compilex.CosmicDungeons.dungeons.events.destroyedoutpost;

import com.compilex.CosmicDungeons.dungeons.events.RegionEvent;
import com.compilex.CosmicDungeons.dungeons.libs.Dungeon;
import com.cosmicpvp.cosmicutils.utils.CC;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import org.jetbrains.annotations.NotNull;

@Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\n"}, d2={"Lcom/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/AirlockARegionEvent;", "Lcom/compilex/CosmicDungeons/dungeons/events/RegionEvent;", "d", "Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;", "regionName", "", "(Lcom/compilex/CosmicDungeons/dungeons/libs/Dungeon;Ljava/lang/String;)V", "run", "", "Companion", "AstroDungeons"})
public final class AirlockARegionEvent
extends RegionEvent {
    public static final Companion Companion = new Companion(null);

    @Override
    public void run() {
        this.dungeon.broadcastMessage(CC.White + "This is the main airlock leading to the planet's surface. It seems to be heavily damaged, find a way to operate it.");
        this.dungeon.updateTimeRestrictionTask(System.currentTimeMillis() + 300000L, "Open the Airlock");
    }

    public AirlockARegionEvent(@NotNull Dungeon d, @NotNull String regionName) {
        super(d, regionName);
        Intrinsics.checkParameterIsNotNull((Object)d, (String)"d");
        Intrinsics.checkParameterIsNotNull((Object)regionName, (String)"regionName");
    }

    @Metadata(mv={1, 4, 0}, bv={1, 0, 3}, k=1, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u00a8\u0006\u0007"}, d2={"Lcom/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/AirlockARegionEvent$Companion;", "", "()V", "$$$reportNull$$$0", "", "n", "", "AstroDungeons"})
    public static final class Companion {
        private final void $$$reportNull$$$0(int n) throws Throwable {
            String format = "Argument for @NotNull parameter '%s' of %s.%s must not be null";
            Object[] args = new Object[3];
            switch (n) {
                case 1: {
                    args[0] = "regionName";
                    break;
                }
                default: {
                    args[0] = "d";
                }
            }
            args[1] = "com/compilex/CosmicDungeons/dungeons/events/destroyedoutpost/AirlockARegionEvent";
            args[2] = "<init>";
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            Object[] arrobject = Arrays.copyOf(args, args.length);
            String string = String.format(format, Arrays.copyOf(arrobject, arrobject.length));
            Intrinsics.checkExpressionValueIsNotNull((Object)string, (String)"java.lang.String.format(format, *args)");
            throw (Throwable)new IllegalArgumentException(string);
        }

        private Companion(Object o) {
        }
    }
}

