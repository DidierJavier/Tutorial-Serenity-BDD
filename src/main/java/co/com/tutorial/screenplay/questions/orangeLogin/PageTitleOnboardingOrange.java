package co.com.tutorial.screenplay.questions.orangeLogin;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.thucydides.core.webdriver.ThucydidesWebDriverSupport;
import org.openqa.selenium.WebDriver;

public class PageTitleOnboardingOrange implements Question <String> {

    @Override
    public String answeredBy(Actor actor) {
        WebDriver driver = ThucydidesWebDriverSupport.getDriver();
        return driver.getTitle();
    }

    public static PageTitleOnboardingOrange displayed() {
        return new PageTitleOnboardingOrange();
    }
}
