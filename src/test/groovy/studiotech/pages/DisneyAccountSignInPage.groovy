package studiotech.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

/**
 * Created with IntelliJ IDEA.
 * User: STAGR005
 * Date: 1/6/14
 * Time: 7:47 AM
 */
class DisneyAccountSignInPage extends DmaBasePage {

    private String environment
    private final By signInButtonFrame = By.id("disneyid-iframe")
    private final By signInUsername = By.id("username")

    DisneyAccountSignInPage(WebDriver driver, String environment) {
        super(driver)
        this.environment = environment
        // make sure that we are focused on the 'Sign In' page
        changeFocusToDisneyAccountSignInPage()
    }

    DisneyAccountSignInPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {
        clickLink(By.cssSelector('li#nav-signin a.signin'))
    }

    @Override
    protected void isPageLoaded() throws Error {
        assert isElementPresentAndVisible(By.id("username")): 'Account sign in modal is not displayed!!'
    }

    void enterUsername(String username) {
        sendText(By.id("username"), username)
    }

    void enterPassword(String password) {
        sendText(By.id("password"), password)
    }

    void changeFocusToDisneyAccountSignInPage() {
        String mainFrame;
        if (environment.equalsIgnoreCase("load")) {
            // Login iframe name for Load Environment is different
            //disneyid-responder-message-https-load-disneymoviesanywhere-com-didresponder-load-html
            mainFrame = "disneyid-responder-message-https-${environment}-disneymoviesanywhere-com-didresponder-${environment}-html"
        } else {
            mainFrame = "disneyid-responder-message-https-${environment}-disneymoviesanywhere-com-didresponder-html"
        }
        String signInFrame = "disneyid-iframe"


        // switchToFrame(mainFrame)

        // After last Disney ID release mainFrame is removed - no need to switch
        //switchToFrame(mainFrame)

        isElementPresent(By.xpath("//*[@id='disneyid-lightbox']"))
        switchToFrame(signInFrame)
    }

    FeaturedMoviesPage clickLoginButton() {
        // wait up to 30 seconds before timing out
        //explicitTimeoutInSeconds = 30
        String locator = 'log-in-button'

        // sign in to DMA
        clickButton(By.id(locator))
        // wait for the 'Sign In' modal to disappear
        isElementStale(driver.findElement(By.id(locator)))
        // go back to the main window from the sign-in frame
        driver.switchTo().defaultContent()
        // now, we should be on the 'Featured Movies' page
        actionTimeOut()
        return new FeaturedMoviesPage(driver)
    }

    boolean signInForm() {
        driver.switchTo().frame(driver.findElement(signInButtonFrame))
        waitUntilElementIsPresentAndVisible(signInUsername)
        return true
    }

}
