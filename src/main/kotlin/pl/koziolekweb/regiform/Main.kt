package pl.koziolekweb.regiform

import io.netty.handler.codec.http.HttpMethod
import org.wasabi.app.AppServer
import org.wasabi.http.Request
import org.wasabi.http.Response
import org.wasabi.interceptors.Interceptor
import org.wasabi.interceptors.enableContentNegotiation
import org.wasabi.routing.RouteHandler
import java.io.File

/**
 * Created by BKuczynski on 2016-06-28.
 */
fun main(args: Array<String>) {
    val appServer = AppServer()


    appServer.enableContentNegotiation()

    val resource = "".javaClass.getResource("/public")

    appServer.serveStaticFiles(resource.path)

    appServer.post("/register", register())

    appServer.start()
}

private fun hw(): RouteHandler.() -> Unit = { response.send("Hello world!") }
private fun register(): RouteHandler.() -> Unit = {

    println(
            request.bodyParams
    )
}

