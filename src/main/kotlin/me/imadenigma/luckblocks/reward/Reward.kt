package me.imadenigma.luckblocks.reward

import me.imadenigma.luckblocks.LuckBlocks
import me.imadenigma.luckblocks.colorize
import me.imadenigma.luckblocks.placeHolderItem
import me.imadenigma.luckblocks.sendM
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class Reward constructor(
    private val type: RewardType,
    val chance: Int,
    private val message: String,
    private val item: ItemStack? = null,
    private val cmd: String? = null
) {

    init {
        this.message.colorize()
        rewards.add(this)
    }

    fun applyReward(player: Player) {
        if (this.type == RewardType.ITEM) {
            player.sendM(message.placeHolderItem(item!!))
            if (player.inventory.firstEmpty() != -1) {
                player.inventory.addItem(this.item)
            } else {
                player.world.dropItem(player.location, this.item)
            }
        } else {
            player.sendM(message)
            if (!cmd.equals("none", true)) {
                Bukkit.getScheduler().runTaskLater(LuckBlocks.instance, Runnable {
                    Bukkit.dispatchCommand(
                        Bukkit.getConsoleSender(),
                        cmd?.replace("{player}", player.name)?.replace("_", " ") ?: ""
                    )
                }, 3L)
            }
        }

    }

    companion object {
        val rewards = mutableSetOf<Reward>()
    }
}