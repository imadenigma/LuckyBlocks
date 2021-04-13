import junit.framework.TestCase
import org.apache.commons.lang.math.RandomUtils
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.io.File
import java.util.Collections.max


class KTest: TestCase() {

    fun test() {
        val loader = YamlConfigurationLoader.builder().file(File("/home/imad/IdeaProjects/LuckBlocks/src/main/resources/","plugin.yml")).build()
        println(loader.canLoad())
        var node = loader.load()
        println(node.node("name").getString(""))
        Thread.sleep(10000)
        node = loader.load()
        println(node.node("name").getString("null"))
    }

    fun test1() {
        for (i in 0..100)
            println(RandomUtils.nextInt(100))
    }
    fun test2() {
        val list = listOf(1,2,3,24562135,136,632,6,8,99,1,3,67)
        println(max(list))
    }

}