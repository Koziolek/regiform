package pl.koziolekweb.regiform

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.netty.handler.codec.http.HttpMethod
import io.netty.handler.codec.http.HttpRequest
import org.fest.assertions.Assertions
import org.jetbrains.spek.api.Spek
import org.wasabi.http.Request
import org.wasabi.http.Response

/**
 * Created by BKuczynski on 2016-06-28.
 */
class CustomStaticFileInterceptorTest() : Spek({

    describe("When we have") {

        given("Custom static interceptor that has default configuration") {

            val res = this.javaClass.getResource("/interceptor")
            val sut = CustomStaticFileInterceptor(res.path)

            it("GET -> return forward") {
                val httpRequest: HttpRequest = mock()
                whenever(httpRequest.method).thenReturn(HttpMethod.GET)
                whenever(httpRequest.uri).thenReturn("/")

                val response: Response = mock()
                val request = Request(httpRequest)
                val intercept = sut.intercept(request, response)

                Assertions.assertThat(intercept).isTrue();
            }

        }
    }

})