package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.tasks.service.GetDataToCreate;
import co.com.tutorial.screenplay.tasks.service.GetUsers;
import co.com.tutorial.screenplay.utils.Constants;
import co.com.tutorial.screenplay.utils.KeyToRemember;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import net.serenitybdd.screenplay.rest.interactions.Post;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;

public class StepDefinitionHttpRequest {
    private static final Logger logger = LoggerFactory.getLogger(StepDefinitionHttpRequest.class);

    //region Obtener usuarios de forma exitosa
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

        theActorInTheSpotlight().should(seeThatResponse(
                        "Se han obtenido todos los usuarios de la pagina " + pageNumber,
                        validatableResponse -> {
                            logger.info("Received status code: {}", validatableResponse.extract().statusCode());
                            List<Integer> actualIds = validatableResponse.extract().body().jsonPath()
                                    .getList("data.id");
                            logger.info("Actual user IDs: {}", actualIds);

                            validatableResponse
                                    .statusCode(200)
                                    .body("data.id", equalTo(Constants.ReqresInService
                                            .ID_USERS_PER_PAGE))
                                    .log().all();
                        }
                )
        );
        logger.info("Verification completed for page number: {}", pageNumber);
    }
    //endregion Obtener usuarios de forma exitosa

    //region Crear un nuevo usuario exitosamente
    @Dado("que tengo un usuario con datos validos para crear")
    public void queTengoUnUsuarioConDatosValidosParaCrear() {
        GetDataToCreate.user().performAs(theActorInTheSpotlight());
    }

    @Cuando("envio la peticion para crear un usuario")
    public void envioLaPeticionParaCrearUnUsuario() {
        HashMap<String, String> dataToCreateUser = theActorInTheSpotlight()
                .recall(KeyToRemember.DATA_TO_CREATE_USER.name());
        Post.to(theActorInTheSpotlight().recall(KeyToRemember.PATH_CREATE_USER.name()))
                .with(request -> request
                        .contentType(ContentType.JSON)
                        .body(dataToCreateUser)
                ).performAs(theActorInTheSpotlight());
    }

    @Entonces("el usuario se crea correctamente")
    public void elUsuarioSeCreaCorrectamente() {
        logger.info("Starting the verification for user created");

        theActorInTheSpotlight().should(seeThatResponse("Se ha creado el usuario correctamente",
                        validatableResponse -> {
                            logger.info("Received status code: {}", validatableResponse.extract().statusCode());
                            String userNameCreated = validatableResponse.extract().body().jsonPath().get("name");
                            logger.info("User name created: {}", userNameCreated);
                            String userJobCreated = validatableResponse.extract().body().jsonPath().get("job");
                            logger.info("User job created: {}", userJobCreated);
                            String userIdCreated = validatableResponse.extract().body().jsonPath().get("id");
                            logger.info("User id created: {}", userIdCreated);
                            String userJobCreatedAt = validatableResponse.extract().body().jsonPath().get("createdAt");
                            logger.info("User job createdAt: {}", userJobCreatedAt);
                            validatableResponse
                                    .statusCode(201)
                                    .body("name", equalTo("Didi"))
                                    .body("job", equalTo("QA"))
                                    .log().all();
                        }
                )
        );
        logger.info("Verification completed for user created");
    }
    //endregion Crear un nuevo usuario exitosamente

    //region Actualizar un nuevo usuario exitosamente
    @Dado("que se ha creado un usuario")
    public void queSeHaCreadoUnUsuario() {
        GetDataToCreate.user().performAs(theActorInTheSpotlight());
        HashMap<String, String> dataToCreateUser = theActorInTheSpotlight()
                .recall(KeyToRemember.DATA_TO_CREATE_USER.name());
        Post.to(theActorInTheSpotlight().recall(KeyToRemember.PATH_CREATE_USER.name()))
                .with(request -> request
                        .contentType(ContentType.JSON)
                        .body(dataToCreateUser)
                        .log().all()
                ).performAs(theActorInTheSpotlight());
        theActorInTheSpotlight().should(
                seeThatResponse(validatableResponse -> {
                    String userIdCreated = validatableResponse.extract().body().jsonPath().get("id");
                    logger.info("User id created: {}", userIdCreated);
                    theActorInTheSpotlight().remember(KeyToRemember.USER_ID.name(), userIdCreated);
                })
        );
    }

    @Cuando("envio la peticion para actualizar el usuario creado")
    public void envioLaPeticionParaActualizarElUsuarioCreado() {
        HashMap<String, String> dataToUpdateUser = new HashMap<>();
        dataToUpdateUser.put("name", "Javi");
        dataToUpdateUser.put("job", "Automatizador");
        theActorInTheSpotlight().remember(KeyToRemember.DATA_TO_UPDATE_USER.name(), dataToUpdateUser);
        String userId = theActorInTheSpotlight().recall(KeyToRemember.USER_ID.name());
        Put.to(theActorInTheSpotlight().recall(KeyToRemember.PATH_CREATE_USER.name()) + userId)
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .body(dataToUpdateUser)
                        .log().all())
                .performAs(theActorInTheSpotlight());
    }

    @Entonces("el usuario se actualiza correctamente")
    public void elUsuarioSeActualizaCorrectamente() {
        logger.info("Starting the verification for user created");

        theActorInTheSpotlight().should(seeThatResponse("Se ha actualizado el usuario correctamente",
                        validatableResponse -> {
                            logger.info("Received status code: {}", validatableResponse.extract().statusCode());
                            String userNameUpdated = validatableResponse.extract().body().jsonPath().get("name");
                            logger.info("User name updated: {}", userNameUpdated);
                            String userJobUpdated = validatableResponse.extract().body().jsonPath().get("job");
                            logger.info("User job updated: {}", userJobUpdated);
                            String userJobUpdatedAt = validatableResponse.extract().body().jsonPath().get("updatedAt");
                            logger.info("User job updatedAt: {}", userJobUpdatedAt);

                            validatableResponse
                                    .statusCode(200)
                                    .body("name", equalTo("Javi"))
                                    .body("job", equalTo("Automatizador"))
                                    .log().all();
                        }
                )
        );
        logger.info("Verification completed for user updated");
    }
    //endregion Actualizar un nuevo usuario exitosamente

    //region Eliminar usuario
    @Cuando("envio la peticion para eliminar el usuario creado")
    public void envioLaPeticionParaEliminarElUsuarioCreado() {
        String userId = theActorInTheSpotlight().recall(KeyToRemember.USER_ID.name());
        Delete.from(theActorInTheSpotlight().recall(KeyToRemember.PATH_CREATE_USER.name()) + userId)
                        .with(requestSpecification -> requestSpecification
                                .contentType(ContentType.JSON)
                                .log().all())
                                .performAs(theActorInTheSpotlight());
    }

    @Entonces("el usuario se elimina correctamente")
    public void elUsuarioSeEliminaCorrectamente() {
        logger.info("Starting the verification for user deleted");

        theActorInTheSpotlight().should(seeThatResponse("Se ha eliminado el usuario correctamente",
                        validatableResponse -> {
                            logger.info("Received status code: {}", validatableResponse.extract().statusCode());

                            validatableResponse
                                    .statusCode(204)
                                    .log().all();
                        }
                )
        );
        logger.info("Verification completed for user updated");
    }
    //endregion Eliminar usuario
}
