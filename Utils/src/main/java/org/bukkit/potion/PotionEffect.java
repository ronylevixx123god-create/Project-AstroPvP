/*
 * Decompiled with CFR 0.150.
 */
package org.bukkit.potion;

import com.google.common.collect.ImmutableMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffectType;

@SerializableAs(value="PotionEffect")
public class PotionEffect
implements ConfigurationSerializable {
    private static final String AMPLIFIER = "amplifier";
    private static final String DURATION = "duration";
    private static final String TYPE = "effect";
    private static final String AMBIENT = "ambient";
    private final int amplifier;
    private final int duration;
    private final PotionEffectType type;
    private final boolean ambient;

    public PotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
        Validate.notNull(type, "effect type cannot be null");
        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
        this.ambient = ambient;
    }

    public PotionEffect(PotionEffectType type, int duration, int amplifier) {
        this(type, duration, amplifier, true);
    }

    public PotionEffect(Map<String, Object> map) {
        this(PotionEffect.getEffectType(map), PotionEffect.getInt(map, DURATION), PotionEffect.getInt(map, AMPLIFIER), PotionEffect.getBool(map, AMBIENT));
    }

    private static PotionEffectType getEffectType(Map<?, ?> map) {
        int type = PotionEffect.getInt(map, TYPE);
        PotionEffectType effect = PotionEffectType.getById(type);
        if (effect != null) {
            return effect;
        }
        throw new NoSuchElementException(map + " does not contain " + TYPE);
    }

    private static int getInt(Map<?, ?> map, Object key) {
        Object num = map.get(key);
        if (num instanceof Integer) {
            return (Integer)num;
        }
        throw new NoSuchElementException(map + " does not contain " + key);
    }

    private static boolean getBool(Map<?, ?> map, Object key) {
        Object bool = map.get(key);
        if (bool instanceof Boolean) {
            return (Boolean)bool;
        }
        throw new NoSuchElementException(map + " does not contain " + key);
    }

    @Override
    public Map<String, Object> serialize() {
        return ImmutableMap.of(TYPE, this.type.getId(), DURATION, this.duration, AMPLIFIER, this.amplifier, AMBIENT, this.ambient);
    }

    public boolean apply(LivingEntity entity) {
        return entity.addPotionEffect(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PotionEffect)) {
            return false;
        }
        PotionEffect that = (PotionEffect)obj;
        return this.type.equals(that.type) && this.ambient == that.ambient && this.amplifier == that.amplifier && this.duration == that.duration;
    }

    public int getAmplifier() {
        return this.amplifier;
    }

    public int getDuration() {
        return this.duration;
    }

    public PotionEffectType getType() {
        return this.type;
    }

    public boolean isAmbient() {
        return this.ambient;
    }

    public int hashCode() {
        int hash = 1;
        hash = hash * 31 + this.type.hashCode();
        hash = hash * 31 + this.amplifier;
        hash = hash * 31 + this.duration;
        return hash ^= 0x22222222 >> (this.ambient ? 1 : -1);
    }

    public String toString() {
        return this.type.getName() + (this.ambient ? ":(" : ":") + this.duration + "t-x" + this.amplifier + (this.ambient ? ")" : "");
    }
}

