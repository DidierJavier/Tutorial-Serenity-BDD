package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.path.xml.XmlPath;
import io.restassured.path.xml.config.XmlPathConfig;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.hamcrest.MatcherAssert;


// Important!
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;

public class StepDefinitionParsingXMLResponseUploadAndDownloadFile {

    //region Verificar la respuesta correcta de una peticion desde un valor en el body XML
    @Dado("que el {actor} cuenta con el recurso para obtener informacion XML")
    public void queElTesterCuentaConElRecursoParaObtenerInformacionXML(Actor tester) {
        tester.whoCan(CallAnApi.at("https://www.ecb.europa.eu"));
    }

    @Cuando("el {actor} hace la peticion para obtener la informacion en formato XML")
    public void elTesterHaceLaPeticionParaObtenerLaInformacionEnFormatoXML(Actor tester) {
        Get.resource("/stats/eurofxref/eurofxref-daily.xml")
                .with(requestSpecification -> requestSpecification
                        .contentType("application/xml")
                        .log().all())
                .performAs(tester);
    }

    @Entonces("el {actor} observa que la respuesta XML es correcta")
    public void elTesterObservaQueLaRespuestaXMLEsCorrecta(Actor tester) {
        // Obtener la respuesta XML
        String xmlResponse = SerenityRest.lastResponse().getBody().asString();

        // Crear XmlPath con namespaces
        XmlPath xmlPath = new XmlPath(xmlResponse)
                .using(new XmlPathConfig()
                        .declaredNamespace("gesmes", "http://www.gesmes.org/xml/2002-08-01")
                        .declaredNamespace("eurofxref",
                                "http://www.ecb.int/vocabulary/2002-08-01/eurofxref"));

        // Validar la respuesta
        tester.should(seeThatResponse("Se han obtenido los datos esperados del usuario",
                response -> response
                        .statusCode(200)
                        .header("Content-Type", containsString("text/xml"))
                        .log().all()
        ));

        // Validar los valores XML con XmlPath
        MatcherAssert.assertThat("Se espera el valor Reference rates",
                xmlPath.getString("gesmes:Envelope.gesmes:subject"),
                equalTo("Reference rates"));
        MatcherAssert.assertThat(xmlPath.getString("gesmes:Envelope.gesmes:Sender.gesmes:name"),
                equalTo("European Central Bank"));
        MatcherAssert.assertThat(xmlPath.getString("gesmes:Envelope.eurofxref:Cube.eurofxref:Cube.@time"),
                equalTo("2025-03-14"));
        MatcherAssert.assertThat(xmlPath
                        .getString("gesmes:Envelope.eurofxref:Cube.eurofxref:Cube.eurofxref:Cube[1].@currency"),
                equalTo("JPY"));
        MatcherAssert.assertThat(xmlPath
                        .getString("gesmes:Envelope.eurofxref:Cube.eurofxref:Cube.eurofxref:Cube[1].@rate"),
                equalTo("161.88"));
        MatcherAssert.assertThat(xmlPath
                        .getString("gesmes:Envelope.eurofxref:Cube.eurofxref:Cube.eurofxref:Cube[10].@currency"),
                equalTo("CHF"));
        MatcherAssert.assertThat(xmlPath
                        .getString("gesmes:Envelope.eurofxref:Cube.eurofxref:Cube.eurofxref:Cube[10].@rate"),
                equalTo("0.9641"));

    }
    //endregion Verificar la respuesta correcta de una peticion desde un valor en el body XML

