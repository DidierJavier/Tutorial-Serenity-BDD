package co.com.tutorial.screenplay.tasks.ui.generalTask;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;


public class NavigateTo implements Task {
    private final String siteUrl;

    public NavigateTo(String keyCalledByActor) {
        this.siteUrl = keyCalledByActor;
    }

    public static NavigateTo orange() {
        return new NavigateTo(KeyToRemember.ORANGE_URL.name());
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Open.url(actor.recall(siteUrl)));
    }
}
