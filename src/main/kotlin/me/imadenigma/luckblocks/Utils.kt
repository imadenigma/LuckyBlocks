package me.imadenigma.luckblocks

import org.apache.commons.lang.StringUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File
import java.io.FileWriter

fun File.checkExistence() {
    if (this.exists()) return
    this.parentFile.mkdirs()
    this.createNewFile()
    val writer = FileWriter(this)
    val input = LuckBlocks.instance.getResource("config.yml")?.bufferedReader()
    for (line in input?.readLines()!!) {
        writer.appendLine(line)
    }
    writer.flush()
    writer.close()
}

fun String.placeHolderItem(item: ItemStack): String {
    if (!this.contains("{0}")) return this
    return StringUtils.replace(this, "{0}", item.itemMeta?.displayName) ?: "null"
}

fun String.colorize(): String {
    return ChatColor.translateAlternateColorCodes('&', this)
}

fun Player.sendM(msg: String) {
    if (msg == "") return
    if (msg.contains("|")) {
        val lines = StringUtils.split(msg, "|")
        for (line in lines) {
            this.sendMessage(line.colorize())
        }
        return
    }
    this.sendMessage(msg.colorize())
}

fun ItemStack.setDisplayName(name: String) {
    val meta = this.itemMeta ?: Bukkit.getItemFactory().getItemMeta(this.type)
    meta?.setDisplayName(name)
    this.itemMeta = meta
}