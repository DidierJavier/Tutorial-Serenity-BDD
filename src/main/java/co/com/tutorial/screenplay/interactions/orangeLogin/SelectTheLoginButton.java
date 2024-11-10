package co.com.tutorial.screenplay.interactions.orangeLogin;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.targets.Target;

import java.time.Duration;


public class SelectTheLoginButton implements Interaction {

    private final Target target;

    public SelectTheLoginButton(Target target) {this.target = target;}

    @Step("{0} select the button to access to OrangeHRM #target")
    @Override
    public <T extends Actor> void performAs(T actor) {
        Click.on(this.target.waitingForNoMoreThan(Duration.ofSeconds(10))).performAs(actor);
    }
}
