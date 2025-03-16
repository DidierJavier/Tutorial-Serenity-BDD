package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.exceptions.TestFailure;
import co.com.tutorial.screenplay.models.service.waysToCreateBodyRequest.DataProductsUser;
import co.com.tutorial.screenplay.models.service.waysToCreateBodyRequest.Product;
import co.com.tutorial.screenplay.utils.KeyToRemember;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers;
import io.restassured.module.jsv.JsonSchemaValidator;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.hamcrest.MatcherAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class StepDefinitionJsonAndXmlSchemaSerializationAndDeserialization {
    Logger LOGGER = LoggerFactory.getLogger(StepDefinitionJsonAndXmlSchemaSerializationAndDeserialization.class);

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

    //region Convertir POJO to JSON
    @Dado("que el {actor} tiene un POJO creado")
    public void queElTesterTieneUnPOJOCreado(Actor tester) {
        // Crear la lista de productos
        List<Product> products = Arrays.asList(
                new Product(1, 2),
                new Product(2, 1),
                new Product(3, 4)
        );
        // Crear el objeto DataProductsUser
        DataProductsUser dataProductsUser = new DataProductsUser(5, products);

        // Imprimir el objeto DataProductsUser
        LOGGER.info(dataProductsUser.toString());

        tester.whoCan(CallAnApi.at("https://dummyjson.com"));
        tester.remember(KeyToRemember.DATA_PRODUCTS_USER.name(), dataProductsUser);
    }

    @Cuando("el {actor} convierte el POJO a JSON")
    public void elTesterConvierteElPOJOAJSON(Actor tester) {
        DataProductsUser dataProductsUser = tester.recall(KeyToRemember.DATA_PRODUCTS_USER.name());

        //Convert POJO to JSON -- Serialization
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String jsonData = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataProductsUser);
            tester.remember("JSON_DATA", jsonData);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Entonces("entonces el {actor} del POJO obtiene un JSON")
    public void entoncesElTesterDelPOJOObtieneUnJSON(Actor tester) {
        String expectedJson = """
                {\r
                  "userId" : 5,\r
                  "products" : [ {\r
                    "id" : 1,\r
                    "quantity" : 2\r
                  }, {\r
                    "id" : 2,\r
                    "quantity" : 1\r
                  }, {\r
                    "id" : 3,\r
                    "quantity" : 4\r
                  } ]\r
                }""";
        MatcherAssert.assertThat("El JSON obtenido es igual al esperado", tester.recall("JSON_DATA"),
                equalTo(expectedJson));
    }
    //endregion Convertir POJO to JSON

    //region Convertir JSON to POJO
    @Dado("que el {actor} tiene un JSON creado")
    public void queElTesterTieneUnJSONCreado(Actor tester) {
        String jsonData = """
                {\r
                  "userId" : 5,\r
                  "products" : [ {\r
                    "id" : 1,\r
                    "quantity" : 2\r
                  }, {\r
                    "id" : 2,\r
                    "quantity" : 1\r
                  }, {\r
                    "id" : 3,\r
                    "quantity" : 4\r
                  } ]\r
                }""";
        tester.remember("JSON_DATA", jsonData);
    }

    @Cuando("el {actor} convierte el JSON a POJO")
    public void elTesterConvierteElJSONAPOJO(Actor tester) {

        //Convert JSON to POJO --    De - Serialization
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonData = tester.recall("JSON_DATA");
        try {
            DataProductsUser dataProductsUser = objectMapper.readValue(jsonData, DataProductsUser.class);
            tester.remember("DATA_PRODUCTS_USER", dataProductsUser);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Entonces("el {actor} del JSON obtiene un POJO")
    public void elTesterDelJSONObtieneUnPOJO(Actor tester) {
        DataProductsUser dataProductsUser =  tester.recall("DATA_PRODUCTS_USER");

        // Crear la lista de productos
        List<Product> expectedProducts = Arrays.asList(
                new Product(1, 2),
                new Product(2, 1),
                new Product(3, 4));

        // Crear el objeto DataProductsUser - expectedDataProductsUser Reference memory different that dataProductsUser
        //To compare both Objects you need "equals" and "hashcode" methods
        DataProductsUser expectedDataProductsUser = new DataProductsUser(5, expectedProducts);

        MatcherAssert.assertThat("El POJO obtenido es igual al esperado",
                dataProductsUser, equalTo(expectedDataProductsUser));
        MatcherAssert.assertThat("Los productos son iguales",
                dataProductsUser.getProducts(), equalTo(expectedDataProductsUser.getProducts()));
        for (int index = 0; index < dataProductsUser.getProducts().size(); index++) {
            MatcherAssert.assertThat("Cada producto es igual",
                    dataProductsUser.getProducts().get(index),
                    equalTo(expectedDataProductsUser.getProducts().get(index)));
        }
        MatcherAssert.assertThat("El POJO obtenido es igual al esperado",
                dataProductsUser.getProducts().get(1).getId(),
                equalTo(expectedDataProductsUser.getProducts().get(1).getId()));
    }
    //endregion Convertir JSON to POJO
}
