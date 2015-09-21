package studiotech.pages

import groovy.util.logging.Slf4j
import org.eclipse.jetty.util.log.Log
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@Slf4j
class MovieDetailsPage extends DmaBasePage {

    private final String MY_FAVORITE_BTN = ".myfavorite"
    private final String SHARE_BTN = ".share-btn"
    private final String FACEBOOK_BTN = ".facebook-logo"
    private final String TWITTER_BTN = ".twitter-logo"
    private final String EMAIL_BTN = ".email-icon"
    // CSS locator to favorite button with attribute data-favorite= "false|true"
    private final String MOVIE_TITLE_TEXT = "#movie-description #title-container h1"
    // CSS locator for movie title
    private final String MOVIE_NOT_FAVORITES = ".myfavorite .glyphicon.favorite-no"
    // CSS locator for not favorites movie
    private final String MOVIE_FAVORITES_YES = ".myfavorite .glyphicon.favorite-yes"
    // CSS locator for favorites movie .myfavorite .glyphicon.favorite-no
    private final String BONUS_CONTENT_CSS_SELECTOR = ".bonus.content ul li"
    private final String BONUS_CATEGORY_CSS_SELECTOR = "div.container.bonus-content div.row h4"
    private final String BONUS_THUMBNAIL_IMAGE = ".bonus-item .content"
    private final String BONUS_CATEGORY_TITLE = ".col-xs-12>h4"
    private final String BONUS_VIDEO_TITLE = ".bonus-title"
    private final String BUY_BUTTON_RESTRICTED = 'div#movie-description div#button-group a[href="#restrictedAccess"]'
    private
    final String PLAY_BUTTON_RESTRICTED = 'div#movie-description div#button-group a#restricted-watch[href="#restrictedAccess"]'
    private final String RESTRICTED_ACCESS_MODAL = 'div#restrictedAccess div.modal-content'
    private
    final String RESTRICTED_ACCESS_MODAL_CANCEL = RESTRICTED_ACCESS_MODAL + ' button[data-analytics-modal="limited access|cancel"]'
    private
    final String RESTRICTED_ACCESS_MODAL_UPDATE = RESTRICTED_ACCESS_MODAL + ' button[data-analytics-modal="limited access|change access"]'
    private final String BONUS_CATEGORY_VIDEO_NAMES = "ul.bxslider.bonuses li a div.bonus-title"
    private final String BONUS_BADGE = "#badges strong.bonus-badge.icon-bonus"
    private final String HD_ICON = "#title-container > div.hdcontent.movie-hd-content"
    private final String MOVIE_DETAIL_PAGE_EXPAND_DOWN = "span.more-arrow-down"
    private final String MOVIE_TITLE = ".hero"

    private By movieNotFavorites = By.cssSelector(MOVIE_NOT_FAVORITES)
    private By movieFavoritesYes = By.cssSelector(MOVIE_FAVORITES_YES)
    private By favoriteButton = By.cssSelector(MY_FAVORITE_BTN)
//	private By shareButton = By.cssSelector(SHARE_BTN)
    private By facebookButton = By.cssSelector(FACEBOOK_BTN)
    private By twitterButton = By.cssSelector(TWITTER_BTN)
    private By emailButton = By.cssSelector(EMAIL_BTN)
    private By movieTitle = By.cssSelector(MOVIE_TITLE_TEXT)
    private By bonusCategoryTitle = By.cssSelector(BONUS_CATEGORY_TITLE)
    private By bonusThumbnailImage = By.cssSelector(BONUS_THUMBNAIL_IMAGE)
    private By bonusVideoTitle = By.cssSelector(BONUS_VIDEO_TITLE)
    private By buyButtonRestricted = By.cssSelector(BUY_BUTTON_RESTRICTED)
    private By playButtonRestricted = By.cssSelector(PLAY_BUTTON_RESTRICTED)
    private By restrictedAccessModalCancelButton = By.cssSelector(RESTRICTED_ACCESS_MODAL_CANCEL)
    private By restrictedAccessModalUpdateButton = By.cssSelector(RESTRICTED_ACCESS_MODAL_UPDATE)
    //  private By bonusContentVideoNames = By.cssSelector(BONUS_CATEGORY_VIDEO_NAMES)
    private By bonusBadge = By.cssSelector(BONUS_BADGE)
    private By hdIcon = By.cssSelector(HD_ICON)
    private final expandDown = By.cssSelector(MOVIE_DETAIL_PAGE_EXPAND_DOWN)
    private final buyButton = By.id("buy_button")
    private final previewButton = By.id("watch-trailer")
    private final title = By.cssSelector(MOVIE_TITLE)

