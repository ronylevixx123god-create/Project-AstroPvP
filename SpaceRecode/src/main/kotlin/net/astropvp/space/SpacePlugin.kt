package net.astropvp.space

import me.astropvp.astropvpskins.enums.SkinType
import me.mattstudios.mf.base.CommandManager
import net.astropvp.space.commands.AdminSpaceCommand
import net.astropvp.space.commands.SpaceCommand
import net.astropvp.space.data.SpacePlayerManager
import net.astropvp.space.data.temp.ActiveSpaceDataManager
import net.astropvp.space.listeners.*
import net.astropvp.space.serialization.SkinTypeSerializerDeserializer
import net.astropvp.space.struct.LocationManager
import net.astropvp.space.tasks.ClearMobsTask
import net.astropvp.space.tasks.LongestSurvivorTask
import net.astropvp.space.tasks.MobSpawnTask
import net.astropvp.space.tasks.SpaceDataCacheFixTask
import net.astropvp.storage.StoragePlugin
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin

class SpacePlugin : JavaPlugin() {

    var spacePlayerManager: SpacePlayerManager? = null

    init {
        instance = this
        activeSpaceDataManager = ActiveSpaceDataManager()
    }

    companion object {
        lateinit var instance : SpacePlugin
        lateinit var activeSpaceDataManager : ActiveSpaceDataManager
    }

    override fun onEnable() {

        val gson = StoragePlugin.getInstance()
            .gson
            .newBuilder()
            .create()
        spacePlayerManager = SpacePlayerManager(this, gson)
        spacePlayerManager!!.loadAll()

        val commandManager = CommandManager(this)
        commandManager.register(SpaceCommand())
        commandManager.register(AdminSpaceCommand())
        LocationManager.loadLocationsFromDisk()
        registerListeners()

        val spaceDataCacheFixTask = SpaceDataCacheFixTask()
        spaceDataCacheFixTask.runTaskTimer(this, (60 * 20 * 20L) + 80L, 60 * 20 * 20L)

        val spawnMobSpawnTask = MobSpawnTask()
        spawnMobSpawnTask.runTaskTimer(this, 5 * 20L, 5 * 20L) // try spawn every 5s

        val clearMobsTask = ClearMobsTask()
        clearMobsTask.runTaskTimer(this, (60 * 20L) + 40L, 60 * 30 * 20L) // clear every 30m

        val longestSurvivorTask = LongestSurvivorTask()
        longestSurvivorTask.runTaskTimer(this, (60 * 5 * 20L) + 20L, 60 * 5 * 20L)
    }

    private fun registerListeners() {
        val pm = server.pluginManager
        pm.registerEvents(PreventStealLootListener(), this)
        pm.registerEvents(SpaceLogicListener(), this)
        pm.registerEvents(LocationSetListener(), this)
        pm.registerEvents(PlayerQuitListener(), this)
        pm.registerEvents(DisableFlightListener(), this)
        pm.registerEvents(DisableEggBreakListener(), this)
        pm.registerEvents(DisableSpawnEggListener(), this)
        pm.registerEvents(PlayerJoinListener(), this)
        pm.registerEvents(PlayerDeathListener(), this)
        pm.registerEvents(MaxHealthFixListener(), this)
    }

    override fun onDisable() {
        SpaceAPI.WORLDS.forEach { Bukkit.getWorld(it).entities.forEach { entity -> entity.remove() }}
        spacePlayerManager?.saveAll()
        activeSpaceDataManager.spacePlayers.values.forEach { it.teleportAndExit() }
        LocationManager.saveLocationsToDisk()
    }


}