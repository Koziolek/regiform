package pl.koziolekweb.regiform

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.netty.handler.codec.http.DefaultHttpRequest
import io.netty.handler.codec.http.HttpHeaders
import io.netty.handler.codec.http.HttpMethod
import org.fest.assertions.Assertions.assertThat
import org.jetbrains.spek.api.Spek
import org.wasabi.http.Request
import org.wasabi.http.Response

/**
 * Created by BKuczynski on 2016-06-28.
 */
class CustomStaticFileInterceptorTest() : Spek({

    describe("When we have") {

        given("Custom static interceptor that has default configuration") {

            val res = "".javaClass.getResource("/interceptors")
            val sut = CustomStaticFileInterceptor(res.path)

            it("GET / -> return true") {
                val httpRequest: DefaultHttpRequest = mock()
                whenever(httpRequest.method).thenReturn(HttpMethod.GET)
                whenever(httpRequest.uri).thenReturn("/")
                whenever(httpRequest.headers()).thenReturn(HttpHeaders.EMPTY_HEADERS)

                val response: Response = Response()
                val request = Request(httpRequest)
                val intercept = sut.intercept(request, response)

                assertThat(intercept).isTrue();
            }

            it("GET index html -> return false") {
                val httpRequest: DefaultHttpRequest = mock()
                whenever(httpRequest.method).thenReturn(HttpMethod.GET)
                whenever(httpRequest.uri).thenReturn("/index.html")
                whenever(httpRequest.headers()).thenReturn(HttpHeaders.EMPTY_HEADERS)

                val response: Response = Response()
                val request = Request(httpRequest)
                val intercept = sut.intercept(request, response)

                assertThat(intercept).isFalse();
            }

        }

        given("Custom static interceptor that has extra configuration") {

            val res = "".javaClass.getResource("/interceptors")
            val sut = CustomStaticFileInterceptor(res.path, true)

            it("GET / -> return false") {
                val httpRequest: DefaultHttpRequest = mock()
                whenever(httpRequest.method).thenReturn(HttpMethod.GET)
                whenever(httpRequest.uri).thenReturn("/")
                whenever(httpRequest.headers()).thenReturn(HttpHeaders.EMPTY_HEADERS)

                val response: Response = Response()
                val request = Request(httpRequest)
                val intercept = sut.intercept(request, response)

                assertThat(intercept).isFalse();
                assertThat(response.contentType).isEqualTo("text/html");

            }

            it("GET index html -> return false") {
                val httpRequest: DefaultHttpRequest = mock()
                whenever(httpRequest.method).thenReturn(HttpMethod.GET)
                whenever(httpRequest.uri).thenReturn("/index.html")
                whenever(httpRequest.headers()).thenReturn(HttpHeaders.EMPTY_HEADERS)

                val response: Response = Response()
                val request = Request(httpRequest)
                val intercept = sut.intercept(request, response)

                assertThat(intercept).isFalse();
                assertThat(response.contentType).isEqualTo("text/html");
            }

        }
    }

})