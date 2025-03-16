package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.exceptions.TestFailure;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

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

    //region Validacion de respuesta en XSL con XSD schema
    @Dado("que el {actor} cuenta con el recurso para hacer la peticion http y el archivo xsd schema")
    public void queElTesterCuentaConElRecursoParaHacerLaPeticionHttpYElArchivoXsdSchema(Actor tester) {
        tester.whoCan(CallAnApi.at("https://mocktarget.apigee.net"));
    }

    @Cuando("el {actor} envia la peticion para comparar la respuesta con xsd schema")
    public void elTesterEnviaLaPeticionParaCompararLaRespuestaConXsdSchema(Actor tester) {
        Get.resource("/xml")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.XML)
                        .log().all())
                .performAs(tester);
    }

    @Entonces("al comparar la respuesta XSL de la peticion con el XSD schema es correcta")
    public void alCompararLaRespuestaXSLDeLaPeticionConElXSDSchemaEsCorrecta() {
        theActorInTheSpotlight().should(seeThatResponse(
                "Los datos obtenidos son correctos",
                validatableResponse -> validatableResponse
                        .log().all()
                        .statusCode(200)
                        .header("Content-Type", "application/xml; charset=utf-8").and()
                        .and()
                        .assertThat().body(RestAssuredMatchers.matchesXsdInClasspath(
                                "service/files/schema/xmlSchemaValidator.xsd"))
                        .log().all())
                .orComplainWith(TestFailure.class, "Error en las aserciones")
        );
    }
    //endregion Validacion de respuesta en XSL con XSD schema
}
