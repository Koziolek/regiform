package pl.koziolekweb.regiform

import io.netty.handler.codec.http.HttpMethod
import org.wasabi.app.AppServer
import org.wasabi.http.Request
import org.wasabi.http.Response
import org.wasabi.interceptors.Interceptor
import java.io.File

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

fun AppServer.serveStaticFiles(folder: String, useDefaultFile: Boolean = false, defaultFile: String = "index.html") {
    val staticInterceptor = CustomStaticFileInterceptor(folder, useDefaultFile, defaultFile)
    this.intercept(staticInterceptor)
}