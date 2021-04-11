package me.imadenigma.luckblocks


import me.imadenigma.luckblocks.reward.Reward
import me.imadenigma.luckblocks.reward.RewardType
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.io.File
import kotlin.system.measureTimeMillis

class Configuration {

    init {
        LuckBlocks.instance.logger.info("Loading Configuration...")
        val ms = measureTimeMillis {
            val file = File(LuckBlocks.instance.dataFolder, "config.yml")
            file.checkExistence()
            val loader = YamlConfigurationLoader.builder().file(file).build()
            if (loader.canLoad()) {
                config = loader.load()
            }
            luckyType = Material.matchMaterial(config.node("Lucky-block").getString("SPONGE")!!)!!
            val itemMsg = config.node("break-lucky-items").getString("msg")

            for (pair in config.node("items").childrenMap()) {
                val type = Material.matchMaterial(pair.key.toString())
                val name = pair.value.node("name").getString("name")
                val amount = pair.value.node("amount").getInt(10)
                val item = ItemStack(type!!, amount)
                item.setDisplayName(name.colorize())
                val chance = pair.value.node("chance").getInt(10)
                Reward(RewardType.ITEM, chance, itemMsg, item)
            }

            for (pair in config.node("commands").childrenMap()) {
                val cmd = pair.key.toString()
                val msg = pair.value.node("msg").getString("")
                val chance = pair.value.node("chance").getInt(10)
                Reward(RewardType.COMMAND, chance, msg, cmd = cmd)
            }

        }
        LuckBlocks.instance.logger.info("Loading Configuration took $ms ms")

    }

    companion object {
        lateinit var config: ConfigurationNode
        lateinit var luckyType: Material

    }


}