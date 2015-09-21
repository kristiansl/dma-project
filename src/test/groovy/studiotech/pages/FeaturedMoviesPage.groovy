package studiotech.pages

import com.disney.studio.qa.stbx.pageobject.Page
import groovy.util.logging.Slf4j
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebElement
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.htmlunit.HtmlUnitDriver
import studiotech.pages.MovieDetailsPage

/**
 * @author Leon Berkovich
 */

@Slf4j
class FeaturedMoviesPage extends DmaBasePage {

    private final String WATCH_VIDEO_LINK = "//a[@class='btn btn-lg btn-clear preview']"
    // Billboard preview link on Preview banner
    private final String MOVIE_DETAILS_LINK = "//a[text()='Movie Details']"
    // Billboard movie details link on Preview banner
    private final String ACTIVE_BILLBOARD_IMG = ".item.active"
    // SCC Locator for currently - active billboard image
    private final String BILLBOARD_IMG = ".item"
    // SCC Locator for active billboard image
    private final String MOVIES_SLIDER_CONTAINER = ".movieslider"             //SCC Locator for sliders container
    private final String MOVIES_SLIDER_IMGS = ".bxslider.poster>li>a>img"     //SCC locator to all slider movie images
    private final String TAKE_TOUR_BUTTON = "a[href=product-tour]"

    private final String LETS_CONNECT_HEADER = "div.connectAccount > h1"
    private final String CLEAR_BUTTON_BILLBOARD = "a.btn-clear"
    private final String BILLBOARD_LOCATOR = " .btn-clear"
    private final String CONNECT_ACCOUNTS_HEADER_TEXT = "Let's Connect"
    private final String WHITE_BUTTON_BILLBOARD = "a.btn-white"

    private By watchVideoLink = By.xpath(WATCH_VIDEO_LINK)
    private By movieDetailsLink = By.xpath(MOVIE_DETAILS_LINK)
    private By activeBillboardImg = By.cssSelector(ACTIVE_BILLBOARD_IMG)
    private By billboardImg = By.cssSelector(BILLBOARD_IMG)
    private By moviesSliderContainer = By.cssSelector(MOVIES_SLIDER_CONTAINER)
    private By moviesSliderImgs = By.cssSelector(MOVIES_SLIDER_IMGS)
    private By takeTourButton = By.cssSelector(TAKE_TOUR_BUTTON)

    private final letsConnectHeader = By.cssSelector(LETS_CONNECT_HEADER)
    private final clearButtonBillboard = By.cssSelector(CLEAR_BUTTON_BILLBOARD)
    private final whiteButtonBillboard = By.cssSelector(WHITE_BUTTON_BILLBOARD)
    private final By thumbnailImages = By.cssSelector('.bxslider.poster>li>a>img')
    private final By secondSliderThumbnails = By.cssSelector('.container.padded-container div:nth-child(2) div:nth-child(2) img')

    protected String pauseCarouselCmd = "\$('#carousel-featured').carousel('pause');"

    def movieDetailPage = new MovieDetailsPage(driver)
    def takeTourPage = new TakeTourPage(driver)

    FeaturedMoviesPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {
        driver.get("${baseUrl}")
    }

    @Override
    protected void isPageLoaded() throws Error {
        assert driver.currentUrl.contains("${baseUrl}"): 'Featured movies page is not displayed!!'
    }

    boolean isCarouselDisplayed() {
        return isElementPresentAndVisible(By.cssSelector('.bx-no-border.movieslider'))
    }

//    boolean billboardImageDisplayed(String bannerName) {
//        return areImagesDisplayed(activeBillboardImg, 'style')
//    }

    boolean validateNumberOfBanners(int numberOfBanners) {
        if (getNumberOfBanners() < numberOfBanners) {
            log.info("Current number of banners is: '{}' but Expected number of banners is: '{}'", getNumberOfBanners(), numberOfBanners)
            return false
        }
        return true
    }

    int getNumberOfBanners() {
        return driver.findElements(billboardImg).size()
    }

    int getNumberOfSliders() {
        return driver.findElements(By.cssSelector('div.bx-viewport')).size()
    }

    // Method verifies if thumbnail images displayed
    boolean areFeaturedMoviesDisplayed() {
        return areImagesDisplayed(thumbnailImages)
    }

