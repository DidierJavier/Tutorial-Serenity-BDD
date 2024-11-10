package co.com.tutorial.screenplay.interactions.orangeLogin;

import co.com.tutorial.screenplay.userInterfaces.orangeLogin.OrangeLoginPage;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.actions.Enter;

import java.time.Duration;

public class EnterUserPassword implements Interaction {

    private final String userPassword;

    public EnterUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Step("{0} enter OrangeHRM user password with #userPassword")
    @Override
    public <T extends Actor> void performAs(T actor) {
        Enter.theValue(userPassword).into(OrangeLoginPage.USER_PASSWORD.waitingForNoMoreThan(Duration.ofSeconds(10)))
                .performAs(actor);
    }
}
