package co.com.tutorial.screenplay.stepDefinitions.ui.login;

import co.com.tutorial.screenplay.questions.PageTitleOnboardingOrange;
import co.com.tutorial.screenplay.tasks.ui.generalTask.LoginOnOrange;
import co.com.tutorial.screenplay.tasks.ui.generalTask.NavigateTo;
import co.com.tutorial.screenplay.userInterfaces.OrangeLoginPage;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actions.Click;
import org.hamcrest.Matchers;

import java.time.Duration;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;

public class StepDefinitionLogin {

    @Dado("que el {actor} esta en la pagina de inicio de sesion")
    public void queElUsuarioEstaEnLaPaginaDeInicioDeSesion(Actor userActor) {
        NavigateTo.orange().performAs(userActor);
    }

    @Cuando("el {actor} ingresa con credenciales validas")
    public void elUsuarioIngresaConCredencialesValidas(Actor userActor) {
        LoginOnOrange.withCredencials("Admin", "admin123").performAs(userActor);
        Click.on(OrangeLoginPage.LOGIN_BUTTON.waitingForNoMoreThan(Duration.ofSeconds(10))).performAs(userActor);
    }

    @Entonces("el {actor} deberia ver la pagina principal del sistema")
    public void elUsuarioDeberiaVerLaPaginaPrincipalDelSistema(Actor userActor) {
       userActor.
               should(
                       seeThat("El titulo de la pagina", PageTitleOnboardingOrange.displayed(), Matchers.equalTo("OrangeHRM"))
               );
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