    boolean isMovieDetailsBillboardDisplayed() {
        selectCarouselBanner("Preview", "Movie Details")
        return isElementPresent(watchVideoLink)
    }

    // ---------------------------------- Select Carousel's ----------------------- //
    void selectCarouselBanner(String bannerName, String bannerLink = "Movie Details") {

        int bnrNumber;
        bnrNumber = getBannerNumber(bannerName, bannerLink)
        JavascriptExecutor js = (JavascriptExecutor) driver;

        String selectCarouselCmd = "\$('#carousel-featured').carousel(" + bnrNumber + ");"


        js.executeScript(selectCarouselCmd)
        js.executeScript(pauseCarouselCmd)
    }

    // ---------------------------------- End ----------------------- //

    // ---------------------------------- Get Carousel's Banner Number ----------------------- //

    /**
     * Use {@link #getBannerNumber(java.lang.String, java.lang.String)}
     *
     * @param bannerName
     * Current available banner names:
     *   Take Tour, Get Started!, Sign In, Start Your Collection, Preview, Get Connected, Watch Video
     * XPath = //a[starts-with(@class, 'btn btn-lg btn-clear')][text()={bannerName}]
     * @param bannerLink
     * Current available banner links
     *   Preview banner: Movie Details, Buy
     *   Take Tour banner: Sign In
     *   Watch Video banner: Movie Details
     * XPath = //a[starts-with(@class, 'btn btn-lg btn-white')][text()={bannerLink}]
     *
     * @return bannerNumber
     */

    int getBannerNumber(String bannerName, String bannerLink) {

        int bannerNumber;
        String dataAnalyticsBannerXpath;

        // Preview banner can have Movie Details or Buy links
        if (bannerName.equals("Preview")) {
            dataAnalyticsBannerXpath = "//a[starts-with(@class, 'btn btn-lg btn-white')][text()='" + bannerLink + "']"
        } else {
            dataAnalyticsBannerXpath = "//a[starts-with(@class, 'btn btn-lg btn-clear')][text()='" + bannerName + "']"
        }


        WebElement bannerElement = driver.findElement(By.xpath(dataAnalyticsBannerXpath))
        // Find banner number for Movie Details
        String firstAttributeValue = bannerElement.getAttribute('data-analytics-banner').split(/\|/)[0]
        // Splitting attribute value //a[@data-analytics-banner='banner4|Movie Details']
        String strBannerNumber = firstAttributeValue.charAt(firstAttributeValue.length() - 1)
        // Retrieving banner number
        bannerNumber = strBannerNumber.toInteger() - 1
        // Banner number will always be  - 1
        log.info("'{}' Banner number is: '{}'", bannerName, bannerNumber)

        return bannerNumber
    }
    // ---------------------------------- END -------------------------------------------------//

    void clickOnMovieDetailsLink() {
        clickLink(movieDetailsLink)
    }

    // Verify if movies slider is displayed
    boolean isMoviesSliderDisplayed() {
        isElementPresentAndVisible(moviesSliderContainer)
    }

    void selectMovieOnSlider() {

       int randomIndex = new Random().nextInt(4 - 2) + 2
        println randomIndex
       List<WebElement> thumbnailsSecondSlider = driver.findElements(secondSliderThumbnails)
       thumbnailsSecondSlider[randomIndex].click()

    }

    boolean signInBillboard() {
        isElementPresent(By.xpath("//a[starts-with(@class, 'btn btn-lg btn-white')][text()='Sign In']"))
    }


    void clickOnBillboardButton(String typeOfButton) {
        WebElement billboardButton
        List<String> billboardIndexes = listOfIndicesOfBillboard(typeOfButton)
        if (billboardIndexes.size() > 0) {
            int billboardIndex = billboardIndexes.get(0).toInteger() - 1
            int billboardNumber = billboardIndexes.get(0).toInteger()

            def selectCarouselCmd = "\$('#carousel-featured').carousel(${billboardIndex})"
            javascriptExecutor.executeScript(selectCarouselCmd)
            By button = By.cssSelector("#billboard-" + "${billboardNumber}" + BILLBOARD_LOCATOR)
            waitUntilElementIsPresentAndVisible(button)
            billboardButton = driver.findElement(button)
            if (billboardButton.getText().equals(typeOfButton)) {
                billboardButton.click()
            }
        }
        else {
            log.info("Unexpected billboard")
        }
        }


