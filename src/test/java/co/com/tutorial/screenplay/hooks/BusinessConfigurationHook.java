package co.com.tutorial.screenplay.hooks;

import io.cucumber.java.Before;
import io.cucumber.java.ParameterType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;

public class BusinessConfigurationHook {
    public Actor actorOnlineCast;

    @Before(order = 0)
    public void setupActor() {
        OnStage.setTheStage(new OnlineCast());
        actorOnlineCast = theActorCalled("HookActor");
    }
    @ParameterType(".*")
    public Actor actor(String actor) {
        actorOnlineCast.assignName(actor);
        return actorOnlineCast;
    }
    @ParameterType(".*")
    public String businessParameter(String parameter) {
        return parameter;
    }
}
