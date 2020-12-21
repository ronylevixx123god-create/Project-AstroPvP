/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit;

import com.google.common.collect.ImmutableMap;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Target(value={ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.TYPE})
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Warning {
    public boolean value() default false;

    public String reason() default "";

    public static enum WarningState {
        ON,
        OFF,
        DEFAULT;

        private static final Map<String, WarningState> values;

        public boolean printFor(Warning warning) {
            if (this == DEFAULT) {
                return warning == null || warning.value();
            }
            return this == ON;
        }

        public static WarningState value(String value) {
            if (value == null) {
                return DEFAULT;
            }
            WarningState state = values.get(value.toLowerCase());
            if (state == null) {
                return DEFAULT;
            }
            return state;
        }

        static {
            values = ImmutableMap.builder().put("off", OFF).put("false", OFF).put("f", OFF).put("no", OFF).put("n", OFF).put("on", ON).put("true", ON).put("t", ON).put("yes", ON).put("y", ON).put("", DEFAULT).put("d", DEFAULT).put("default", DEFAULT).build();
        }
    }
}

