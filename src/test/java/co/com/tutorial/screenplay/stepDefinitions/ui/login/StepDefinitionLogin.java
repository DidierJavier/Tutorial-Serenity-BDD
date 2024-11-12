package co.com.tutorial.screenplay.stepDefinitions.ui.login;

import co.com.tutorial.screenplay.exceptions.TestFailure;
import co.com.tutorial.screenplay.questions.orangeLogin.PageTitleOnboardingOrange;
import co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin.NavigateAndLoginOn;
import co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin.NavigateTo;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import org.hamcrest.Matchers;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class StepDefinitionLogin {

    @Dado("que el {actor} esta en la pagina de inicio de sesion")
    public void queElUsuarioEstaEnLaPaginaDeInicioDeSesion(Actor userActor) {
        NavigateTo.orange().performAs(userActor);
    }

    @Cuando("el {actor} ingresa con credenciales validas")
    public void elUsuarioIngresaConCredencialesValidas(Actor userActor) {
        NavigateAndLoginOn.orangeHRM().performAs(userActor);
    }

    @Entonces("el {actor} deberia ver la pagina principal del sistema")
    public void elUsuarioDeberiaVerLaPaginaPrincipalDelSistema(Actor userActor) {
        userActor.should(
                seeThat(
                        "El titulo de la pagina",
                        PageTitleOnboardingOrange.displayed(), Matchers.equalTo("OrangeHRM"))
                        .orComplainWith(TestFailure.class, "El titulo de la pagina es diferente a OrangeHRM"));
    }
}
