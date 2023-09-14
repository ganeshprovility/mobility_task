package apiutil

import com.google.gson.Gson
import state.ScenarioData
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

private const val DeveloperKey = "057e903f-48b3-4461-b9fc-39d14ca829ce"
private const val DeveloperSecret = "35a425ad968fbe5e4ec8364e8e500420"
private const val TenantId = 205
private const val SsgUrl = "https://qa-ssge-ticketing.staging.unwire.com"

private val Client = HttpClient.newBuilder().build()
private val Gson = Gson().newBuilder().create()

class AuthenticationService(private val state: ScenarioData) : APIBase(state) {

    val ssgSigner = SSGSigner(tenantId = TenantId, devKey = DeveloperKey, devSecret = DeveloperSecret, ssgCredentials = null)


    fun getAppInstance(): AppInstance {

        // Creating the Auth request
        val authRequest = ssgSigner.authRequest()
        val request = HttpRequest
            .newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(Gson.toJson(authRequest.body)))
            .uri(URI.create("${SsgUrl}/api-gateway/v2/appinstance"))
            .headers(*authRequest.headers.toHeaders())
            .build()
        val response = Client.send(request, HttpResponse.BodyHandlers.ofString())
        val authResponse = Gson.fromJson(response.body(), AuthResponse::class.java)
        ssgSigner.auth(authResponse.appInstance)
        return authResponse.appInstance;
    }

    fun callApi(state: ScenarioData): HttpResponse<String> {
        println(state);
        val headers = ssgSigner.sign(
            path = "/api-gateway/v6/api/ttools/stops",
            body = null,
            method = "GET",
            queryParams = mapOf(
                "lat" to "32.78132139711093",
                "lng" to "-96.7982304096222",
                "radius" to "1000"
            )
        )

        val catalogReq = HttpRequest
            .newBuilder()
            .GET()
            .uri(URI.create("${SsgUrl}/api-gateway/v6/api/ttools/stops?lat=32.78132139711093&lng=-96.7982304096222&radius=1000"))
            .headers(*headers.toHeaders())
            .build()

        return Client.send(catalogReq, HttpResponse.BodyHandlers.ofString())
    }

    private fun Map<String, Any>.toHeaders(): Array<String> =
        flatMap { listOf(it.key, it.value.toString()) }.toTypedArray()
}
