package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

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

    //region Bearer token Authentication
    @Dado("que el {actor} cuenta con el recurso para autenticarse con Bearer Token")
    public void queElTesterCuentaConElRecursoParaAutenticarseConBearerToken(Actor tester) {
        tester.whoCan(CallAnApi.at("https://api.github.com"));
    }

    @Cuando("el {actor} realiza la peticion de Bearer Token authentication")
    public void elTesterRealizaLaPeticionDeBearerTokenAuthentication(Actor tester) {
        String bearerTokenMyGitHub = "TOKEN_DE_GIT_HUB_VENCE_CADA_30_DIAS";//Token vence cada 30 días
        Get.resource("/user")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .headers("Authorization", "Bearer " + bearerTokenMyGitHub)
                        .log().all())
                .performAs(tester);
    }

    @Entonces("el {actor} se autentica correctamente con Bearer Token")
    public void elTesterSeAutenticaCorrectamenteConBearerToken(Actor tester) {
        Response response = SerenityRest.lastResponse();
        tester.should(seeThatResponse(
                ValidatableResponse -> ValidatableResponse
                        .statusCode(200)
                        .contentType("application/json; charset=utf-8")
                        .log().all()
        ));

        String planName = response.jsonPath().getString("plan.name");
        MatcherAssert.assertThat("El nombre del plan es", planName, Matchers.is("free"));

        String avatarUrl = response.jsonPath().getString("avatar_url");
        MatcherAssert.assertThat("La URL del Avatar es", avatarUrl,
                Matchers.is("https://avatars.githubusercontent.com/u/83479269?v=4"));
    }
    //endregion Bearer token Authentication

    //region OAut1 Authentication ------ Se requiere para la autenticaciòn "consumerKey", "consumerSecret", "accessToken", "tokenSecret"
    /*@Dado("que el tester cuenta con el recurso para autenticarse con OAut1")
    public void queElTesterCuentaConElRecursoParaAutenticarseConOAut1(Actor tester) {
        tester.whoCan(CallAnApi.at("RESOURCE_TO_CALL_AN_API"));
    }

    @Cuando("el {actor} realiza la peticion de OAut1 authentication")
    public void elTesterRealizaLaPeticionDeOAut1Authentication(Actor tester) {
        Get.resource("ENDPOINT_TO_GET_RESOURCE")
                .with(requestSpecification -> requestSpecification
                        .auth().oauth("consumerKey", "consumerSecret", "accessToken", "tokenSecret")//Data for OAuth1.0
                        .log().all())
                .performAs(tester);
    }

    @Entonces("el tester se autentica correctamente con OAut1")
    public void elTesterSeAutenticaCorrectamenteConOAut1(Actor tester) {
        tester.should(seeThatResponse(
                ValidatableResponse -> ValidatableResponse
                        .statusCode(200)
                        .contentType("application/json; charset=utf-8")
                        .log().all()
        ));

        //With RestAzure
        given()
                .auth().oauth("consumerKey", "consumerSecret", "accessToken", "tokenSecret")
                .when().get("URL")
                .then()
                .statusCode(200)
                .log().all();
    }*/
    //endregion OAut1 Authentication

    //region OAut2 Authentication   ----- Se requiere para la autenticaciòn TOKEN y URL
    /*@Dado("que el {actor} cuenta con el recurso para autenticarse con OAut2")
    public void queElTesterCuentaConElRecursoParaAutenticarseConOAut2(Actor tester) {
        tester.whoCan(CallAnApi.at("RESOURCE_TO_CALL_AN_API"));
    }

    @Cuando("el {actor} realiza la peticion de OAut2 authentication")
    public void elTesterRealizaLaPeticionDeOAut2Authentication(Actor tester) {
        Get.resource("ENDPOINT_TO_GET_RESOURCE")
                .with(requestSpecification -> requestSpecification
                        .auth().oauth2("TOKEN")//Data for OAuth2.0
                        .log().all())
                .performAs(tester);
    }

    @Entonces("el {actor} se autentica correctamente con OAut2")
    public void elTesterSeAutenticaCorrectamenteConOAut2(Actor tester) {
        tester.should(seeThatResponse(
                ValidatableResponse -> ValidatableResponse
                        .statusCode(200)
                        .log().all()
        ));

        //With RestAzure
        given()
                .auth().oauth2("TOKEN")
                .when().get("URL")
                .then()
                .statusCode(200)
                .log().all();
    }*/
    //endregion OAut2 Authentication

    //region API Key Authentication
    @Dado("que el {actor} cuenta con el recurso para autenticarse con API Key")
    public void queElTesterCuentaConElRecursoParaAutenticarseConAPIKey(Actor tester) {
        tester.whoCan(CallAnApi.at("https://api.openweathermap.org"));
    }

    @Cuando("el {actor} realiza la peticion de API Key authentication")
    public void elTesterRealizaLaPeticionDeAPIKeyAuthentication(Actor tester) {
        Get.resource("/data/2.5/weather/")
                .with(requestSpecification -> requestSpecification
                        .queryParam("lat", "44.34")
                        .queryParam("lon", "10.99")
                        .queryParam("appid", "ea78cc197e86a6057aec88ec39602071")
                        .log().all())
                .performAs(tester);
    }

    @Entonces("el {actor} se autentica correctamente con API Key")
    public void elTesterSeAutenticaCorrectamenteConAPIKey(Actor tester) {
        tester.should(seeThatResponse(
                ValidatableResponse -> ValidatableResponse
                        .statusCode(200)
                        .log().all()
        ));
    }
    //endregion API Key Authentication
}
