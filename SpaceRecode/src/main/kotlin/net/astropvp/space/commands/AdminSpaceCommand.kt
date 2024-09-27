package net.astropvp.space.commands

import me.mattstudios.mf.annotations.Command
import me.mattstudios.mf.annotations.Default
import me.mattstudios.mf.annotations.Permission
import me.mattstudios.mf.annotations.SubCommand
import me.mattstudios.mf.base.CommandBase
import net.astropvp.space.SpaceAPI
import net.astropvp.space.hostile.MobManager
import net.astropvp.space.items.ZombieItemPlacer
import net.astropvp.space.utils.msg
import org.bukkit.Bukkit
import org.bukkit.entity.Player

@Command("aspace")
class AdminSpaceCommand : CommandBase() {

    @Default
    @Permission("space.admin")
    fun defaultCommand(player: Player) {

    }

    @SubCommand("killall")
    @Permission("space.admin")
    fun killAllCommand(player: Player) {
        MobManager.activeEntities.forEach { it.value.remove() }
        MobManager.activeEntities.clear()
        SpaceAPI.WORLDS.forEach { Bukkit.getWorld(it).entities.forEach { entity -> entity.remove() }}
        SpaceAPI.WORLDS.forEach { Bukkit.getWorld(it).players.forEach { player -> player.msg("&d&l(!) &dAll space mobs have been cleared, new ones will spawn soon")}}
    }

    @SubCommand("zombieplacer")
    @Permission("space.admin")
    fun zombiePlacerCommand(player: Player) {
        player.inventory.addItem(ZombieItemPlacer.getItem())
    }
}