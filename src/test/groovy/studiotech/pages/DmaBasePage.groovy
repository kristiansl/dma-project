package studiotech.pages

import com.disney.studio.qa.stbx.pageobject.Page

import java.util.concurrent.TimeUnit;
import groovy.util.logging.Slf4j
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.WebDriverWait
import studiotech.environment.TestEnvironment
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.apache.commons.lang.RandomStringUtils
import org.openqa.selenium.support.ui.ExpectedConditions

@Slf4j
abstract class DmaBasePage extends Page {
    private final String SIGN_IN_MENU_ITEM_LOCATOR = 'li#nav-signin a'
    private final String USER_SETTINGS_LOCATOR = 'a div.icon-account'
    private final String CLOSE_BTN = "#disneyid-close"  // CSS locator for Login Light Box Close button
    private final String MY_FAVORITE_BTN = ".myfavorite span"
    private final String MOVIE_DESC = "#movie-description"
    private final String MOVIES_LINK_LOCATOR = 'li#nav-browse a'
    private final String DISCOVER_LINK_LOCATOR = 'li#nav-discover a'
    private final String MY_COLLECTION_LINK_LOCATOR = 'li#nav-mycollection a'
    private final String REWARDS_LINK_LOCATOR = 'li#nav-rewards a'
    private final String SUPPORT_LINK_LOCATOR = '//a[text()="Support"]'
    private final String SETTINGS_LINK_LOCATOR = 'li#nav-settings a div'
    private final String ACCESS_LINK_LOCATOR = 'li#nav-settings-access a'
    private final String CONTACT_LINK_LOCATOR = 'a[data-analytics-nav="footer|connect:contact"]'
    private final String MOVIE_CATEGORY_PREVIEW_LIST = '#subnav-browse'
    private final String MOVIE_NOT_FAVORITES = ".myfavorite .glyphicon.favorite-no"
    private final String MOVIE_FAVORITES_YES = ".myfavorite .glyphicon.favorite-yes"

    private By closeButton = By.cssSelector(CLOSE_BTN)
    private By favoriteButton = By.cssSelector(MY_FAVORITE_BTN)
    private By movieDescription = By.cssSelector(MOVIE_DESC)
    private By moviesLink = By.cssSelector(MOVIES_LINK_LOCATOR)
    private By discoverLink = By.cssSelector(DISCOVER_LINK_LOCATOR)
    private By myCollectionLink = By.cssSelector(MY_COLLECTION_LINK_LOCATOR)
    private By rewardsLink = By.cssSelector(REWARDS_LINK_LOCATOR)
    private By settingsLink = By.cssSelector(SETTINGS_LINK_LOCATOR)
    private By accessLink = By.cssSelector(ACCESS_LINK_LOCATOR)
    private By contactLink = By.cssSelector(CONTACT_LINK_LOCATOR)
    private By supportLink = By.xpath(SUPPORT_LINK_LOCATOR)
    private By movieNotFavorites = By.cssSelector(MOVIE_NOT_FAVORITES)
    private By movieFavoritesYes = By.cssSelector(MOVIE_FAVORITES_YES)
    private final By loadingModal = By.cssSelector(".loading.modal-centered")

    private By searchLink = By.cssSelector(".icon-search")

    double imageErrorRate
    int missingImageCounter
    // CSS locator to favorite button with attribute data-favorite= "false|true"


    WebDriverWait wait


    DmaBasePage(WebDriver driver) {
        super(driver)
        driver.manage().window().maximize()
    }

    DisneyAccountSignInPage clickSignInLink(String environment, Boolean lightBox) {
        if (!lightBox) {
            clickLink(By.cssSelector(SIGN_IN_MENU_ITEM_LOCATOR))
        }
        return new DisneyAccountSignInPage(driver, environment)
    }

    MoviesPage clickMoviesLink(String env) {
        if (env.equals('prod')) {
            driver.navigate().to("disneymoviesanywhere.com")
        } else {
            clickLink(moviesLink)
        }
        return new MoviesPage(driver, env)
    }

    DiscoverPage clickDiscoverLink() {
        clickLink(discoverLink)
        return new DiscoverPage(driver)
    }

    MyCollectionPage clickMyCollectionLink() {
        clickLink(myCollectionLink)
        return new MyCollectionPage(driver)

    }

    RewardsPage clickRewardsLink() {
        clickLink(rewardsLink)
        return new RewardsPage(driver)
    }

    SupportPage clickSupportLink() {
        clickLink(supportLink)
        return new SupportPage(driver)
    }

    ContactPage clickContactLink() {
        clickLink(contactLink)
        return new ContactPage(driver)
    }

    SearchPage clickSearchLink() {
        clickLink(searchLink)
        return new SearchPage(driver)
    }

    MovieDetailsPage goToMovieDetailsPage(String seoTitle) {
        MovieDetailsPage.seoTitle = seoTitle
        MovieDetailsPage movieDetailsPage = new MovieDetailsPage(driver)
        movieDetailsPage.visit()
        return movieDetailsPage
    }

