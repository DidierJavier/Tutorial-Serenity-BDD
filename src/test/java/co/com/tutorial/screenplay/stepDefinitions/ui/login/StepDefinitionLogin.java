package co.com.tutorial.screenplay.stepDefinitions.ui.login;

import co.com.tutorial.screenplay.exceptions.TestFailure;
import co.com.tutorial.screenplay.questions.orangeLogin.InvalidCredentials;
import co.com.tutorial.screenplay.questions.orangeLogin.PageTitleOnboardingOrange;
import co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin.LoginWithValidUserName;
import co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin.NavigateAndLoginOn;
import co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin.NavigateTo;
import co.com.tutorial.screenplay.utils.Constants;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import net.serenitybdd.screenplay.Actor;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.openqa.selenium.TimeoutException;


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

    @Cuando("el {actor} ingresa el nombre de usuario correcto y la contrasena incorrecta")
    public void elUsuarioIngresaElNombreDeUsuarioCorrectoYLaContrasenaIncorrecta(Actor userActor) {
        LoginWithValidUserName.andInvalidPassword().performAs(userActor);
    }
    @Entonces("el {actor} no puede acceder a la pagina de OrangeHRM")
    public void elUsuarioNoPuedeAccederALaPaginaDeOrangeHRM(Actor userActor) {
        try {
            userActor.should(
                    seeThat(
                            "La alerta al ingresar la contrasena incorrecta, debe decir***"
                                    + Constants.OrangeLogin.INVALID_CREDENTIALS + "***",
                            InvalidCredentials.text(),
                            IsEqual.equalTo(Constants.OrangeLogin.INVALID_CREDENTIALS))
                            .orComplainWith(TestFailure.class, "La alerta no es igual a ***"
                                    + Constants.OrangeLogin.INVALID_CREDENTIALS)
            );
        } catch (TimeoutException e) {
            throw new TestFailure("No se visualiza la alerta " + Constants.OrangeLogin.INVALID_CREDENTIALS);
        }
    }
}
