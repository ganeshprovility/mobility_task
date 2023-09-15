package steps

import apiutil.AuthenticationService
import apiutil.PropertiesService
import io.cucumber.java.BeforeAll
import state.ScenarioData

private val state = ScenarioData()
private val privatePropertiesService = PropertiesService()
private val privateAuthenticationService = AuthenticationService(state, privatePropertiesService)


@BeforeAll
fun beforeAll() {
//    val appInstance  = privateAuthenticationService.getAppInstance()
//    state.appInstance = appInstance

}



