package co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;

public class NavigateAndLoginOn implements Task {

    public static NavigateAndLoginOn orangeHRM() {
        return new NavigateAndLoginOn();
    }


    @Override
    public <T extends Actor> void performAs(T actor) {
        LoginOnOrange.withCredentials(
                        actor.recall(KeyToRemember.ORANGE_USER_NAME.name()),
                        actor.recall(KeyToRemember.ORANGE_USER_PASSWORD.name()))
                .performAs(actor);
    }
}
