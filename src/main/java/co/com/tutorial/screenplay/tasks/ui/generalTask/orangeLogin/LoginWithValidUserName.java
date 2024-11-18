package co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import com.github.javafaker.Faker;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class LoginWithValidUserName implements Task {

    public static LoginWithValidUserName andInvalidPassword() {
        return new LoginWithValidUserName();
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Faker fakerPassword = new Faker();
        actor.forget(actor.recall(KeyToRemember.ORANGE_USER_PASSWORD.name()));
        actor.remember(KeyToRemember.ORANGE_USER_PASSWORD.name(), fakerPassword.internet().password(8, 16).toUpperCase());
        LoginOnOrange.withCredentials(
                        actor.recall(KeyToRemember.ORANGE_USER_NAME.name()),
                        actor.recall(KeyToRemember.ORANGE_USER_PASSWORD.name()))
                .performAs(actor);
    }
}
