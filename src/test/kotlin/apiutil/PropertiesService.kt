package apiutil

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.cucumber.core.exception.ExceptionUtils
import io.restassured.RestAssured
import io.restassured.config.ObjectMapperConfig
import io.restassured.config.RestAssuredConfig
import java.time.ZonedDateTime
import java.util.*

class PropertiesService {

    companion object {
        val properties = Properties()

        init {
            try {
                val profile = System.getenv("CUCUMBER_PROFILE") ?: "local"
                properties.setProperty("profile", profile.lowercase())
                val file = this::class.java.classLoader.getResourceAsStream("config-$profile.properties")
                properties.load(file)
                RestAssured.config = RestAssuredConfig.config()
                    .objectMapperConfig(ObjectMapperConfig().jackson2ObjectMapperFactory { _, _ ->
                        ObjectMapper().findAndRegisterModules().apply {
                            registerModule(JavaTimeModule())
                            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                            setSerializationInclusion(JsonInclude.Include.NON_NULL)

                            val zoneDateTimeModule = SimpleModule().apply {
                                addSerializer(ZonedDateTime::class.java, CustomZoneDateSerializer())
                            }
                            registerModule(zoneDateTimeModule)
                        }
                    })
            } catch (e: Exception) {
                ExceptionUtils.printStackTrace(e)
            }
        }
    }

    fun getProperties() = properties

    fun getProfile(): String = properties.getProperty("profile")

}
