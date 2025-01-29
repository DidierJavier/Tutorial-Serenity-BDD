package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.tasks.service.GetUsers;
import co.com.tutorial.screenplay.utils.Constants;
import co.com.tutorial.screenplay.utils.KeyToRemember;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;

public class StepDefinitionHttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(StepDefinitionHttpRequest.class);

    @Dado("que el tester cuenta con el recurso para hacer la peticion http")
    public void queElTesterCuentaConElRecursoParaHacerLaPeticionHttp() {
        theActorInTheSpotlight()
                .whoCan(CallAnApi.at(theActorInTheSpotlight().recall(KeyToRemember.REQRES_IN_URL_BASE.name())));
    }

    @Cuando("el tester envia la peticion para obtener los usuarios de la pagina {businessParameter}")
    public void elTesterEnviaLaPeticionParaObtenerLosUsuariosDeLaPaginaNumeroDePagina(String pageNumber) {
        GetUsers.list(pageNumber).performAs(theActorInTheSpotlight());
    }

    @Entonces("se obtiene la lista de usuarios de la pagina {businessParameter} correctamente")
    public void seObtieneLaListaDeUsuariosDeLaPaginaNumeroDePaginaCorrectamente(String pageNumber) {
        logger.info("Starting the verification for page number: {}", pageNumber);

        theActorInTheSpotlight().should(
                seeThatResponse("Se han obtenido todos los usuarios de la pagina " + pageNumber,
                        validatableResponse -> {
                            int statusCode = validatableResponse.extract().statusCode();
                            logger.info("Received status code: {}", statusCode);

                            List<Integer> actualIds = validatableResponse.extract().body().jsonPath()
                                    .getList("data.id");
                            logger.info("Actual user IDs: {}", actualIds);

                            theActorInTheSpotlight().should(
                                    seeThatResponse("Se han obtenido todos los usuarios de la pagina " +
                                                    pageNumber,
                                            validatableResponseAssertion -> validatableResponse
                                                    .statusCode(200)
                                                    .body("data.id", contains(Constants.ReqresInService
                                                            .ID_USERS_PER_PAGE.toArray()))
                                    )
                            );
                        }
                )
        );

        logger.info("Verification completed for page number: {}", pageNumber);
    }
}
