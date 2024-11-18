package co.com.tutorial.screenplay.userInterfaces.orangeLogin;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class OrangeLoginPage {

    private OrangeLoginPage() {
    }

    public static final Target USER_NAME = Target
            .the("Campo para ingresar el nombre usuario para iniciar sesion")
            .located(By.cssSelector("input[placeholder='Username']"));
    public static final Target USER_PASSWORD = Target
            .the("Campo para ingresar el Password para iniciar sesion")
            .located(By.cssSelector("input[placeholder='Password']"));
    public static final Target LOGIN_BUTTON = Target.the("Boton para inicar sesion")
            .located(By.cssSelector("button[type='submit']"));
    public static final Target INVALID_CREDENTIALS = Target.the("Boton para inicar sesion")
            .located(By.cssSelector(".oxd-alert-content--error > p"));
}
