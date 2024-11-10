package co.com.tutorial.screenplay.interactions.orangeLogin;

import co.com.tutorial.screenplay.userInterfaces.orangeLogin.OrangeLoginPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Enter;

import java.time.Duration;

public class EnterUserName implements Interaction {

    private final String userName;

    public EnterUserName(String userName) {
        this.userName = userName;
    }

    @Step("{0} enter OrangeHRM user name with #userName")
    @Override
    public <T extends Actor> void performAs(T actor) {
        Enter.theValue(userName).into(OrangeLoginPage.USER_NAME.waitingForNoMoreThan(Duration.ofSeconds(10)))
                .performAs(actor);
    }
}
