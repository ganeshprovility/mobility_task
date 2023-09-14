package steps

import io.cucumber.java.PendingException
import io.cucumber.java.en.Given
import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.junit.Assert
import service.EventService
import state.ScenarioData
import java.util.*


class EventServiceSteps(private val state: ScenarioData, private val eventService: EventService) {

    @Given("We get the event details based on id,page & size")
    fun we_get_the_event_details_based_on_id_page_size() {
        val res = eventService.getAllEvents()
        state.apiResponse = res
        //  state.apiResponse = getAllEvents(providerId = 1, page = 1, size = 10)
    }

    @Then("We should get the status Code {int}")
    fun we_should_get_the_status_code(code: Int?) {
        Assert.assertEquals(code,state.apiResponse?.statusCode())
    }

    @Then("I print the response")
    fun i_print_the_response() {
       // state.apiResponse?.body?.prettyPrint()
    }

    @Given("admin provides details to create an event")
    fun admin_provides_details_to_create_an_event() {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @When("user send {string} request")
    fun user_send_request(string: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }

    @Then("Validate {string} value present in {string} field")
    fun validate_value_present_in_field(string: String?, string2: String?) {
        // Write code here that turns the phrase above into concrete actions
        throw PendingException()
    }
}