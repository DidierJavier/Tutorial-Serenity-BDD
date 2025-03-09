package co.com.tutorial.screenplay.utils.ui;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;
import java.util.List;

public class WaitUtils {

    public WaitUtils() {
    }

    private static FluentWait<WebDriver> createFluentWait(Actor actor, int timeoutInSeconds, int pollingInMillis) {
        WebDriver driver = BrowseTheWeb.as(actor).getDriver();
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(timeoutInSeconds))
                .pollingEvery(Duration.ofMillis(pollingInMillis))
                .ignoring(NoSuchElementException.class);
    }

    private static <T> void waitFor(Actor actor, ExpectedCondition<T> condition, int timeoutInSeconds, int pollingInMillis) {
        createFluentWait(actor, timeoutInSeconds, pollingInMillis).until(condition);
    }

    public static void waitForVisibilityOf(Actor actor, Target target, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.visibilityOf(target.resolveFor(actor)), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForInvisibilityOf(Actor actor, Target target, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.invisibilityOf(target.resolveFor(actor)), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForElementToBeClickable(Actor actor, Target target, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.elementToBeClickable(target.resolveFor(actor)), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForPresenceOfElementLocated(Actor actor, By locator, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.presenceOfElementLocated(locator), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForPresenceOfElementLocated(Actor actor, Target target, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.presenceOfElementLocated(By.xpath(target.getCssOrXPathSelector())), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForTextToBePresentInElementLocated(Actor actor, By locator, String text, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.textToBePresentInElementLocated(locator, text), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForTextToBePresentInElement(Actor actor, Target target, String text, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.textToBePresentInElement(target.resolveFor(actor), text), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForAlertIsPresent(Actor actor, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.alertIsPresent(), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForNumberOfElementsToBe(Actor actor, By locator, int number, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.numberOfElementsToBe(locator, number), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForNumberOfElementsToBeMoreThan(Actor actor, By locator, int number, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.numberOfElementsToBeMoreThan(locator, number), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForNumberOfElementsToBeLessThan(Actor actor, By locator, int number, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.numberOfElementsToBeLessThan(locator, number), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForTitleContains(Actor actor, String title, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.titleContains(title), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForTitleIs(Actor actor, String title, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.titleIs(title), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForUrlContains(Actor actor, String url, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.urlContains(url), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForUrlToBe(Actor actor, String url, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.urlToBe(url), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForAttributeContains(Actor actor, Target target, String attribute, String value, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.attributeContains(target.resolveFor(actor), attribute, value), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForAttributeToBe(Actor actor, Target target, String attribute, String value, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.attributeToBe(target.resolveFor(actor), attribute, value), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForAttributeToBeNotEmpty(Actor actor, Target target, String attribute, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.attributeToBeNotEmpty(target.resolveFor(actor), attribute), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForElementSelectionStateToBe(Actor actor, Target target, boolean selected, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.elementSelectionStateToBe(target.resolveFor(actor), selected), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForFrameToBeAvailableAndSwitchToIt(Actor actor, By locator, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForFrameToBeAvailableAndSwitchToIt(Actor actor, Target target, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.frameToBeAvailableAndSwitchToIt(target.resolveFor(actor)), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForStalenessOf(Actor actor, Target target, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.stalenessOf(target.resolveFor(actor)), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForVisibilityOfAllElementsLocatedBy(Actor actor, By locator, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.visibilityOfAllElementsLocatedBy(locator), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForVisibilityOfAllElements(Actor actor, List<WebElement> elements, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.visibilityOfAllElements(elements), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForInvisibilityOfAllElements(Actor actor, List<WebElement> elements, int timeoutInSeconds, int pollingInMillis) {
        waitFor(actor, ExpectedConditions.invisibilityOfAllElements(elements), timeoutInSeconds, pollingInMillis);
    }

    public static void waitForInvisibilityOfElementLocated(Actor actor, By locator, int timeoutInSeconds, int pollingInMillis){
        waitFor(actor, ExpectedConditions.invisibilityOfElementLocated(locator), timeoutInSeconds, pollingInMillis);
    }
}


