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

    private final String reqresInUrlBase = EnvironmentSpecificConfiguration.from(environmentVariables)
            .getProperty("reqres.in.url");
    // endregion Environment variables

    //region UI
    //region @FeatureName:LoginOrange
    @Before("@FeatureName:LoginOrange")
    public void setEnvironmentVariablesLoginOrange() {
        String userNameOrange = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("orange.ui.userName.userManagement");
        String userPasswordOrange = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("orange.ui.password.userManagement");
        theActorInTheSpotlight().remember(KeyToRemember.ORANGE_URL.name(), orangeUrl);
        theActorInTheSpotlight().remember(KeyToRemember.ORANGE_USER_NAME.name(), userNameOrange);
        theActorInTheSpotlight().remember(KeyToRemember.ORANGE_USER_PASSWORD.name(), userPasswordOrange);
    }
    //endregion @FeatureName:LoginOrange
    //endregion UI

    //region Services
    //region @FeatureName:HttpRequests
    @Before("@FeatureName:HttpRequests")
    public void setEnvironmentVariablesService() {
        String pathUserList = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("path.user.list");
        String pathCreateUser = EnvironmentSpecificConfiguration.from(environmentVariables)
                .getProperty("path.create.user");
        theActorInTheSpotlight().remember(KeyToRemember.REQRES_IN_URL_BASE.name(), reqresInUrlBase);
        theActorInTheSpotlight().remember(KeyToRemember.PATH_USER_LIST.name(), pathUserList);
        theActorInTheSpotlight().remember(KeyToRemember.PATH_CREATE_USER.name(), pathCreateUser);
    }
    //endregion @FeatureName:HttpRequests
    //endregion Services
}
