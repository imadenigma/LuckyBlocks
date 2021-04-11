package me.imadenigma.luckblocks.reward

import me.imadenigma.luckblocks.Configuration
import me.imadenigma.luckblocks.LuckBlocks
import me.imadenigma.luckblocks.colorize
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.inventory.ItemStack
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.io.File
import kotlin.system.measureTimeMillis

class Commands : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (args.isNotEmpty()) {
            if (args[0].equals("give", true)) {
                if (sender.hasPermission("luckyblock.give")) {
                    if (args.size >= 3) {
                        val player = Bukkit.getPlayer(args[1]) ?: return false
                        if (player.inventory.firstEmpty() != -1) {
                            player.inventory.addItem(ItemStack(Configuration.luckyType, args[2].toInt()))
                        } else player.world.dropItem(
                            player.location,
                            ItemStack(Configuration.luckyType, args[1].toInt())
                        )
                        sender.sendMessage("&b Sent".colorize())
                    } else return false
                } else sender.sendMessage("&4You don't have permission to execute this command".colorize())
            } else if (args[0].equals("reload", true)) {
                if (sender.hasPermission("luckyblock.reload")) {
                    sender.sendMessage("&4reloading...".colorize())
                    val ms = measureTimeMillis {
                        val file = File(LuckBlocks.instance.dataFolder, "config.yml")
                        val loader = YamlConfigurationLoader.builder().file(file).build()
                        Configuration.config = loader.load()
                        Configuration.luckyType =
                            Material.matchMaterial(Configuration.config.node("Lucky-block").getString("SPONGE"))!!
                    }
                    sender.sendMessage("&breloading took &4$ms &ams &7(that's great)".colorize())
                } else sender.sendMessage("&4You don't have permission to execute this command".colorize())
            }
        }
        return true
    }
}