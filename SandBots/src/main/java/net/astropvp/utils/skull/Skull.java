// 
// Decompiled by Procyon v0.5.36
// 

package net.astropvp.utils.skull;

import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.inventory.meta.ItemMeta;
import com.mojang.authlib.properties.PropertyMap;
import org.bukkit.Material;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.GameProfile;
import java.util.UUID;
import org.bukkit.inventory.ItemStack;
import org.apache.commons.codec.binary.Base64;

public enum Skull
{
    ARROW_LEFT("MHF_ArrowLeft"), 
    ARROW_RIGHT("MHF_ArrowRight"), 
    ARROW_UP("MHF_ArrowUp"), 
    ARROW_DOWN("MHF_ArrowDown"), 
    QUESTION("MHF_Question"), 
    EXCLAMATION("MHF_Exclamation"), 
    CAMERA("FHG_Cam"), 
    ZOMBIE_PIGMAN("MHF_PigZombie"), 
    PIG("MHF_Pig"), 
    SHEEP("MHF_Sheep"), 
    BLAZE("MHF_Blaze"), 
    CHICKEN("MHF_Chicken"), 
    COW("MHF_Cow"), 
    SLIME("MHF_Slime"), 
    SPIDER("MHF_Spider"), 
    SQUID("MHF_Squid"), 
    VILLAGER("MHF_Villager"), 
    OCELOT("MHF_Ocelot"), 
    HEROBRINE("MHF_Herobrine"), 
    LAVA_SLIME("MHF_LavaSlime"), 
    MOOSHROOM("MHF_MushroomCow"), 
    GOLEM("MHF_Golem"), 
    GHAST("MHF_Ghast"), 
    ENDERMAN("MHF_Enderman"), 
    CAVE_SPIDER("MHF_CaveSpider"), 
    CACTUS("MHF_Cactus"), 
    CAKE("MHF_Cake"), 
    CHEST("MHF_Chest"), 
    MELON("MHF_Melon"), 
    LOG("MHF_OakLog"), 
    PUMPKIN("MHF_Pumpkin"), 
    TNT("MHF_TNT"), 
    DYNAMITE("MHF_TNT2");
    
    private static final Base64 base64;
    private String id;
    
    private Skull(final String id) {
        this.id = id;
    }
    
    public static ItemStack getCustomSkull(final String url) {
        final GameProfile profile = new GameProfile(UUID.randomUUID(), (String)null);
        final PropertyMap propertyMap = profile.getProperties();
        if (propertyMap == null) {
            throw new IllegalStateException("Profile doesn't contain a property map");
        }
        final byte[] encodedData = Skull.base64.encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        propertyMap.put("textures", new Property("textures", new String(encodedData)));
        final ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final ItemMeta headMeta = head.getItemMeta();
        final Class<?> headMetaClass = headMeta.getClass();
        Reflections.getField(headMetaClass, "profile", GameProfile.class).set(headMeta, profile);
        head.setItemMeta(headMeta);
        return head;
    }
    
    public static ItemStack getPlayerSkull(final String name) {
        final ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
        meta.setOwner(name);
        itemStack.setItemMeta((ItemMeta)meta);
        return itemStack;
    }
    
    public String getId() {
        return this.id;
    }
    
    public ItemStack getSkull() {
        final ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        final SkullMeta meta = (SkullMeta)itemStack.getItemMeta();
        meta.setOwner(this.id);
        itemStack.setItemMeta((ItemMeta)meta);
        return itemStack;
    }
    
    static {
        base64 = new Base64();
    }
}
