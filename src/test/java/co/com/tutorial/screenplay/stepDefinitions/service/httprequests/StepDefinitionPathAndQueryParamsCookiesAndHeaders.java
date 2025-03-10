package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;

public class StepDefinitionPathAndQueryParamsCookiesAndHeaders {

    private static final Logger LOGGER = LoggerFactory.getLogger(StepDefinitionPathAndQueryParamsCookiesAndHeaders.class);

    @Dado("que el tester cuenta con el recurso para hacer la peticion http con parametros")
    public void queElTesterCuentaConElRecursoParaHacerLaPeticionHttpConParametros() {
        LOGGER.info("URL Base se llama desde el hook");
        LOGGER.info(theActorInTheSpotlight().recall(KeyToRemember.REQRES_IN_URL_BASE.name()));
    }

    @Cuando("el tester envia la peticion para obtener los usuarios con path {businessParameter} con pagina {int} y con id {int}")
    public void elTesterEnviaLaPeticionParaObtenerLosUsuariosConPathConPaginaYConId(String path, Integer page,
                                                                                    Integer id) {
        String urlBase = theActorInTheSpotlight().recall(KeyToRemember.REQRES_IN_URL_BASE.name());
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
}
