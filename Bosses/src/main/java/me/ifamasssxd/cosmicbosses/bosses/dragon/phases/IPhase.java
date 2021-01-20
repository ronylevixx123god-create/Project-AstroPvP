package me.ifamasssxd.cosmicbosses.bosses.dragon.phases;

import org.bukkit.*;
import javax.annotation.*;
import net.minecraft.server.v1_8_R3.*;

public interface IPhase
{
    boolean getIsStationary();
    
    void doLocalUpdate() throws NoSuchFieldException;
    
    void onCrystalDestroyed(final EntityEnderCrystal p0, final Location p1, final DamageSource p2, final EntityPlayer p3);
    
    void initPhase();
    
    void removeAreaEffect();
    
    float getMaxRiseOrFall();
    
    float getYawFactor();
    
    PhaseList<? extends IPhase> getType();
    
    @Nullable
    Vec3D getTargetLocation();
    
    float getAdjustedDamage(final EntityComplexPart p0, final DamageSource p1, final float p2);
}
