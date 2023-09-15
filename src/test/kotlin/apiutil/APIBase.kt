package apiutil

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.JsonSerializer
import com.fasterxml.jackson.databind.SerializerProvider
import io.restassured.http.ContentType
import io.restassured.http.Method
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import io.restassured.response.Response
import state.ScenarioData
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoUnit

open class APIBase(val state: ScenarioData, propertiesService: PropertiesService) {


    var response: Response? = null

    fun apiPOST(
        endPoint: String,
        body: Any? = null,
        queryParams: Map<String, String> = emptyMap(),
        customHeaders: Map<String?, String?> = emptyMap()
    ): Response =
        callApi(Method.POST, endPoint, queryParams, body)

    fun apiPUT(
        endPoint: String,
        body: Any? = null,
        queryParams: Map<String, String> = emptyMap(),
        customHeaders: Map<String?, String?> = emptyMap()
    ): Response =
        callApi(Method.PUT, endPoint, queryParams, body)

    fun apiPATCH(
        endPoint: String,
        body: Any? = null,
        queryParams: Map<String, String> = emptyMap(),
        customHeaders: Map<String?, String?> = emptyMap()
    ): Response =
        callApi(Method.PATCH, endPoint, queryParams, body)

    fun apiGET(
        endPoint: String,
        queryParams: Map<String, String> = emptyMap(),
        customHeaders: Map<String?, String?> = emptyMap()
    ): Response =
        callApi(Method.GET, endPoint, queryParams, null)

    fun apiDELETE(
        endPoint: String,
        queryParams: Map<String, String> = emptyMap(),
        customHeaders: Map<String?, String?> = emptyMap()
    ): Response =
        callApi(Method.DELETE, endPoint, queryParams, null)

    private fun callApi(
        method: Method,
        endPoint: String,
        queryParams: Map<String, String> = emptyMap(),
        body: Any? = null,
        customHeaders: Map<String?, String?> = emptyMap()
    ): Response {
        return Given {

            queryParams.forEach { queryParam(it.key, it.value) }
            body?.let { body(it) }
            contentType(ContentType.JSON)
        } When {
            request(method, endPoint)
        } Then {
            // Leaving Then blank for now - this block can be used for logging in case of error or validate response time else throw error
        } Extract {
            response()
        }
    }
}

class CustomZoneDateSerializer : JsonSerializer<ZonedDateTime?>() {

    override fun serialize(value: ZonedDateTime?, gen: JsonGenerator, serializers: SerializerProvider?) {
        try {
            val ZONED_DATE_TIME = DateTimeFormatter.ISO_ZONED_DATE_TIME
            val utcZonedDateTime = value?.withZoneSameInstant(ZoneOffset.UTC)
            val s = ZONED_DATE_TIME.format(utcZonedDateTime?.truncatedTo(ChronoUnit.SECONDS))
            gen.writeString(s)
        } catch (e: DateTimeParseException) {
            System.err.println(e)
            gen.writeString("")
        }
    }
}