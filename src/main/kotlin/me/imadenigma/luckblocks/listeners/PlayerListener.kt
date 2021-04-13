package me.imadenigma.luckblocks.listeners

import me.imadenigma.luckblocks.Configuration
import me.imadenigma.luckblocks.reward.Reward
import org.apache.commons.lang.math.RandomUtils
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import java.util.Collections.max
import kotlin.streams.toList

class PlayerListener : Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onBlockBreak(e: BlockBreakEvent) {
        if (e.block.type == Configuration.luckyType) {
            var randomInt = RandomUtils.nextInt(max(Reward.rewards.stream().map { it.chance }.toList()))
            val rewards = Reward.rewards.stream().filter { it.chance >= randomInt }.toList()
            if (rewards.isEmpty()) return
            randomInt = RandomUtils.nextInt(rewards.size)
            rewards[randomInt].applyReward(e.player)
            e.block.drops.clear()
            e.isDropItems = false
        }
    }

}