package com.massivecraft.factions.listeners;

public class FactionsSpawnerBoostListener extends AbstractListener{

/*    public FactionsPlugin plugin;

    public FactionsSpawnerBoostListener(FactionsPlugin plugin) {
        this.plugin = plugin;
    }

    private static Field spawnDelay;

    private static Method getTileEntity;

    private static Method getSpawner;

    private static boolean doSpawnerUpgrade;

    static{
        try{
            Class<?> mobSpawnerAbstractClass = Class.forName("net.minecraft.server." + FactionsPlugin.version() + ".MobSpawnerAbstract");

            getTileEntity = Class.forName("org.bukkit.craftbukkit." + FactionsPlugin.version() + ".block.CraftCreatureSpawner").getMethod("getTileEntity");

            getSpawner = Class.forName("net.minecraft.server." + FactionsPlugin.version() + ".TileEntityMobSpawner").getMethod("getSpawner");

            spawnDelay = mobSpawnerAbstractClass.getField("spawnDelay");
        }catch(ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e){
            e.printStackTrace();

            Bukkit.getLogger().severe("[Factions] - There was an error while setting up the spawner upgrade!");

            doSpawnerUpgrade = false;
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSpawn(SpawnerPreSpawnEvent e) throws InvocationTargetException, IllegalAccessException {
        if(!doSpawnerUpgrade || !FactionsPlugin.getInstance().conf().upgrades().spawnerBoost().isEnabled())return;

        FLocation fLocation = new FLocation(e.getLocation());

        Faction factionAt = Board.getInstance().getFactionAt(fLocation);

        if(factionAt.isWilderness())return;

        int upgrade = factionAt.getUpgrade(UpgradeType.SPAWNER_BOOST);

        if(upgrade <= 0 && FactionsPlugin.getInstance().conf().upgrades().spawnerBoost().getDefaultBoost() == 1.0D)return;

        double boost = FactionsPlugin.getInstance().conf().upgrades().spawnerBoost().getNumber(upgrade);

        CreatureSpawner creatureSpawner = (CreatureSpawner) e.getLocation().getBlock().getState();

        Object tileEntityMobSpawner = getSpawner.invoke(getTileEntity.invoke(creatureSpawner));

        new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    spawnDelay.set(tileEntityMobSpawner, (int) Math.floor((int) spawnDelay.get(tileEntityMobSpawner) * boost));
                } catch (IllegalAccessException ex) {
                    ex.printStackTrace();
                }
            }
        }.runTaskLaterAsynchronously(FactionsPlugin.getInstance(), 1);
    }*/
}
