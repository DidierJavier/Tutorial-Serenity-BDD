package co.com.tutorial.screenplay.tasks.service;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;

import java.util.HashMap;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;

public class GetDataToCreate implements Task {

    public static GetDataToCreate user() {
        return new GetDataToCreate();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        HashMap<String, String> dataToCreateUser = new HashMap<>();
        dataToCreateUser.put("name", "Didi");
        dataToCreateUser.put("job", "QA");
        actor.remember(KeyToRemember.DATA_TO_CREATE_USER.name(), dataToCreateUser);
        actor.whoCan(CallAnApi.at(theActorInTheSpotlight().recall(KeyToRemember.REQRES_IN_URL_BASE.name())));
    }
}
