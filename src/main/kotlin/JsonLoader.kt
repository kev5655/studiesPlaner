import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import data.Root
import java.io.File

class JsonLoader(private val path: String) {

    private val mapper = jacksonObjectMapper()

    fun loadFile(): String = File(path).readText()
    private fun loadFileAsByte(): ByteArray = File(path).readBytes()
    fun loadJson(): Root = this.mapper.readValue(loadFileAsByte(), Root::class.java)

}