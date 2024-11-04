package co.com.tutorial.screenplay.tasks.ui.generalTask;

import co.com.tutorial.screenplay.exceptions.TestFailure;
import co.com.tutorial.screenplay.userInterfaces.OrangeLoginPage;
import net.bytebuddy.implementation.bytecode.Throw;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.Switch;

import java.time.Duration;

public class LoginOnOrange implements Task {
    private final String email;
    private final String password;

    public LoginOnOrange(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginOnOrange withCredencials(String email, String password) {
        return new LoginOnOrange(email, password);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Switch.toDefaultContext().performAs(actor);

        if (!OrangeLoginPage.USER_NAME.isVisibleFor(actor))
            throw new TestFailure("El campo para ingresar el nombre del usuario en la pagina de Orange no es visible");
        Enter.theValue(email).into(OrangeLoginPage.USER_NAME.waitingForNoMoreThan(Duration.ofSeconds(10))).performAs(actor);
        Enter.theValue(password).into(OrangeLoginPage.USER_PASSWORD.waitingForNoMoreThan(Duration.ofSeconds(10))).performAs(actor);
    }
}
