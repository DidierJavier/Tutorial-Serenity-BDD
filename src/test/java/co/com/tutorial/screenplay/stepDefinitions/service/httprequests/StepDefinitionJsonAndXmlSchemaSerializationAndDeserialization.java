package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;

public class StepDefinitionJsonAndXmlSchemaSerializationAndDeserialization {

    //region Validacion con Json schema
    @Dado("que el {actor} cuenta con el recurso para hacer la peticion http y el archivo json schema")
    public void queElTesterCuentaConElRecursoParaHacerLaPeticionHttpYElArchivoJsonSchema(Actor tester) {

    }

    @Cuando("el {actor} envia la peticion para comparar la respuesta con Json schema")
    public void elTesterEnviaLaPeticionParaCompararLaRespuestaConJsonSchema(Actor tester) {

    }

    @Entonces("al comparar la respuesta de la peticion con el Json schema es correcta")
    public void alCompararLaRespuestaDeLaPeticionConElJsonSchemaEsCorrecta() {

    }
    //endregion Validacion con Json schema
}
