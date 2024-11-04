package co.com.tutorial.screenplay.hooks;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import io.cucumber.java.Before;
import net.serenitybdd.model.environment.EnvironmentSpecificConfiguration;
import net.thucydides.model.environment.SystemEnvironmentVariables;
import net.thucydides.model.util.EnvironmentVariables;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;


public class UserInterfaceHook {
    // region Environment variables
    private final EnvironmentVariables environmentVariables = SystemEnvironmentVariables.createEnvironmentVariables();

    private final String orangeUrl = EnvironmentSpecificConfiguration.from(environmentVariables)
            .getProperty("orange.url");
    // endregion Environment variables

    //region @FeatureName:LoginOrange
    @Before("@FeatureName:LoginOrange")
    public void setEnvironmentVariables() {
        String userNameOrange = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("orange.ui.userName.userManagement");
        String userPasswordOrange = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("orange.ui.password.userManagement");
        theActorInTheSpotlight().remember(KeyToRemember.ORANGE_URL.name(), orangeUrl);
        theActorInTheSpotlight().remember(KeyToRemember.ORANGE_USER_NAME.name(), userNameOrange);
        theActorInTheSpotlight().remember(KeyToRemember.ORANGE_USER_PASSWORD.name(), userPasswordOrange);
    }
    //endregion @FeatureName:LoginOrange

}
