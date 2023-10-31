package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "pretty",
                "html:target/default-cucumber-reports.html",
                "json:target/json-reports/cucumber.json",
                "junit:target/xml-report/cucumber.xml",
                "rerun:target/failedRerun.txt"
        },
        features = "src/test/resources/feature",
        glue = {"stepDefinition","hooks"},
        tags = "@Register",
        dryRun = false
)
public class Runner {

    //TODO: write a Comment
    //https://collectapi.com/tr/api/gasPrice/gas-prices-api
    // girip subscribe yapılcak daha sonrasında hesap bölümünden api key alınacak

}
