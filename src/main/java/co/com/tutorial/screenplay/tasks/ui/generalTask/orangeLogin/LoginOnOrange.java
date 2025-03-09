package co.com.tutorial.screenplay.tasks.ui.generalTask.orangeLogin;

import co.com.tutorial.screenplay.exceptions.TestFailure;
import co.com.tutorial.screenplay.interactions.orangeLogin.interactionsManager.OrangeLogin;
import co.com.tutorial.screenplay.userInterfaces.orangeLogin.OrangeLoginPage;
import co.com.tutorial.screenplay.utils.ui.WaitUtils;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Switch;
import net.serenitybdd.screenplay.ensure.Ensure;

import java.time.Duration;


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
        Ensure.that(OrangeLoginPage.USER_NAME.waitingForNoMoreThan(Duration.ofSeconds(10)).resolveFor(actor).isVisible())
                .isTrue()
                .orElseThrow(new TestFailure(
                        "El campo para ingresar el nombre del usuario en la pagina de OrangeHRM no es visible"))
                .performAs(actor);
        OrangeLogin.enterUserName(userName).performAs(actor);
        OrangeLogin.enterUserPassword(userPassword).performAs(actor);

        /*WaitUntil.the(OrangeLoginPage.LOGIN_BUTTON, WebElementStateMatchers.isVisible())
                .forNoMoreThan(Duration.ofSeconds(30)).performAs(actor);

        WaitUntil.the(OrangeLoginPage.LOGIN_BUTTON, WebElementStateMatchers.containsOnlyText("DIDI"))
                .performAs(actor);

        Ensure.that(Visibility.of(OrangeLoginPage.LOGIN_BUTTON.waitingForNoMoreThan(Duration.ofSeconds(30)))).isTrue()
                .orElseThrow(new TestFailure("BAD")).performAs(actor);

        Ensure.that(Presence.of(OrangeLoginPage.LOGIN_BUTTON.waitingForNoMoreThan(Duration.ofSeconds(10)))).isTrue()
                .performAs(actor);

        Ensure.that(Text.of(OrangeLoginPage.LOGIN_BUTTON.resolveFor(actor).getText())).isEqualTo("Hola");

        FluentWait<WebDriver> wait = new FluentWait<>(BrowseTheWeb.as(actor).getDriver())
                .withTimeout(Duration.ofSeconds(45))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOf(OrangeLoginPage.LOGIN_BUTTON.resolveFor(actor)));*/

        WaitUtils.waitForVisibilityOf(actor, OrangeLoginPage.LOGIN_BUTTON, 10, 500);

        OrangeLogin.selectTheLoginButton(OrangeLoginPage.LOGIN_BUTTON).performAs(actor);
    }
}
