package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class StepDefinitionWaysToCreateBodyRequest {
    private static final Logger logger = LoggerFactory.getLogger(StepDefinitionHttpRequest.class);

    @Dado("que existe un usuario con productos en el carrito de compras")
    public void queExisteUnUsuarioConProductosEnElCarritoDeCompras() {
        // Crear el HashMap principal
        Map<String, Object> dataProductsUser = new HashMap<>();

        // Agregar userId
        dataProductsUser.put("userId", 5);

        // Crear la lista de productos
        List<Map<String, Object>> products = new ArrayList<>();

        // Agregar productos a la lista
        products.add(Map.of("id", 1, "quantity", 2));
        products.add(Map.of("id", 2, "quantity", 1));
        products.add(Map.of("id", 3, "quantity", 4));

        // Agregar la lista de productos al HashMap principal
        dataProductsUser.put("products", products);
        // Imprimir el HashMap
        logger.info(dataProductsUser.toString());

        theActorInTheSpotlight().whoCan(CallAnApi.at("https://dummyjson.com"));
        theActorInTheSpotlight().remember(KeyToRemember.DATA_PRODUCTS_USER.name(), dataProductsUser);
    }

    @Cuando("el usuario hace la peticion de agregar los productos al carrito de compras")
    public void elUsuarioHaceLaPeticionDeAgregarLosProductosAlCarritoDeCompras() {
        Map<String, Object> dataProductsUser = theActorInTheSpotlight().recall(KeyToRemember.DATA_PRODUCTS_USER.name());
        Post.to("/carts/add")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .body(dataProductsUser)
                        .log().all())
                .performAs(theActorInTheSpotlight());
    }

    @Entonces("los productos se agregan al carrito de compras")
    public void losProductosSeAgreganAlCarritoDeCompras() {
        theActorInTheSpotlight().should(seeThatResponse("Se ha actualizado el usuario correctamente",
                        validatableResponse -> validatableResponse
                                .statusCode(201)
                                .log().all()));
    }
}
