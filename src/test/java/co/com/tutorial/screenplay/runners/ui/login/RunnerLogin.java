package co.com.tutorial.screenplay.runners.ui.login;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/ui/login/login.feature",
        glue = {"co.com.tutorial.screenplay.stepDefinitions",
                "co.com.tutorial.screenplay.hooks"},
        snippets = CucumberOptions.SnippetType.CAMELCASE,
        plugin = {"pretty"},
        tags = "@FeatureName:Login"
)

public class RunnerLogin {
}
