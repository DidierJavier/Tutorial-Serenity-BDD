package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.exceptions.TestFailure;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import net.serenitybdd.screenplay.rest.questions.ResponseConsequence;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class StepDefinitionJsonAndXmlSchemaSerializationAndDeserialization {

    //region Validacion con Json schema
    @Dado("que el {actor} cuenta con el recurso para hacer la peticion http y el archivo json schema")
    public void queElTesterCuentaConElRecursoParaHacerLaPeticionHttpYElArchivoJsonSchema(Actor tester) {
        tester.whoCan(CallAnApi.at("https://pokeapi.co"));
    }

    @Cuando("el {actor} envia la peticion para comparar la respuesta con Json schema")
    public void elTesterEnviaLaPeticionParaCompararLaRespuestaConJsonSchema(Actor tester) {
        Get.resource("/api/v2/encounter-condition/1")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .log().all())
                .performAs(tester);
    }

    @Entonces("al comparar la respuesta de la peticion con el Json schema es correcta")
    public void alCompararLaRespuestaDeLaPeticionConElJsonSchemaEsCorrecta() {
        theActorInTheSpotlight().should(ResponseConsequence.seeThatResponse( // Use ResponseConsequence for better readability
                        "Los datos obtenidos son correctos",
                        validatableResponse -> validatableResponse
                                .statusCode(200)
                                .header("Content-Type", "application/json; charset=utf-8")
                                .body("names[1].language.name", equalTo("de"))
                                .and()
                                .assertThat().body(matchesJsonSchemaInClasspath("file.json"))
                                .log().all())
                .orComplainWith(TestFailure.class, "Error en las aserciones")
        );

        theActorInTheSpotlight().should(seeThatResponse(
                "Los datos obtenidos son correctos",
                validatableResponse -> validatableResponse
                        .statusCode(200)
                        .header("Content-Type", "application/json; charset=utf-8").and()
                        .body("names[1].language.name", equalTo("de"))
                        .and()
                        .assertThat().body(JsonSchemaValidator.matchesJsonSchemaInClasspath(
                                "service/files/schema/jsonSchemaValidator.json"))
                        .log().all())
                .orComplainWith(TestFailure.class, "Error en las aserciones")
        );
    }
    //endregion Validacion con Json schema
}
