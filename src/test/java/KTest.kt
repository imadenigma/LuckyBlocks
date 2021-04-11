import junit.framework.TestCase
import org.spongepowered.configurate.yaml.YamlConfigurationLoader
import java.io.File

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

}