    //private final movieDetailHeaders = By.cssSelector('h2')
    private final movieDetailHeaders = By.xpath("//div[starts-with(@class, 'col')]/h2")

    static String seoTitle

    MovieDetailsPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {
        baseUrl = baseUrl.replace('/?home', '')
        driver.get("${baseUrl}/movie/$seoTitle")
    }

    @Override
    protected void isPageLoaded() throws Error {
        assert driver.currentUrl.contains(seoTitle): "The title of the movie is unknown!! Be sure to set the movie's title " +
                "(i.e., MovieDetailsPage.seoTitle = 'toy-story-2'), before calling the visit() method."
    }

    boolean isMovieDetailsPageDisplayed() {
        getMovieTitle()
        movieDetailsPageDisplayed()
    }


    void addToFavorites() {
        isMovieFavorites(false)
        log.info("Adding movie to favorites: {}", seoTitle)
        clickFavoriteButton()
        isElementPresentAndVisible(movieFavoritesYes)

    }

    void isMovieFavorites(boolean status) {
        if (!status) {
            if (isElementPresent(movieFavoritesYes)) {
                log.info("Selected movie {} is already a favorite movie", seoTitle)
                log.info("Unfavoriting a movie {}", seoTitle)
                clickFavoriteButton()
                waitUntilElementIsPresentAndVisible(movieNotFavorites)
            } else {
                log.info("The video is NOT a favorite")
            }

        } else {
            if (isElementPresent(movieFavoritesYes)) {
                log.info("The movie is a favorite")
            } else {
                log.info("Selected movie {} is not a favorite movie", seoTitle)
                log.info("Favoriting a movie {}", seoTitle)
                clickFavoriteButton()
                waitUntilElementIsPresentAndVisible(movieFavoritesYes)
            }
        }

    }

    void removeFromFavorites() {
        isMovieFavorites(true)
        clickFavoriteButton()
        log.info("Removed movie from favorites: {}", seoTitle)
    }

    void removeVideoFromFavorites() {
        clickFavoriteButton()
        log.info("Removed video from favorites: {}", seoTitle)
    }

    String getMovieTitle() {
        isElementPresentAndVisible(movieTitle)
        seoTitle = driver.findElement(movieTitle).getText()
        log.info("Selected movie title is: '{}'", seoTitle)
        return seoTitle
    }

    boolean isBonusListingCountBetween1And(int maxCount) {
        int bonusListItemCount = driver.findElements(By.cssSelector(BONUS_CONTENT_CSS_SELECTOR)).size()
        log.debug("Bonus item count:" + bonusListItemCount)
        return bonusListItemCount > 0 && bonusListItemCount <= maxCount
    }

    boolean isBonusContentInCategories() {
        List<WebElement> bonusCategories = driver.findElements(bonusCategoryTitle)
        return bonusCategories.size() > 0
    }

    boolean isBonusContentThumbnailLinks() {
        List<WebElement> bonusContentImages = driver.findElements(bonusThumbnailImage)
        return bonusContentImages.size() > 0
    }

    boolean isBuyButtonRestricted() {
        if (isElementPresentAndVisible(buyButtonRestricted)) {
            clickButton(buyButtonRestricted)
            return isRestrictionModalVisible(true)
        }
        return false
    }

