package co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin;

import co.com.tutorial.screenplay.exceptions.TestFailure;
import co.com.tutorial.screenplay.interactions.orangeLogin.interactionsManager.OrangeLogin;
import co.com.tutorial.screenplay.userInterfaces.orangeLogin.OrangeLoginPage;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Switch;
import net.serenitybdd.screenplay.ensure.Ensure;
import net.serenitybdd.screenplay.targets.Target;


public class LoginOnOrange implements Task {
    private final String userName;
    private final String userPassword;

    public LoginOnOrange(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public static LoginOnOrange withCredentials(String userName, String userPassword) {
        return new LoginOnOrange(userName, userPassword);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        Switch.toDefaultContext().performAs(actor);
        Ensure.that(OrangeLoginPage.USER_NAME.resolveFor(actor).isVisible())
                .isTrue()
                .orElseThrow(new TestFailure(
                        "El campo para ingresar el nombre del usuario en la pagina de OrangeHRM no es visible"))
                .performAs(actor);
        OrangeLogin.enterUserName(userName).performAs(actor);
        OrangeLogin.enterUserPassword(userPassword).performAs(actor);
        OrangeLogin.selectTheLoginButton(OrangeLoginPage.LOGIN_BUTTON).performAs(actor);
    }
}
