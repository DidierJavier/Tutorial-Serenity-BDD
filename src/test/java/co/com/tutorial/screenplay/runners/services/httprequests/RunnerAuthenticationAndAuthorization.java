package co.com.tutorial.screenplay.runners.services.httprequests;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/service/httprequests/AuthenticationAndAuthorization.feature",
        glue = {"co.com.tutorial.screenplay.stepDefinitions",
                "co.com.tutorial.screenplay.hooks"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        plugin = {"pretty"},
        tags = "@FeatureName:AuthenticationAndAuthorization"
)
public class RunnerAuthenticationAndAuthorization {
}
