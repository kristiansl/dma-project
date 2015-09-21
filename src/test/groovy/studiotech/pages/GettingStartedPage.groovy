package studiotech.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class GettingStartedPage extends DmaBasePage {
    private final static String GET_STARTED_CAROUSEL_LOCATOR = "div.carousel-caption"
    private final static String GET_STARTED_SIGNIN_BUTTON_LOCATOR = "div.carousel-caption button.signin"

    GettingStartedPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {

    }

    @Override
    protected void isPageLoaded() throws Error {

    }

    boolean isGettingStartedPageDisplayed() {
        return isLogoDisplayed() && isGetStartedSignInButtonDisplayed()
    }

    boolean isLogoDisplayed() {
        return isElementPresentAndVisible(By.cssSelector(GET_STARTED_CAROUSEL_LOCATOR))
    }

    boolean isGetStartedSignInButtonDisplayed() {
        return isElementPresentAndVisible(By.cssSelector(GET_STARTED_SIGNIN_BUTTON_LOCATOR))
    }
}
