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

    val resource = appServer.javaClass.getResource("/public")

    val staticInterceptor = CustomStaticFileInterceptor(resource.path, true)
    appServer.intercept(staticInterceptor)
//    appServer.serveStaticFilesFromFolder(resource.path)

    appServer.post("/register", register())

    appServer.start()
}

private fun hw(): RouteHandler.() -> Unit = { response.send("Hello world!") }
private fun register(): RouteHandler.() -> Unit = {

    println(
            request.bodyParams
    )
}

class CustomStaticFileInterceptor(val folder: String, val useDefaultFile: Boolean = false, val defaultFile: String = "index.html") : Interceptor() {

    private fun existingDir(path: String): Boolean {
        val file = File(path)
        return file.exists() && file.isDirectory
    }

    private fun existingFile(path: String): Boolean {
        val file = File(path)
        return file.exists() && file.isFile
    }

    override fun intercept(request: Request, response: Response): Boolean {
        return when (request.method) {
            HttpMethod.GET -> {
                val fullPath = "${folder}${request.uri}"
                when {
                    existingFile(fullPath) -> {
                        response.setFileResponseHeaders(fullPath); false
                    }
                    existingDir(fullPath) && useDefaultFile -> {
                        response.setFileResponseHeaders("${fullPath}/${defaultFile}"); false
                    }
                    else -> true
                }
            }
            else -> true
        }
    }
}

