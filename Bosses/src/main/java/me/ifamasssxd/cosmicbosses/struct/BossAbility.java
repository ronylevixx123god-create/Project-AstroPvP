/*
 * Decompiled with CFR 0.150.
 */
package me.ifamasssxd.cosmicbosses.struct;

public abstract class BossAbility<T extends Boss> {
    public T boss;
    protected BlockModifier blockModifier;

    public BossAbility(T boss) {
        this.boss = boss;
    }

    public BossAbility(T boss, BlockModifier modifier) {
        this.boss = boss;
        this.blockModifier = modifier;
    }

    public abstract void onAbility();

    public abstract long getCooldown();

    public abstract String getAbilityName();

    public boolean canUse() {
        if (((Boss)this.boss).getAbilityCooldown().containsKey(this)) {
            long cooldown = ((Boss)this.boss).getAbilityCooldown().get(this);
            if (cooldown >= System.currentTimeMillis()) {
                return false;
            }
            ((Boss)this.boss).getAbilityCooldown().remove(this);
        }
        return true;
    }

    public void activateCooldown() {
        ((Boss)this.boss).getAbilityCooldown().put(this, System.currentTimeMillis() + this.getCooldown());
    }

    public T getBoss() {
        return this.boss;
    }

    public BlockModifier getBlockModifier() {
        return this.blockModifier;
    }
}

