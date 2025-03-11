package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class StepDefinitionParsingResponseBody {

    //region Verificar el valor del segundo nombre de un Pokemon en estado salvaje
    @Dado("que el {actor} cuenta con el recurso para encontrar un Pokemon en estado salvaje")
    public void queElTesterCuentaConElRecursoParaEncontrarUnPokemonEnEstadoSalvaje(Actor tester) {
        tester.whoCan(CallAnApi.at("https://pokeapi.co"));
    }

    @Cuando("el {actor} hace la peticion para obtener el Pokemon en estado salvaje")
    public void elTesterHaceLaPeticionParaObtenerElPokemonEnEstadoSalvaje(Actor tester) {
        Get.resource("/api/v2/encounter-condition/1")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .log().all())
                .performAs(tester);
    }

    @Entonces("el {actor} observa que el valor del segundo nombre del Pokemon en estado salvaje coincide con el valor esperado")
    public void elTesterObservaQueElValorDelSegundoNombreDelPokemonEnEstadoSalvajeCoincideConElValorEsperado(Actor tester) {
        String bodyResponse = SerenityRest.lastResponse().getBody().asString();
        tester.should(seeThatResponse(
                        "Se han obtenido las cookies del sitio web",
                        validatableResponse -> validatableResponse
                                .statusCode(200)
                                .header("Content-Type", "application/json; charset=utf-8").and()
                                .body("names[1].language.name", equalTo("de"))
                                .log().all()
                )
        );
    }
    //endregion Verificar el valor del segundo nombre de un Pokemon en estado salvaje
}
