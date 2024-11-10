package co.com.tutorial.screenplay.interactions.orangeLogin.interactionsManager;

import co.com.tutorial.screenplay.interactions.orangeLogin.EnterUserName;
import co.com.tutorial.screenplay.interactions.orangeLogin.EnterUserPassword;
import co.com.tutorial.screenplay.interactions.orangeLogin.SelectTheLoginButton;
import net.serenitybdd.screenplay.Interaction;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.targets.Target;


public class OrangeLogin {

    private OrangeLogin() {
    }

    /**
     * @param userName nombre de usuario para loguearse en OrangeHRM
     */
    public static Interaction enterUserName(String userName) {
        return Tasks.instrumented(EnterUserName.class, userName);
    }

    /**
     * @param userPassword password de usuario para loguearse en OrangeHRM
     */
    public static Interaction enterUserPassword(String userPassword) {
        return Tasks.instrumented(EnterUserPassword.class, userPassword);
    }

    /**
     * @param target boton para loguearse en OrangeHRM
     */
    public static Interaction selectTheLoginButton(Target target) {
        return Tasks.instrumented(SelectTheLoginButton.class, target);
    }
}
