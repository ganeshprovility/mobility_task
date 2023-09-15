package steps

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import org.junit.Assert
import service.EventService
import state.ScenarioData
import java.util.*


class EventServiceSteps(private val state: ScenarioData, private val eventService: EventService) {

    @Given("Get the event details based on id,page & size")
    fun we_get_the_event_details_based_on_id_page_size() {
        val apiResponse = eventService.getAllEvents()
        state.apiResponse = apiResponse
    }


    @Then("Response status code should  be {int}")
    fun response_status_code_should_be(code: Int?) {
        Assert.assertEquals(code, state.apiResponse?.statusCode())
    }

    @Then("Print the response")
    fun i_print_the_response() {
        // state.apiResponse?.body?.prettyPrint()
    }

    @Given("Admin should provides details to create an event")
    fun `create an event`() {
//        eventService.eventCreation(NewEventCreation())
    }


    @Then("Response should contain id field")
    fun response_should_contain_id_field() {

    }

}