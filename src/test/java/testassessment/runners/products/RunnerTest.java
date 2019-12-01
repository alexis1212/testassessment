package testassessment.runners.products;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/reports/test-report", "json:target/reports/json/test-report.json"},
        features = {"classpath:features/"},
        glue = {"testassessment.glue"}
)
public class RunnerTest {
}

