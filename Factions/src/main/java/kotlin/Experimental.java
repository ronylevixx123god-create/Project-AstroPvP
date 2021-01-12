/*
 * Decompiled with CFR 0.150.
 */
package kotlin;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import kotlin.Deprecated;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;
import kotlin.annotation.Target;

@Target(allowedTargets={AnnotationTarget.ANNOTATION_CLASS})
@Retention(value=AnnotationRetention.BINARY)
@java.lang.annotation.Retention(value=RetentionPolicy.CLASS)
@java.lang.annotation.Target(value={ElementType.ANNOTATION_TYPE})
@Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=1, d1={"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0087\u0002\u0018\u00002\u00020\u0001:\u0002\u0004\u0005B\n\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003R\t\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0000\u00f8\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009120\u0001\u00a8\u0006\u0006"}, d2={"Lkotlin/Experimental;", "", "level", "Lkotlin/Experimental$Level;", "Impact", "Level", "kotlin-stdlib"})
@SinceKotlin(version="1.2")
public @interface Experimental {
    public Level level() default Level.ERROR;

    @Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lkotlin/Experimental$Level;", "", "(Ljava/lang/String;I)V", "WARNING", "ERROR", "kotlin-stdlib"})
    public static final class Level
    extends Enum<Level> {
        public static final /* enum */ Level WARNING;
        public static final /* enum */ Level ERROR;
        private static final /* synthetic */ Level[] $VALUES;

        static {
            Level[] arrlevel = new Level[2];
            Level[] arrlevel2 = arrlevel;
            arrlevel[0] = WARNING = new Level();
            arrlevel[1] = ERROR = new Level();
            $VALUES = arrlevel;
        }

        public static Level[] values() {
            return (Level[])$VALUES.clone();
        }

        public static Level valueOf(String string) {
            return Enum.valueOf(Level.class, string);
        }
    }

    @Deprecated(message="This enum is going to be removed and should no longer be used.")
    @Metadata(mv={1, 1, 10}, bv={1, 0, 2}, k=1, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0087\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2={"Lkotlin/Experimental$Impact;", "", "(Ljava/lang/String;I)V", "COMPILATION", "LINKAGE", "RUNTIME", "kotlin-stdlib"})
    public static final class Impact
    extends Enum<Impact> {
        public static final /* enum */ Impact COMPILATION;
        public static final /* enum */ Impact LINKAGE;
        public static final /* enum */ Impact RUNTIME;
        private static final /* synthetic */ Impact[] $VALUES;

        static {
            Impact[] arrimpact = new Impact[3];
            Impact[] arrimpact2 = arrimpact;
            arrimpact[0] = COMPILATION = new Impact();
            arrimpact[1] = LINKAGE = new Impact();
            arrimpact[2] = RUNTIME = new Impact();
            $VALUES = arrimpact;
        }

        public static Impact[] values() {
            return (Impact[])$VALUES.clone();
        }

        public static Impact valueOf(String string) {
            return Enum.valueOf(Impact.class, string);
        }
    }
}