    FeaturedMoviesPage clickFeaturedMoviesLink(TestEnvironment te) {
        // updated method to accept an instance of the TestEnvironment
        // this is required due to the security changes made to the site. Since the changes,
        // clicking on the Featured link redirects to Disney.com. That's why we are using
        // the base URL, which redirects to Featured page
        driver.get(te.getBaseUrl())
        return new FeaturedMoviesPage(driver)
    }

    AccessPage clickAccessLink() {
        clickLink(settingsLink)
        clickLink(accessLink)
        return new AccessPage(driver)
    }

    boolean isSettingsDisplayed() {
        // explicitTimeoutInSeconds = 15
        return isElementPresentAndVisible(By.cssSelector(USER_SETTINGS_LOCATOR), 15)
    }

    boolean isMovieCategoryPreviewDisplayed() {
        // explicitTimeoutInSeconds = 15
        return isElementPresentAndVisible(By.cssSelector(MOVIE_CATEGORY_PREVIEW_LIST), 15)
    }

    boolean isSignInMenuItemDisplayed() {
        return isElementPresentAndVisible(By.cssSelector(SIGN_IN_MENU_ITEM_LOCATOR))
    }

    boolean areImagesDisplayed(By locator, String attribute = 'src') {

        boolean thumbnailsDisplayed

        waitForLoadModal()

        //waitUntilElementIsPresentAndVisible(locator)

        List<WebElement> imgElements = driver.findElements(locator)

        if (imgElements.empty || imgElements.size() == 0) {
            log.info("Movies thumbnails on ${driver.getTitle()} page weren't displayed")

        }

        log.info("Total number of thumbnails = ${imgElements.size()}  on ${driver.getTitle()} page ")
        log.info("Is thumbnails list is empty? - ${imgElements.empty} ")

        if (!imgElements.empty && imgElements.size() > 0) {

            missingImageCounter = 0

            for (imgElement in imgElements) {
                String imgAttribute = imgElement.getAttribute(attribute)
                if (!imgAttribute.contains('.jpg')
                        && !imgAttribute.contains('.jpeg')
                        && !imgAttribute.contains('.png')) {
                    missingImageCounter++
                }
            }

            if (missingImageCounter > 0) {
                // calculate the image error rate
                imageErrorRate = missingImageCounter / imgElements.size()
                log.info("Missing Image Count: {}", missingImageCounter)
                thumbnailsDisplayed = false
            } else {
                // setting error rate 0.0%
                imageErrorRate = 0.0
                thumbnailsDisplayed = true

            }
        } else {

            log.info("Test failed to verify thumbnail images on ${driver.getTitle()} page")
            thumbnailsDisplayed = false
        }

        return thumbnailsDisplayed
    }


    void closeLoginLightBox(String environment) {

        clickButton(closeButton)
        driver.switchTo().defaultContent()
    }

    boolean movieDetailsPageDisplayed() {
        // Verifying Movie Details Page strings presents
        isElementPresent(movieDescription)
        if (isElementPresentAndVisible(By.xpath('//h2[text()=\'Synopsis\']'))
                & isElementPresentAndVisible(By.xpath('//h2[text()=\'Cast\']'))) {
            return true
        }
        return false
    }

    void actionTimeOut() {
        // sleep 2 sec
        sleep(2 * 1000)
    }

    void clickFavoriteButton() {
        clickButton(favoriteButton)
        actionTimeOut()
    }

    void isMovieFavorites(boolean status) {
        if (!status) {
            if (isElementPresent(movieFavoritesYes)) {
                log.info("Selected movie {} is already a favorite movie", MovieDetailsPage.seoTitle)
                log.info("Unfavoriting a movie {}", MovieDetailsPage.seoTitle)
                clickFavoriteButton()
                waitUntilElementIsPresentAndVisible(movieNotFavorites)
            } else {
                log.info("The video is NOT a favorite")
            }

        } else {
            if (isElementPresent(movieFavoritesYes)) {
                log.info("The movie is a favorite")
            } else {
                log.info("Selected movie {} is not a favorite movie", MovieDetailsPage.seoTitle)
                log.info("Favoriting a movie {}", MovieDetailsPage.seoTitle)
                clickFavoriteButton()
                waitUntilElementIsPresentAndVisible(movieFavoritesYes)
            }
        }

    }

    void implicitWait(long numberOfSeconds) {
        driver.manage().timeouts().implicitlyWait(numberOfSeconds, TimeUnit.SECONDS)
    }

    void refreshThePage() {
        driver.navigate().refresh()
    }

    String randomString(int strLength) {
        String charset = (('A'..'Z') + ('0'..'9')).join()
        String randomString = RandomStringUtils.random(strLength, charset.toCharArray())
        return randomString
    }

    boolean isEachElementDisplayed(List<WebElement> weLocators) {

        boolean weDisplayed = true

        for (weLocator in weLocators) {
            if (!isElementPresentAndVisible(weLocator)) {
                log.info("Missing  expected element" + weLocator)
                weDisplayed = false
                break
            }
        }
        return weDisplayed
    }

    void waitForLoadModal(){
        wait = new WebDriverWait(driver, 40)
        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingModal))
    }

}
