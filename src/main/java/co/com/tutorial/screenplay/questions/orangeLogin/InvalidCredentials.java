package co.com.tutorial.screenplay.questions.orangeLogin;

import co.com.tutorial.screenplay.userInterfaces.orangeLogin.OrangeLoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

import java.time.Duration;

public class InvalidCredentials implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return
                OrangeLoginPage.INVALID_CREDENTIALS
                        .waitingForNoMoreThan(Duration.ofSeconds(5))
                        .resolveFor(actor).getText();
    }

    public static InvalidCredentials text() {
        return new InvalidCredentials();
    }
}
