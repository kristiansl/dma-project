package studiotech

import cucumber.api.junit.Cucumber
import org.junit.runner.RunWith
import cucumber.api.CucumberOptions

@RunWith(Cucumber.class)
@CucumberOptions(
        features = ["classpath:features/discover/discover.feature:7"]
        , monochrome = true
        , format = ["pretty", "json:target/cucumber.json", "junit:target/cucumber.xml", "rerun:rerun.txt"]
//        , tags = ["~@manual"]
        , glue = ["src/test/groovy/studiotech/steps"]
)
class RunDmaITCase {
}
