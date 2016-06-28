package pl.koziolekweb.regiform

import org.wasabi.app.AppServer
import java.util.*

/**
 * Created by BKuczynski on 2016-06-28.
 */
fun main(args: Array<String>) {
    val appServer = AppServer()

    appServer.get("/", {response.send("Hello world!")})

    appServer.start()
}