    boolean connectAccountsHeader() {
        waitUntilElementIsPresentAndVisible(letsConnectHeader)
        //Verify connect accounts page is displayed
        String ConnectAccountsText = driver.findElement(letsConnectHeader).getText()
        if (ConnectAccountsText.equals(CONNECT_ACCOUNTS_HEADER_TEXT)) {
            return true
        }
        return false
    }

    List<String> listOfIndicesOfBillboard(String typeOfButton) {

        List<String> billboardIndexList = []
        List<WebElement> billboardButtons = driver.findElements(clearButtonBillboard)

        for (billboardButton in billboardButtons) {
            if ((billboardButton.getAttribute("data-analytics-billboard-cta-title")).equals(typeOfButton)) {
                billboardIndexList.add(billboardButton.getAttribute("data-analytics-billboard-index"))
                //Getting list of billboard indices from billboards having clear buttons
            }
        }
        return billboardIndexList
    }

    boolean verifyMovieDetailsDisplayed(String typeOfButton) {

        String selectCarousel
        WebElement billboardButton
        boolean mdHeaders = true
        boolean mdElements = true
        boolean result = true

        List<String> billboardIndexes = listOfIndicesOfBillboard(typeOfButton)

        if (billboardIndexes.size() > 0) {

            int billboardsCount = billboardIndexes.size().toInteger()
            (1..billboardsCount).each { int i ->

                int billboardIndex = billboardIndexes.get(i - 1).toInteger() - 1
                int billboardNumber = billboardIndexes.get(i - 1).toInteger()

                selectCarousel = "\$('#carousel-featured').carousel(${billboardIndex})"                                                      //Navigating to billboard
                javascriptExecutor.executeScript(selectCarousel)
                javascriptExecutor.executeScript(pauseCarouselCmd)

                By button = By.cssSelector("#billboard-" + "${billboardNumber}" + BILLBOARD_LOCATOR)
                waitUntilElementIsPresentAndVisible(button)

                billboardButton = driver.findElement(button)

                if (billboardButton.getText().equals(typeOfButton)) {

                    def movieTitleFromBillboard = billboardButton.getAttribute("data-analytics-billboard-cta").split("/movie/")[1]
                    log.info("Movie title: ${movieTitleFromBillboard}")

                    billboardButton.click()
                    assert (movieTitleFromBillboard.equals(movieDetailPage.getTitle())): "{$movieTitleFromBillboard} is not landing on correct movie detail page from billboard"

                    mdHeaders = movieDetailPage.areMovieDetailsHeadersDisplayed()

                    if (mdHeaders == false) {
                        result = false
                        log.info("Movie title: ${movieTitleFromBillboard} + is missing expected headers")
                    }
                    mdElements = movieDetailPage.areMovieDetailsElementsDisplayed(false)

                    if (mdElements == false) {
                        result = false
                        log.info("Movie title: ${movieTitleFromBillboard} + is missing expected elements")
                    }

                    driver.navigate().back()
                } else {
                    log.info("Unexpected billboard")
                }
            }

        }
        return result
    }

    List<String> listOfWhiteButtonIndices(String typeOfButton) {

        List<String> billboardIndexList = []
        List<WebElement> billboardButtons = driver.findElements(whiteButtonBillboard)

        for (billboardButton in billboardButtons) {
            if ((billboardButton.getAttribute("data-analytics-billboard-cta-title")).equals(typeOfButton)) {
                billboardIndexList.add(billboardButton.getAttribute("data-analytics-billboard-index"))                //Getting list of billboard indices from billboards having white buttons
            }
        }
        return billboardIndexList
    }

    void clickOnBillboardWhiteButton(String typeOfButton){
        def selectCarouselCmd = "\$('#carousel-featured').carousel(${listOfWhiteButtonIndices(typeOfButton)}-1)"
        javascriptExecutor.executeScript(selectCarouselCmd)
        switch (typeOfButton) {
            case 'Take Tour': waitUntilEachElementIsPresentAndVisible(takeTourButton)
                              clickButton(takeTourButton)
                              break
            default: throw new IllegalArgumentException("The '$typeOfButton' button is not displayed!")
        }
    }


    }