    //region Verificar la respuesta correcta de una peticion desde un valor en el body XML con XMLObject
    @Entonces("el {actor} observa que la respuesta XML es correcta al usar XMLObject")
    public void elTesterObservaQueLaRespuestaXMLEsCorrectaAlUsarXMLObject(Actor tester) {
        // Obtener la respuesta XML
        String xmlResponse = SerenityRest.lastResponse().getBody().asString();

        // Validar la respuesta
        tester.should(seeThatResponse("Se han obtenido los datos esperados del usuario",
                response -> response
                        .statusCode(200)
                        .header("Content-Type", containsString("text/xml"))
                        .log().all()
        ));

        // Crear XmlPath con namespaces
        XmlPath xmlPath = new XmlPath(xmlResponse)
                .using(new XmlPathConfig()
                        .declaredNamespace("gesmes", "http://www.gesmes.org/xml/2002-08-01")
                        .declaredNamespace("eurofxref",
                                "http://www.ecb.int/vocabulary/2002-08-01/eurofxref"));

        // Obtener todas las monedas y tasas como listas separadas
        List<String> currencies = xmlPath.getList("gesmes:Envelope.eurofxref:Cube.eurofxref:Cube.eurofxref:Cube.@currency");
        List<String> rates = xmlPath.getList("gesmes:Envelope.eurofxref:Cube.eurofxref:Cube.eurofxref:Cube.@rate");

// Crear lista de mapas con las monedas y tasas
        List<Map<String, String>> currencyRates = new ArrayList<>();

        for (int i = 0; i < currencies.size(); i++) {
            Map<String, String> entry = new HashMap<>();
            entry.put("currency", currencies.get(i));
            entry.put("rate", rates.get(i));
            currencyRates.add(entry);
        }
        MatcherAssert.assertThat(currencyRates.size(), equalTo(30));

        for (Map<String, String> entry : currencyRates) {
            MatcherAssert.assertThat(entry, is(notNullValue()));
        }

        for (Map<String, String> entry : currencyRates) {
            MatcherAssert.assertThat("Currency should not be null", entry.get("currency"), is(notNullValue()));
            MatcherAssert.assertThat("Rate should not be null", entry.get("rate"), is(notNullValue()));
        }

        MatcherAssert.assertThat(currencyRates, everyItem(is(notNullValue())));
    }
    //endregion Verificar la respuesta correcta de una peticion desde un valor en el body XML con XMLObject

    //region Cargar archivo y validar la respuesta
    @Entonces("el tester observa que el archivo se carga de forma correcta")
    public void elTesterObservaQueElArchivoSeCargaDeFormaCorrecta() {
        final String UPLOAD_URL = "https://reqres.in/api/upload";
        final String FILE_PATH = "src/test/resources/files/json/waysToCreateBodyRequest/dataProductsUser.json";

        File file = new File(FILE_PATH);

        Response response = SerenityRest.given()
                .multiPart("file", file)  // Attach file
                .when()
                .post(UPLOAD_URL)
                .then()
                .log().all()
                .extract().response();

        // Assertions
        MatcherAssert.assertThat(response.statusCode(), is(201));
        MatcherAssert.assertThat(response.jsonPath().getString("id"), is(notNullValue()));
        MatcherAssert.assertThat(response.jsonPath().getString("createdAt"), is(notNullValue()));

        // Validate that id is a number
        MatcherAssert.assertThat("The id should be a number", response.jsonPath().getInt("id"), greaterThan(0));

        // Validate that createdAt is a valid date in ISO 8601 format
        String createdAt = response.jsonPath().getString("createdAt");
        MatcherAssert.assertThat("The createdAt should be a valid date", isValidDate(createdAt));

        // If you want to ensure the format of createdAt (ISO 8601)
        MatcherAssert.assertThat("createdAt should be in ISO 8601 format", createdAt, matchesPattern("\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}\\.\\d{3}Z"));
    }

    // Helper method to validate if a date string is in ISO 8601 format
    private boolean isValidDate(String dateStr) {
        try {
            DateTimeFormatter.ISO_DATE_TIME.parse(dateStr);  // Try parsing to ISO format
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    //endregion Cargar archivo y validar la respuesta

    //region Descargar archivo y validar la respuesta
    @Entonces("el tester observa que el archivo se descarga de forma correcta")
    public void elTesterObservaQueElArchivoSeDescargaDeFormaCorrecta() {
        final String DOWNLOAD_URL = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";
        final String DOWNLOAD_PATH = "C:/Tutorial-Serenity-BDD/src/test/resources/service.files.download/downloaded_dummy.pdf";

        Response response = SerenityRest.given()
                .when()
                .get(DOWNLOAD_URL)
                .then()
                .log().all()
                .extract().response();

        // Save the file
        try (FileOutputStream fos = new FileOutputStream(new File(DOWNLOAD_PATH))) {
            fos.write(response.asByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Assertions
        File file = new File(DOWNLOAD_PATH);
        MatcherAssert.assertThat(file.exists(), is(true));
        MatcherAssert.assertThat(file.length(), greaterThan(0L)); // Ensure file is not empty
    }
    //endregion Descargar archivo y validar la respuesta
}
