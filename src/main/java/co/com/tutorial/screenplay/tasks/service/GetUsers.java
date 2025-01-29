package co.com.tutorial.screenplay.tasks.service;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.rest.interactions.Get;

public class GetUsers implements Task {
    private final String numberPage;

    public GetUsers(String numberPage) {
        this.numberPage = numberPage;
    }

    public static GetUsers list(String numberPage) {
        return new GetUsers(numberPage);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Get.resource(actor.recall(KeyToRemember.PATH_USER_LIST.name()) + numberPage)
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)).performAs(actor);
    }
}
