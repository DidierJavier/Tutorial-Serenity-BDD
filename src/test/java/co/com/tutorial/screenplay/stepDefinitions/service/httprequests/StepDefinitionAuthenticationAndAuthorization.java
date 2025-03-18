package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class StepDefinitionAuthenticationAndAuthorization {

    //region Autenticacion basica
    @Dado("que el {actor} cuenta con el recurso para autenticarse")
    public void queElTesterCuentaConElRecursoParaAutenticarse(Actor tester) {
        tester.whoCan(CallAnApi.at("https://postman-echo.com"));
    }

    @Cuando("el {actor} realiza la peticion de autenticacion basica")
    public void elTesterRealizaLaPeticionDeAutenticacionBasica(Actor tester) {
        Get.resource("/basic-auth")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .auth().basic("postman", "password")
                        .log().all())
                .performAs(tester);
    }

    @Entonces("el {actor} se autentica correctamente")
    public void elTesterSeAutenticaCorrectamente(Actor tester) {
        tester.should(seeThatResponse(
                ValidatableResponse -> ValidatableResponse
                        .statusCode(200)
                        .body("authenticated", equalTo(Boolean.TRUE))
                        .log().all()
        ));
    }
    //endregion Autenticacion basica

    //region Digest authentication
    @Cuando("el {actor} realiza la peticion de Digest authentication")
    public void elTesterRealizaLaPeticionDeDigestAuthentication(Actor tester) {
        Get.resource("/basic-auth")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .auth().digest("postman", "password")
                        .log().all())
                .performAs(tester);
    }
    //endregion Digest authentication

    //region Preemptive authentication
    @Cuando("el {actor} realiza la peticion de Preemptive authentication")
    public void elTesterRealizaLaPeticionDePreemptiveAuthentication(Actor tester) {
        Get.resource("/basic-auth")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .auth().preemptive().basic("postman", "password")
                        .log().all())
                .performAs(tester);
    }
    //endregion Preemptive authentication
}