    boolean isPlayButtonRestricted() {
        if (isElementPresentAndVisible(playButtonRestricted)) {
            clickButton(playButtonRestricted)
            return isRestrictionModalVisible(false)
        }
        return false
    }

    boolean doesShareButtonShowRestriction() {
        clickButton(facebookButton)
        return isRestrictionModalVisible(true)
    }

    /**
     * Checks if the restricted access modal is shown. Checks if
     * the restricted access modal has popped up and is visible. If the
     * closeModal value is true, then the modal's cancel button is
     * clicked.
     *
     * @return
     * true if the modal appears; otherwise, false
     */
    private isRestrictionModalVisible(boolean closeModal) {
        boolean restrictionModalShows = false

        if (isElementPresentAndVisible(restrictedAccessModalCancelButton)
                && driver.findElement(restrictedAccessModalCancelButton).isDisplayed()) {
            restrictionModalShows = true
            if (closeModal) {
                clickButton(restrictedAccessModalCancelButton)
            }
        }
        return restrictionModalShows
    }

    void clickRestrictionModalUpdateButton() {
        clickButton(restrictedAccessModalUpdateButton)
    }

    boolean bonusContentVideoNames() {

        waitUntilElementIsPresentAndVisible(bonusBadge)

        Collection<String> bonusContentImages = driver.findElements(bonusThumbnailImage).collect() //bonus video images
        Collection<String> bonusVideoNames = driver.findElements(bonusVideoTitle).collect() { it.getText() }
        //bonus video titles

        log.info("Bonus Title Names:" + bonusVideoNames)

        if ((bonusContentImages.size()).equals(bonusVideoNames.size())) {
            log.info("All the bonus content images has title")
            return true
        }
        return false
    }

    boolean hdIconDisplayed() {
        waitUntilElementIsPresentAndVisible(hdIcon)
        //HD icon in movie detail page
        if (isElementPresentAndVisible(hdIcon)) {
            log.info("Guest movie version is : HD")
            return true
        }
        return false
    }


    boolean isFavoritesButtonDisplayed() {
        isElementPresentAndVisible(favoriteButton)
    }

    String getTitle() {
        waitUntilElementIsPresentAndVisible(title)
        def movieTitleFromMovieDetailPage = driver.findElement(title).getAttribute("data-analytics-movie-title")
        return movieTitleFromMovieDetailPage
    }

    boolean areMovieDetailsElementsDisplayed(boolean authenticatedUser) {

        List<WebElement> expElements

        if (authenticatedUser) {
            expElements = [facebookButton, twitterButton, emailButton, previewButton, favoriteButton]
        } else {
            expElements = [previewButton, favoriteButton]
        }


        List<WebElement> mdButtons = driver.findElements(By.cssSelector("#button-group a"))

        if (mdButtons.size() > 1) {
            expElements.add(buyButton)
        }

        isEachElementDisplayed(expElements)
    }

    boolean areMovieDetailsHeadersDisplayed() {

        List<String> expMovieDetailHeaders = ['Synopsis', 'Cast']
        Boolean headersDisplayed = true
        List<String> uiMovieDetailHeaders = []

        clickLink(expandDown)

        waitUntilElementIsPresentAndVisible(By.cssSelector(".more-arrow-up"))

        List<WebElement> weHeaders = driver.findElements(movieDetailHeaders)

        for (weHeader in weHeaders) {

            uiMovieDetailHeaders.add(weHeader.getText())
        }

        if (uiMovieDetailHeaders.size() == 3) {
            expMovieDetailHeaders.add('Bonus Features')
        }

        if (!expMovieDetailHeaders.sort().equals(uiMovieDetailHeaders.sort())) {
            log.info("Expected Headers are not matching")
            log.info("${expMovieDetailHeaders.sort()} - Movie detailes headers are not match - ${uiMovieDetailHeaders.sort()}")
            headersDisplayed = false
        }

        return headersDisplayed

    }
}
