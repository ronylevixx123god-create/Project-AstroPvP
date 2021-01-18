/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.cosmicpvp.cosmicutils.utils.NBTWrapper
 *  org.bukkit.inventory.ItemStack
 */
package me.ifamasssxd.cosmicskycustoms.struct;

import com.cosmicpvp.cosmicutils.utils.NBTWrapper;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ItemPersistentData {
    private Constructor<CosmicPersistentItem> constructor;
    private Constructor<CosmicPersistentItem> simpleConstructor;
    private Class<? extends CosmicPersistentItem> persistentItem;
    private CosmicPersistentItem sampleVersion;
    private String internalName;

    ItemPersistentData(String internal, Class<? extends CosmicPersistentItem> clazz) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        this(clazz.getConstructor(ItemStack.class, NBTWrapper.class, String.class), clazz.getConstructor(String.class), clazz, null, internal);
        this.sampleVersion = this.createSampleInstance();
    }

    public ItemPersistentData(Constructor<? extends CosmicPersistentItem> constructor, Constructor<? extends CosmicPersistentItem> constructor1, Class<? extends CosmicPersistentItem> clazz, Object sampleVersion, String internal) {
    }

    public CosmicPersistentItem createInstance(ItemStack itemStack, NBTWrapper wrapper) throws IllegalAccessException, InvocationTargetException, InstantiationException {
        return this.constructor.newInstance(itemStack, wrapper, this.internalName);
    }

    public CosmicPersistentItem createSampleInstance() {
        Bukkit.getConsoleSender().sendMessage("createdSampleInstance ************");
        try {
            return this.simpleConstructor.newInstance(this.internalName);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemPersistentData(Constructor<CosmicPersistentItem> constructor, Constructor<CosmicPersistentItem> simpleConstructor, Class<? extends CosmicPersistentItem> persistentItem, CosmicPersistentItem sampleVersion, String internalName) {
        this.constructor = constructor;
        this.simpleConstructor = simpleConstructor;
        this.persistentItem = persistentItem;
        this.sampleVersion = sampleVersion;
        this.internalName = internalName;
    }

    public Constructor<CosmicPersistentItem> getConstructor() {
        return this.constructor;
    }

    public Constructor<CosmicPersistentItem> getSimpleConstructor() {
        return this.simpleConstructor;
    }

    public Class<? extends CosmicPersistentItem> getPersistentItem() {
        return this.persistentItem;
    }

    public CosmicPersistentItem getSampleVersion() {
        return this.sampleVersion;
    }

    public String getInternalName() {
        return this.internalName;
    }
}

