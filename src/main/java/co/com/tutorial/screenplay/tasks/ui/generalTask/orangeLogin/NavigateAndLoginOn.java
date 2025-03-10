package co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NavigateAndLoginOn implements Task {
    private static final Logger LOGGER = LoggerFactory.getLogger(NavigateAndLoginOn.class);

    public static NavigateAndLoginOn orangeHRM() {
        return new NavigateAndLoginOn();
    }


    @Override
    public <T extends Actor> void performAs(T actor) {
        LoginOnOrange.withCredentials(
                        actor.recall(KeyToRemember.ORANGE_USER_NAME.name()),
                        actor.recall(KeyToRemember.ORANGE_USER_PASSWORD.name()))
                .performAs(actor);
        if(LOGGER.isInfoEnabled())
            LOGGER.info("User access correctly in Orange page");
    }
}
