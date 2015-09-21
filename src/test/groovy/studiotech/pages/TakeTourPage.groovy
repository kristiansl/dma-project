package studiotech.pages

import com.disney.studio.qa.stbx.pageobject.Page
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

@Slf4j

class TakeTourPage extends DmaBasePage {

    private final String CLICK_MORE_BUTTON = "#scroll-arrow > span"
    private final String CLICK_MORE_BUTTON_TEXT = "CLICK FOR MORE"
    private final By clickForMoreButton = By.cssSelector(CLICK_MORE_BUTTON)
    private final By navigateRightArrow = By.cssSelector(".pulse")

    //Screen 1
    private final String HEADER_TEXT = "A Whole New Movie Experience"
    private
    final String BOTTOM_TEXT = "With Disney Movies Anywhere, you can enjoy your digital movie collection anywhere you go." +
            " It's a free, no-subscription movie experience."

    //Screen 2
    private final String PROVIDERS_TEXT = "Discover exclusive videos, access special offers, earn reward points," +
            " and bring your Disney, Pixar, and Marvel movies together."
    private final String CONNECT_PROVIDERS_TEXT = "Just join and connect to participating providers."


    //Screen 3
    private final String FAMILY_HEADER_TEXT = "Family friendly"
    private final String PARENTAL_CONTROLS = "Set up parental controls to create a movie experience you can trust."


    //Screen 4
    private final String REDEEM_CODE_HEADER = "Redeem Your Codes"
    private
    final String UNLOCK_DIGITAL_COPY = "It's never been easier to unlock Digital Copies from your Blu-ray Combo Packs." +
            " Just enter your code to instantly watch your movies wherever you go."
    private final String POINTS_REDEEM_TEXT = "And for every movie you buy, you'll earn points that you can redeem for exciting rewards."

    //Screen 5
    private final String STREAM_DOWNLOAD = "Stream or download your digital collection on all your favorite devices."

    //Screen 6
    private final String UNLOCK_MAGIC = "Unlock the Magic"
    private
    final String COLLECTION_ACCOUNTS = "Once you're set up, enjoy your Disney Movies Anywhere Collection across your connected accounts."

    //Screen 7
    private final String FUN_BEGIN_HEADER = "Let the Fun Begin"
    private
    final String MOVIE_EXPERIENCE_TEXT = "To start your own magical movie experience, just sign in and connect accounts now."
    private final String GET_STARTED_BUTTON_TEXT = "Get Started"
    private final String START_EXPLORING_TEXT = "Start Exploring"

    // List of each screen text locators
    List<List<String>> listOfTextLocators = [['#scroll-arrow>span'],
                                             ['h2', 'p'],
                                             ['p', 'p:nth-of-type(2)'],
                                             ['h2', 'p'],
                                             ['h2', 'p', 'p:nth-of-type(2)'],
                                             ['p'],
                                             ['h2', 'p'],
                                             ['h2', 'p', ' .explore-link', ' #start-btn']]

    // List of each screen element locattors
    List<List<String>> listOfElements = [['#s-0>h1>img'], ['none'], ['#img-logos'], ['none'], ['#s-4>img'], ['none'], ['.img-tablet'], ['#start-btn', '.explore-link']]

    // List of expected text on each screen
    List<List<String>> listOfTexts = [[CLICK_MORE_BUTTON_TEXT],
                                      [HEADER_TEXT, BOTTOM_TEXT],
                                      [PROVIDERS_TEXT, CONNECT_PROVIDERS_TEXT],
                                      [FAMILY_HEADER_TEXT, PARENTAL_CONTROLS],
                                      [POINTS_REDEEM_TEXT, UNLOCK_DIGITAL_COPY, REDEEM_CODE_HEADER],
                                      [STREAM_DOWNLOAD],
                                      [COLLECTION_ACCOUNTS, UNLOCK_MAGIC],
                                      [FUN_BEGIN_HEADER, MOVIE_EXPERIENCE_TEXT, GET_STARTED_BUTTON_TEXT, START_EXPLORING_TEXT]]


    TakeTourPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {

    }

    @Override
    protected void isPageLoaded() throws Error {

    }

    boolean verifyTakeTourPages() {
        int missingElmOrText = 0
        // Number of tour screens
        int totalNumberOfScreens = 7

        (0..totalNumberOfScreens).each { int i ->

            By tourScreen = By.xpath("//div[starts-with(@data-analytics-screen, 'welcome:${i + 1}')]")

            waitUntilElementIsPresentAndVisible(tourScreen)

            List<String> uiText = []

            for (txtLocator in listOfTextLocators[i]) {
                // First screen text locators

                if (i == 0) {

                    uiText.add(driver.findElement(By.cssSelector("${txtLocator}")).getText())
                    // The rest of screens text locators
                } else {
                    uiText.add(driver.findElement(By.cssSelector("#s-${i} ${txtLocator}")).getText())
                }

            }

            // Comparing list of expected text and UI text for each screen
            if (!uiText.sort().equals(listOfTexts[i].sort())) {

                log.info("Expected text ${listOfTexts[i].sort()} dosen't match UI text ${uiText.sort()} on screen {$i}")

                missingElmOrText = 1
            }

            // Verify screen elements
            List<WebElement> listOfWebElements = [clickForMoreButton, navigateRightArrow]
            if (i == totalNumberOfScreens) {
                listOfWebElements = []
            }


            for (element in listOfElements[i]) {
                // last screen don't have "Click For More" link
                if (element != 'none') {
                    listOfWebElements.add(By.cssSelector("${element}"))
                }
            }

            if (!isEachElementDisplayed(listOfWebElements)) {
                log.info("Missing  Web elements on page ${i}" + listOfWebElements)
                missingElmOrText = 1
            }

            if (i < totalNumberOfScreens) {
                clickButton(clickForMoreButton)
            }
        }


        if (missingElmOrText > 0) {
            return false

        }
        return true
    }

}


