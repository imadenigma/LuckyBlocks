package me.imadenigma.luckblocks

import me.imadenigma.luckblocks.listeners.PlayerListener
import me.imadenigma.luckblocks.reward.Commands
import org.bukkit.plugin.java.JavaPlugin

class LuckBlocks : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        instance = this
        Configuration()
        server.pluginManager.registerEvents(PlayerListener(), this)
        getCommand("luckyblock")?.setExecutor(Commands())
        logger.info("&eSetting lucky Block to:&a ${Configuration.luckyType.name}".colorize())
    }

    override fun onDisable() {}

    companion object {
        lateinit var instance: LuckBlocks
    }
}