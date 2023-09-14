package steps

import apiutil.AppInstance
import apiutil.AuthenticationService
import io.cucumber.java.BeforeAll
import service.EventService
import state.ScenarioData

private val globalState = ScenarioData()
private val privateAuthenticationService = AuthenticationService(globalState)
private val privateEventService = EventService(privateAuthenticationService, globalState)


@BeforeAll
fun beforeAll() {
    val res  = getAppInstance()
    globalState.text = "test"
    globalState.appInstance = res
}

fun getAppInstance(): AppInstance = privateAuthenticationService.getAppInstance()


