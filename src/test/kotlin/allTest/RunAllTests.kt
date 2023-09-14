package allTest

import io.cucumber.junit.Cucumber
import io.cucumber.junit.CucumberOptions
import org.junit.runner.RunWith


/*
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features")
@ConfigurationParameter(key = "PLUGIN_PROPERTY_NAME", value = "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:")
*/

@RunWith(Cucumber::class)
@CucumberOptions(features = ["Features/"], monochrome = true, glue = ["steps"], dryRun = false,
    plugin = ["pretty","com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"])
class RunAllTests


