package studiotech.pages

import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@Slf4j

class SupportPage extends DmaBasePage {
    private final String SUPPORT_TITLE = "About | Disney Movies Anywhere"
    private final String HEADER_TXT = "Support"
    private final By headerTxt = By.cssSelector(".col-xs-12>h1")
    private final By elmCuratedQuestions = By.cssSelector("ul[aria-hidden=\"false\"] a")
    private final By elmHelpTopics = By.cssSelector(".faq-group")
    private final By keyChestLogo = By.cssSelector("a > span[aria-label='Powered by Keychest']")
    private final By keyChestFaq = By.id("2385")

    SupportPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {

    }

    @Override
    protected void isPageLoaded() throws Error {

    }

    boolean pageDisplayed() {
        if (driver.getTitle().equalsIgnoreCase(SUPPORT_TITLE)) {
            return isTextPresent(headerTxt, HEADER_TXT)


        }
        return false
    }

    boolean curatedQuestions(int curatedQuestionsCount) {

        List<WebElement> lstCuratedQuestions = driver.findElements(elmCuratedQuestions)

        if (lstCuratedQuestions.size() >= curatedQuestionsCount) {
            return true
        }
        return false
    }

    boolean helpTopics() {

        List<WebElement> helpTopicsLinks = driver.findElements(elmHelpTopics)

        return isEachElementPresentAndVisible(helpTopicsLinks)

    }

    boolean clickEachHelpTopic() {

        List<WebElement> helpTopicsLinks = driver.findElements(elmHelpTopics)

        Boolean helpTopicStatus = false

        for (helpTopicsLink in helpTopicsLinks) {

            String helpTopicHref = helpTopicsLink.getAttribute("href")
            //Click on Help Topic Link
            helpTopicsLink.click()

            String currentUrl = driver.getCurrentUrl()

            log.info("Help topic link href anchor: {}", helpTopicHref)
            log.info("Current URL anchor: {}", currentUrl)

            if (helpTopicHref.equalsIgnoreCase(currentUrl)) {
                helpTopicStatus = true
            } else {
                break
            }
        }
        return helpTopicStatus
    }

    void clickKeyChestLogo() {
        waitUntilElementIsPresentAndVisible(keyChestLogo)                                                               //Key Chest logo in DMA home page
        clickLink(keyChestLogo)
    }

    boolean keychestFaqInSupportPage() {
        isElementPresentAndVisible(keyChestFaq)                                                                         //Key Chest FAQ in support page
    }

}