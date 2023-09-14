package service

import apiutil.APIBase
import apiutil.AuthenticationService
import com.google.gson.Gson
import state.ScenarioData
import java.net.http.HttpClient
import java.net.http.HttpResponse
import kotlin.random.Random
import kotlin.random.nextInt

private const val DeveloperKey = "057e903f-48b3-4461-b9fc-39d14ca829ce"
private const val DeveloperSecret = "35a425ad968fbe5e4ec8364e8e500420"
private const val TenantId = 205
private const val SsgUrl = "https://qa-ssge-ticketing.staging.unwire.com"

private val Client = HttpClient.newBuilder().build()
private val Gson = Gson().newBuilder().create()

class EventService(private val authenticationService: AuthenticationService, private val state: ScenarioData) : APIBase(state) {

    private val endPointUrl = "https://stoplight.io/mocks/unwire/utils/13508558"

    private val allEventsUrl = "$endPointUrl/v1/api/utils/admin/events"

    fun providerId(): Int = Random.nextInt(0..10)
    fun page(): Int = Random.nextInt(0..10)
    fun size(): Int = Random.nextInt(0..100)

    fun getAllEvents(): HttpResponse<String> = authenticationService.callApi(state)

    // fun createEvent(body: EventCreation): Response = apiPOST(allEventsUrl, body)

}