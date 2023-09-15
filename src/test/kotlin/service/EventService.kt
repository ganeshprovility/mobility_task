package service

import apiutil.APIBase
import apiutil.PropertiesService
import io.restassured.response.Response
import state.ScenarioData
import utlis.NewEventCreation
import java.net.http.HttpResponse
import kotlin.random.Random
import kotlin.random.nextInt


class EventService(state: ScenarioData, propertiesService: PropertiesService) : APIBase(state, propertiesService) {

    private val endPointUrl = "https://stoplight.io/mocks/unwire/utils/13508558"

    private val allEventsUrl = "$endPointUrl/v1/api/utils/admin/events"

    fun providerId(): Int = Random.nextInt(0..10)
    fun page(): Int = Random.nextInt(0..10)
    fun size(): Int = Random.nextInt(0..100)

    fun getAllEvents(): HttpResponse<String> {
        return callApi()
    }

    fun eventCreation(body: NewEventCreation): Response {
        return apiPOST(allEventsUrl, body);
    }

    // fun createEvent(body: EventCreation): Response = apiPOST(allEventsUrl, body)

}