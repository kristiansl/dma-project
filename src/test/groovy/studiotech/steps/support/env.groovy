package studiotech.steps.support

import cucumber.runtime.ScenarioImpl
import cucumber.api.groovy.Hooks
import groovy.util.logging.Slf4j
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import studiotech.dsl.DmaDSL
import studiotech.environment.TestEnvironment
import studiotech.pages.DmaBasePage

this.metaClass.mixin(Hooks)

@Slf4j
class DMAWorld {
    final WebDriver driver
    final TestEnvironment testEnvironment
    final DmaDSL dmaDSL
    static String buildNumber

    DMAWorld() {
        testEnvironment = new TestEnvironment()
        driver = testEnvironment.loadEnvironment()
        // set up the base URL location
        DmaBasePage.baseUrl = testEnvironment.baseUrl
        // init the DSL
        dmaDSL = new DmaDSL(testEnvironment)
    }

//    String retrieveDMABuildNumber() {
//        String pageSource = driver?.getPageSource()
//        String contentWithBuildNumber = pageSource.split("Build Number: ")[1]
//        buildNumber = contentWithBuildNumber.split(/\n/)[0]
//
//        if (! (buildNumber ==~ /(\d+\.)?\d+\.\d+/) ) {
//            return "Undefined: could not locate a build number!!"
//        }
//
//        return buildNumber
//    }
}

World {
    new DMAWorld()
}

Before() { ScenarioImpl scenario ->
    // load the base URL into the browser
    driver.get(testEnvironment.baseUrl)
    // record the build number
//    if (buildNumber == null) {
//        println "DMA BUILD NUMBER = " + retrieveDMABuildNumber()
//    }

    testEnvironment.scenario = scenario
}

After() { ScenarioImpl scenario ->
    try {
        if (scenario.failed && testEnvironment.isScreenShotSupported()) {
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES)
            scenario.embed(screenshot, "image/png")
        }

        if (testEnvironment.remoteHub.contains("saucelabs")) {
            def message = "${testEnvironment.sauceOnDemandSessionAndJob}"
            println "$message"
            log.info("$message")
        }
    } finally {
        driver.quit()
    }
}
