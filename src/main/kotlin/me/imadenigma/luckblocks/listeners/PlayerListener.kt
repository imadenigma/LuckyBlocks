package me.imadenigma.luckblocks.listeners

import me.imadenigma.luckblocks.Configuration
import me.imadenigma.luckblocks.reward.Reward
import org.apache.commons.lang.math.RandomUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import kotlin.streams.toList

class PlayerListener : Listener {

    @EventHandler
    fun onBlockBreak(e: BlockBreakEvent) {
        if (e.block.type == Configuration.luckyType) {
            var randomInt = RandomUtils.nextInt(100)
            val rewards = Reward.rewards.stream().filter { it.chance >= randomInt }.toList()
            try {
                randomInt = RandomUtils.nextInt(rewards.size)
                rewards[randomInt].applyReward(e.player)
            }catch (e: IllegalArgumentException) {}
            catch (e: ArrayIndexOutOfBoundsException) {}
            e.block.drops.clear()
            e.isDropItems = false
        }
    }

}