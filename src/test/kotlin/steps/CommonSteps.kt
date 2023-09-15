package steps

import apiutil.AuthenticationService
import io.cucumber.java.Before
import io.cucumber.java.Scenario
import state.ScenarioData

class CommonSteps(
    private val state: ScenarioData,
    private val authenticationService: AuthenticationService

) {
    @Before
    fun setup(scenario: Scenario)
    {
        state.appInstance = authenticationService.getAppInstance()
    }


//	@After
//	fun teardown(scenario: Scenario) {
//		if (scenario.isFailed) {
//			println("Test Feature: " + scenario.uri + "\n")
//			println("Scenario Name: " + scenario.name + "\n")
//			println("Status code:" + state.apiResponse?.statusCode + "\n")
//			println("TraceId is " + state.apiResponse?.header("x-amzn-requestid").toString() + "\n")
//			state.apiResponse?.body()?.prettyPrint()
//		}
//	}

}
