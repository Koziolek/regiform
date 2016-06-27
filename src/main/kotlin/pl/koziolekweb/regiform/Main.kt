package pl.koziolekweb.regiform

import org.wasabi.app.AppServer
import java.io.File
import java.util.*

/**
 * Created by BKuczynski on 2016-06-27.
 */
fun main(args: Array<String>) {
    val appServer = AppServer()

    appServer.get("/", {response.send("Hello world!")})

    val properties = Properties()
    properties.load(appServer.javaClass.getResourceAsStream("/app.properties"))

    val filterKeys = properties.filterKeys { (it as String).startsWith("app.") } .mapKeys { (it.key as String).removePrefix("app.") }

    println(
            filterKeys
    )

//    appServer.start()
}