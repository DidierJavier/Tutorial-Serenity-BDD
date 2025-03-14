package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.exceptions.TestFailure;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class StepDefinitionParsingResponseBody {

    Logger LOGGER = LoggerFactory.getLogger(StepDefinitionParsingResponseBody.class);

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
        tester.should(seeThatResponse(
                        "Los datos obtenidos son correctos",
                        validatableResponse -> validatableResponse
                                .statusCode(200)
                                .header("Content-Type", "application/json; charset=utf-8").and()
                                .body("names[1].language.name", equalTo("de"))
                                .log().all())
                .orComplainWith(TestFailure.class, "Error en las aserciones")
        );
    }
    //endregion Verificar el valor del segundo nombre de un Pokemon en estado salvaje

    //region Verificar la respuesta a una peticion con Response de rest-azure
    @Entonces("el tester observa que los valores esperados son iguales a los del Response de rest-azure")
    public void elTesterObservaQueLosValoresEsperadosSonIgualesALosDelResponseDeRestAzure() {
        Response response = SerenityRest.lastResponse();

        int statusCode = response.getStatusCode();
        MatcherAssert.assertThat("El codigo de estado es 200", statusCode, Matchers.is(200));
        LOGGER.info("Status code was = {}", statusCode);

        String languageName = response.jsonPath().getString("names[0].language.name");
        MatcherAssert.assertThat("El lenguaje es frances", languageName, Matchers.is("fr"));
        LOGGER.info("The language was = {}", languageName);
    }
    //endregion Verificar la respuesta a una peticion con Response de rest-azure

    //region Verificar la respuesta a una peticion con Response y JSONObject class
    @Entonces("el tester observa que los valores esperados son iguales a los del Response usando JSONObject class")
    public void elTesterObservaQueLosValoresEsperadosSonIgualesALosDelResponseUsandoJSONObjectClass() {
        List<String> expectedLanguages = List.of("fr", "de", "en");
        JSONObject jsonObject = new JSONObject(SerenityRest.lastResponse().getBody().asString());

        // Check if "names" exists and is an array
        if (!jsonObject.has("names") || jsonObject.isNull("names")) {
            throw new RuntimeException("Error: 'names' field is missing or null.");
        }

        JSONArray namesArray = jsonObject.getJSONArray("names");

        // List to store language names
        List<String> languagesResponse = new ArrayList<>();

        // Loop through the array to get languages
        for (int i = 0; i < namesArray.length(); i++) {
            JSONObject nameEntry = namesArray.getJSONObject(i); // ✅ Correct usage of index
            if (!nameEntry.has("language") || nameEntry.isNull("language")) {
                continue; // Skip if language key is missing
            }
            JSONObject languageObject = nameEntry.getJSONObject("language"); // ✅ Correct extraction

            if (!languageObject.has("name") || languageObject.isNull("name")) {
                continue; // Skip if name key is missing
            }

            String languageName = languageObject.getString("name"); // ✅ Now it's correctly extracted
            languagesResponse.add(languageName);
        }

        // Print languages for debugging
        MatcherAssert.assertThat(
                "La lista de lenguajes esperados son iguales a la lista de lenguajes de la respuesta",
                languagesResponse, Matchers.is(expectedLanguages));
        LOGGER.info("The language was iqual = {}", languagesResponse);
    }
    //endregion Verificar la respuesta a una peticion con Response y JSONObject class
}
