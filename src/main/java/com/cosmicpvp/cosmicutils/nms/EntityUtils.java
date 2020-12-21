/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_7_R4.AttributeInstance
 */
package com.cosmicpvp.cosmicutils.nms;

import com.cosmicpvp.cosmicutils.nms.EntityAttribute;
import com.cosmicpvp.cosmicutils.utils.ReflectionUtils;
import net.minecraft.server.v1_7_R4.AttributeInstance;
import org.bukkit.entity.Entity;

public class EntityUtils {
    public static void setGenericAttribute(Entity entity, EntityAttribute attribute, float value) {
        AttributeInstance inst = EntityUtils.getGenericAttributes(entity, attribute);
        inst.setValue((double)value);
    }

    private static AttributeInstance getGenericAttributes(Entity entity, EntityAttribute attributes) {
        Object handle = ReflectionUtils.getNMSHandle(entity);
        return (AttributeInstance)ReflectionUtils.invokeMethod(ReflectionUtils.getMethod(ReflectionUtils.getEntityLivingClass(), "getAttributeInstance", ReflectionUtils.getIattributeInstanceClass()), handle, attributes.getNmsEnumObject());
    }
}

