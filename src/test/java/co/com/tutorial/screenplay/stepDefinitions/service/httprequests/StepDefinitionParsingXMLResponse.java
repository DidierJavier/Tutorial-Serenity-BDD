package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import groovy.xml.XmlSlurper;
import groovy.xml.slurpersupport.GPathResult;
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
import org.junit.Assert;


// Important!
import java.io.StringReader;
import java.util.*;

import static com.ibm.icu.impl.ValidIdentifiers.Datatype.currency;
import static net.serenitybdd.rest.SerenityRest.given;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.*;

public class StepDefinitionParsingXMLResponse {

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
}
