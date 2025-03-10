package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.*;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StepDefinitionPathAndQueryParamsCookiesAndHeaders {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitionPathAndQueryParamsCookiesAndHeaders.class);

    //region Obtener informacion de usuarios empleando PATH y QUERY parameters
    @Dado("que el tester cuenta con el recurso para hacer la peticion http con parametros")
    public void queElTesterCuentaConElRecursoParaHacerLaPeticionHttpConParametros() {
        LOGGER.info("URL Base se llama desde el hook");
        LOGGER.info(theActorInTheSpotlight().recall(KeyToRemember.REQRES_IN_URL_BASE.name()));
    }

    @Cuando("el tester envia la peticion para obtener los usuarios con path {businessParameter} con pagina {int} y con id {int}")
    public void elTesterEnviaLaPeticionParaObtenerLosUsuariosConPathConPaginaYConId(String path, Integer page,
                                                                                    Integer id) {
        Get.resource(path)
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .queryParam("page", page.toString())
                        .queryParam("id", id.toString())
                        .log().all())
                .performAs(theActorInTheSpotlight());
    }

    @Entonces("se obtiene el usuario de la pagina {int} con id {int} correctamente")
    public void seObtieneElUsuarioDeLaPaginaConIdCorrectamente(Integer page, Integer id) {
        theActorInTheSpotlight().should(seeThatResponse(
                        "Se han obtenido los datos esperados del usuario de la pagina " + page + "con id " + id,
                        validatableResponse -> {
                            validatableResponse
                                    .statusCode(200)
                                    .body(
                                            "data.id", equalTo(id),
                                            "data.email", notNullValue(),
                                            "data.first_name", notNullValue(),
                                            "data.last_name", notNullValue(),
                                            "data.avatar", notNullValue(),
                                            "support.url", notNullValue(),
                                            "support.text", notNullValue()
                                    )
                                    .log().all();
                        }
                )
        );
    }
    //endregion Obtener informacion de usuarios empleando PATH y QUERY parameters

    //region Obtener cookies de un sitio web
    @Dado("que el {actor} tiene el recurso para realizar la peticion http")
    public void queElTesterTieneElRecursoParaRealizarLaPeticionHttp(Actor actor) {
        actor.whoCan(CallAnApi.at("https://www.google"));
    }

    @Cuando("el {actor} envia la peticion para obtener las cookies del sitio")
    public void elTesterEnviaLaPeticionParaObtenerLasCookiesDelSitio(Actor actor) {
        Get.resource(".com")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .log().all())
                .performAs(theActorInTheSpotlight());

        Cookies cookies = SerenityRest.lastResponse().getDetailedCookies();
        Map<String, String> cookiesMap = new HashMap<>();
        for (Cookie cookie : cookies) {
            cookiesMap.put(cookie.getName(), cookie.getValue());
        }
        actor.remember("cookiesMap", cookiesMap); // Guarda el mapa de cookies en la memoria del actor
    }

    @Entonces("el {actor} obtiene las cookies correctamente")
    public void elTesterObtieneLasCookiesCorrectamente(Actor actor) {
        Map<String, String> cookiesMap = actor.recall("cookiesMap");
        List<String> expectedCookiesKeys = List.of("NID", "AEC");
        List<String> obtainedCookiesKeys = new ArrayList<>(cookiesMap.keySet());
        LOGGER.info(obtainedCookiesKeys.toString());

        theActorInTheSpotlight().should(seeThatResponse(
                        "Se han obtenido las cookies del sitio web",
                        validatableResponse -> {
                            validatableResponse
                                    .statusCode(200)
                                    .cookies("AEC", notNullValue())
                                    .cookie("NID", notNullValue())
                                    .log().all();
                            // Validar que las llaves obtenidas coincidan con las esperadas
                            assertThat("Las llaves de las cookies coinciden con las esperadas",
                                    new HashSet<>(obtainedCookiesKeys), equalTo(new HashSet<>(expectedCookiesKeys)));
                        }
                )
        );
    }
    //endregion Obtener cookies de un sitio web

    //region Validar Headers de la respuesta de una peticion HTTP
    @Cuando("el {actor} envia la peticion para obtener los headers del sitio")
    public void elTesterEnviaLaPeticionParaObtenerLosHeadersDelSitio(Actor actor) {
        Get.resource(".com")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .log().all())
                .performAs(actor);

        LOGGER.info(SerenityRest.lastResponse().getHeader("Content-Type"));
        Headers headers = SerenityRest.lastResponse().headers();
        for (Header header : headers) {
            LOGGER.info("{} = {}", header.getName(), header.getValue());
        }
    }

    @Entonces("el {actor} valida que los headers sean correctos")
    public void elTesterValidaQueLosHeadersSeanCorrectos(Actor actor) {
        actor.should(seeThatResponse(
                        "Se han obtenido las cookies del sitio web",
                        validatableResponse -> {
                            validatableResponse
                                    .statusCode(200)
                                    .header("Content-Type", "text/html; charset=ISO-8859-1")
                                    .and()
                                    .header("Content-Encoding", "gzip")
                                    .and()
                                    .header("Server", "gws")
                                    .log().all();
                        }
                )
        );
    }
    //endregion Validar Headers de la respuesta de una peticion HTTP
}
