package co.com.tutorial.screenplay.stepDefinitions.service.httprequests;

import co.com.tutorial.screenplay.utils.KeyToRemember;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.Matchers.equalTo;

public class StepDefinitionWaysToCreateBodyRequest {
    private static final Logger logger = LoggerFactory.getLogger(StepDefinitionHttpRequest.class);

    //region Realizar peticion Http con body request usando Hashmap
    @Dado("que existe un usuario con productos en el carrito de compras usando Hashmap")
    public void queExisteUnUsuarioConProductosEnElCarritoDeComprasUsandoHashmap() {
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

    @Cuando("el usuario hace la peticion de agregar los productos al carrito de compras usando Hashmap")
    public void elUsuarioHaceLaPeticionDeAgregarLosProductosAlCarritoDeComprasUsandoHashmap() {
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
                                .body("id", equalTo(51))
                                .body("products[0].id", equalTo(1))
                                .body("products[0].title", equalTo("Essence Mascara Lash Princess"))
                                .body("products[0].thumbnail", equalTo("https://cdn.dummyjson.com/products/images/beauty/Essence%20Mascara%20Lash%20Princess/thumbnail.png"))
                                .body("products[0].quantity", equalTo(2))

                                .body("products[1].id", equalTo(2))
                                .body("products[1].quantity", equalTo(1))

                                .body("products[2].id", equalTo(3))
                                .body("products[2].quantity", equalTo(4))

                                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                                .log().all()));
    }
    //endregion Realizar peticion Http con body request usando Hashmap

    //region Realizar peticion Http con body request usando org.json
    @Dado("que tengo un usuario con datos validos para crear usando org.json")
    public void queTengoUnUsuarioConDatosValidosParaCrearUsandoOrgJson() {
        // Crear el JSONObject principal
        JSONObject dataProductsUser = new JSONObject();

        // Agregar userId
        dataProductsUser.put("userId", 5);

        // Crear el JSONArray de productos
        JSONArray products = new JSONArray();

        // Agregar productos al JSONArray
        JSONObject product1 = new JSONObject();
        product1.put("id", 1);
        product1.put("quantity", 2);
        products.put(product1);

        JSONObject product2 = new JSONObject();
        product2.put("id", 2);
        product2.put("quantity", 1);
        products.put(product2);

        JSONObject product3 = new JSONObject();
        product3.put("id", 3);
        product3.put("quantity", 4);
        products.put(product3);

        // Agregar el JSONArray de productos al JSONObject principal
        dataProductsUser.put("products", products);

        // Imprimir el JSONObject
        logger.info(dataProductsUser.toString());

        theActorInTheSpotlight().whoCan(CallAnApi.at("https://dummyjson.com"));
        //actor.remember(KeyToRemember.DATA_PRODUCTS_USER.name(), dataProductsUser);
        theActorInTheSpotlight().remember(KeyToRemember.DATA_PRODUCTS_USER.name(), dataProductsUser); //Asumiendo que keytoremember es un string.
    }

    @Cuando("envio la peticion para crear un usuario usando org.json")
    public void envioLaPeticionParaCrearUnUsuarioUsandoOrgJson() {
        JSONObject dataProductsUser = theActorInTheSpotlight().recall(KeyToRemember.DATA_PRODUCTS_USER.name());
        logger.info(dataProductsUser.toString());
        String jsonBody = dataProductsUser.toString();
        Post.to("/carts/add")
                .with(requestSpecification -> requestSpecification
                        .contentType(ContentType.JSON)
                        .body(jsonBody)
                        .log().all())
                .performAs(theActorInTheSpotlight());
    }

    @Entonces("el usuario se crea correctamente usando org.json")
    public void elUsuarioSeCreaCorrectamenteUsandoOrgJson() {
        theActorInTheSpotlight().should(seeThatResponse("Se ha actualizado el usuario correctamente",
                validatableResponse -> {
                    Response response = validatableResponse.extract().response();
                    JSONObject jsonResponse = new JSONObject(response.getBody().asString());

                    validatableResponse
                            .statusCode(201)
                            .header("Content-Type", Matchers.equalTo("application/json; charset=utf-8"))
                            .log().all();

                    // Aserciones para el ID principal
                    validatableResponse.body("id", Matchers.equalTo(51));

                    // Aserciones para los productos
                    JSONArray products = jsonResponse.getJSONArray("products");

                    // Producto 1
                    JSONObject product1 = products.getJSONObject(0);
                    validatableResponse.body("products[0].id", Matchers.equalTo(product1.get("id")));
                    validatableResponse.body("products[0].title", Matchers.equalTo(product1.getString("title")));
                    validatableResponse.body("products[0].thumbnail", Matchers.equalTo(product1.getString("thumbnail")));
                    validatableResponse.body("products[0].quantity", Matchers.equalTo(product1.getInt("quantity")));

                    // Producto 2
                    JSONObject product2 = products.getJSONObject(1);
                    validatableResponse.body("products[1].id", Matchers.equalTo(product2.getInt("id")));
                    validatableResponse.body("products[1].quantity", Matchers.equalTo(product2.getInt("quantity")));

                    // Producto 3
                    JSONObject product3 = products.getJSONObject(2);
                    validatableResponse.body("products[2].id", Matchers.equalTo(product3.getInt("id")));
                    validatableResponse.body("products[2].quantity", Matchers.equalTo(product3.getInt("quantity")));
                }));
    }
    //endregion Realizar peticion Http con body request usando org.json
}
