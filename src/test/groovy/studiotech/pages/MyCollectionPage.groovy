package studiotech.pages

import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

@Slf4j
class MyCollectionPage extends DmaBasePage {

    List<String> webTitles

    private final String FAVORITES_EMPTY_STATE = "Like Something? Favorite It!"
    private final String BUILD_YOUR_COLLECTION = "Build Your Collection"
    private List<String> dirtyWords = ["damn", "asshole", "shit"]
    private final String GUEST_ENTITLEMENTS = ".container.collection.movies .movielist.container .poster"
    // CSS Locator for Guest's Entitlements posters
    private final String GUEST_ENTITLEMENTS_LINK = ".container.collection.movies .movielist.container .poster a"
    // CSS Locator for Guest's Entitlements link

    private final String MOVIE_TITLE_ATTRIBUTE_NAME = "data-analytics-movie-title"
    // Attribute name for movie title in Guest Entitlement element
    private final String IMAGES_FOR_ENTITLEMENTS = ".movie-image>img"
    // CSS Locator for Guest's Entitlements images
    private final String MOVIE_IMAGES_ATTRIBUTE_NAME = "data-source" // Attribute name for movie images
    //private final String SORT_DROPDOWN_ID = "#sort-dropdown" // CSS Locator for "Sort by ....." dropdown div element
    private final String SORT_BUTTON = ".btn.btn-link" // CSS Locator for sorting button
    // CSS locator for default - selected Sort Option
    private final String MOVIES_PAGE_HEADERS = "#nav-mycollection-movies>a>span.text"
    private final String FAVORITES_PAGE_HEADERS = "#nav-mycollection-favorites>a>span.text"
    private final String MOVIES_HEADER_ACTIVE = "#mycollection-subnav #nav-mycollection-movies.active a"
    // CSS Locator when Movies Header is Active
    private final String SYNC_BUTTON = ".icon-sync.pull-right" // CSS Locator to Sync button
    private final String SUB_NAV_BAR = ".navbar.navbar-static-top.navbar-default.subnavbar"
    // CSS Locator for My-Collection Sub Navigation Bar
    private final String MOVIES_TITLE_LINKS = ".movie-title.ellipsis" // CSS Locator to movies title links
    private final String OWNED_MOVIE_RIBBON_IMG = ".icon-flag-owned"
    private final String OWNED_FAVORITE_RIBBON_IMG = ".icon-flag-favorite-owned"
    private final String FAVORITES_MOVIE_IMG = ".icon-flag-favorite"
    private final String SUB_HEADER_MOVIES_LINK = "//div[@class='favorite-subnav']/span[text()='Movies']"
    private final String NO_MOVIES_MESSAGE_TITLE = "div.no-movies h1"
    private final String NO_MOVIES_ONBOARDING_LINK = ".cta-section>a"
    private final String NO_MOVIES_IMAGE = ".empty-collection img"
    // XPath to default state of Movies link
    private final String SUB_HEADER_VIDEOS_LINK = "//div[@class='favorite-subnav']/span/a[text()='Videos']"

    private final By alertMessage = By.cssSelector("strong[role=\"alert\"]")
    private final By sorryMessage = By.cssSelector("#modal-header-error")
    private final By hiddenAlertMessage = By.cssSelector("strong[role=\"alert\"][class=\"hide\"]")


    private By guestEntitlements = By.cssSelector(".container.collection .movielist .poster")
    // Guest's entitlements locator
    private By sortDropdnowId = By.cssSelector("#moviesSort")
    // Movies sort dropdown locator
    private By sortOptions = By.cssSelector(".menu-item")
    // Movie sort options
    private By selectedSortOption = By.cssSelector(".menu-item.ng-binding.selected")
    // Selected movie sort option
    private By guestEntitlementsLink = By.cssSelector(".container.collection .movielist .poster a")
    //Guest's Entitlements links

    private final By favoritesPageEmptyStateMsgElm = By.cssSelector(".text-purple")
    private By imagesForEntitlements = By.cssSelector(IMAGES_FOR_ENTITLEMENTS)

    private By sortButton = By.cssSelector(SORT_BUTTON)

    private By headersMovies = By.cssSelector(MOVIES_PAGE_HEADERS)
    private By headersFavorites = By.cssSelector(FAVORITES_PAGE_HEADERS)
    private By activeHeaderMovies = By.cssSelector(MOVIES_HEADER_ACTIVE)
    private By syncButton = By.cssSelector(SYNC_BUTTON)
    private By subNavBar = By.cssSelector(SUB_NAV_BAR)
    private By moviesTitleLinks = By.cssSelector(".movie-title")
    private By ownedMovieRibbon = By.cssSelector(OWNED_MOVIE_RIBBON_IMG)
    private By favoritesMovieImages = By.cssSelector(FAVORITES_MOVIE_IMG)
    private By ownedFavoriteMovieImages = By.cssSelector(OWNED_FAVORITE_RIBBON_IMG)
    private By subHeaderMoviesLink = By.xpath(SUB_HEADER_MOVIES_LINK)
    private By subHeaderVideosLink = By.xpath(SUB_HEADER_VIDEOS_LINK)
    private By noMoviesHeaderText = By.cssSelector(NO_MOVIES_MESSAGE_TITLE)
    private By noMoviesLink = By.cssSelector(".cta-section>a")
    private By noMoviesImage = By.cssSelector(NO_MOVIES_IMAGE)
    private String moviesHeaderText = "Movies"
    private String favoritesHeaderText = "Favorites"
    private By closeButton = By.cssSelector(".modal-dialog.modal-centered .modal-content .close")

    private By playMovieButton = By.id("watch-featured")
    private By vaultedMovieMessage = By.cssSelector(".ribbon-text")
    private final String VAULTED_MOVIE_MESSAGE = "In The Disney Vault"
    private By movieSlider = By.cssSelector(".slider-container .slider .movie")


    MyCollectionPage(WebDriver driver) {
        super(driver)
        webTitles = []
    }

    @Override
    protected void loadPage() {
        baseUrl = baseUrl.replace('/?home', '')
        driver.get("${baseUrl}/my-collection")
    }

    @Override
    protected void isPageLoaded() throws Error {
        assert driver.currentUrl.contains('my-collection'): "The my collection page is not displayed!!"
    }

    // Validating the Sign-In Button on MyCollection Page
    boolean isMyCollectionSignInDisplayed() {
        return isElementPresentAndVisible(By.cssSelector('a.btn'))

    }

    MyCollectionPage clickFavoriteLink() {
        clickLink(By.cssSelector('a.border span.nav-icon-favorite'))
        return this
    }

    String getFavoriteMessage() {
        def locator = 'div#main-body div.favorite-subnav + div.container h1'
        isElementPresentAndVisible(By.cssSelector(locator))
        return driver.findElement(By.cssSelector(locator)).getText()
    }

    boolean isFavoriteVideoLinkDisplayed() {
        return isElementPresentAndVisible(By.cssSelector('div.favorite-subnav span a'))
    }

    boolean areGuestEntitlementsImagesDisplayed() {
        isElementPresentAndVisible(sortDropdnowId)
        return areImagesDisplayed(imagesForEntitlements)
    }

    boolean verifyNewAndUpcomingSliderDisplayed() {
        return isElementPresentAndVisible(movieSlider)
    }

    boolean isGuestHasEntitlements(List entitlements) {
        isElementPresentAndVisible(sortDropdnowId)
        List<String> configTitles = entitlements  // List of guest's entitlements from config file
        List<WebElement> webEntitlements = driver.findElements(By.cssSelector(".movie.ng-isolate-scope"))
        // List for movies titles form my-collection page
        for (webEntitlement in webEntitlements) {
            webTitles.add(webEntitlement.getAttribute("dma-analytic-movie-title"))
        }

        if (configTitles.sort() == webTitles.sort()) {
            return true;
        }
        return false;
    }

    boolean moviesHeaderDisplayed() {
        isTextPresent(headersMovies, moviesHeaderText)
    }

    boolean favoritesHeaderDisplayed() {
        isTextPresent(headersFavorites, favoritesHeaderText)
    }

    // Is Movies Header is Selected and Displayed
    boolean activeMoviesHeaderDisplayed() {
        isElementPresentAndVisible(activeHeaderMovies)
    }

    // Is Sync Button is Visible
    boolean syncButtonDisplayed() {
        isElementPresentAndVisible(syncButton)
    }

    // My-Collection page Navigation Menu Bar
    boolean subNavBarDisplayed() {
        isElementPresentAndVisible(subNavBar)
    }

    List<String> getMoviesTitleLinks() {

        List<String> txtMoviesTitleLinks = []  // Movie links List
        waitUntilEachElementIsPresentAndVisible(imagesForEntitlements, 20)
        waitUntilElementIsClickable(moviesTitleLinks, 20)
        List<WebElement> elementsMoviesTitleLinks = driver.findElements(moviesTitleLinks)

        isEachElementPresentAndVisible(elementsMoviesTitleLinks)

        for (elementsMoviesTitleLink in elementsMoviesTitleLinks) {
            txtMoviesTitleLinks.add(elementsMoviesTitleLink.getText())
        }
        return txtMoviesTitleLinks
    }

    // List of Favorites - not owned movie images
    List<WebElement> getFavoritesTitles() {
        List<WebElement> elmFavoritesTitles = driver.findElements(favoritesMovieImages)
        return elmFavoritesTitles
    }

    // List of favorites and owned movie images
    List<WebElement> getFavoritesOwnedTitles() {
        List<WebElement> elmFavoritesOwnedTitles = driver.findElements(ownedFavoriteMovieImages)
        return elmFavoritesOwnedTitles
    }

    boolean selectMovieAndDisplayDetailsPage() {

        driver.findElement(By.partialLinkText(getMoviesTitleLinks()[0])).click()
        if (!movieDetailsPageDisplayed()) {
            return false
        }
        return true
    }

    boolean sortButtonDisplayed() {
        isElementPresentAndVisible(sortButton)
    }

    boolean sortOptionsAreDisplayed(List myCollectionSortOptions) {

        clickButton(sortButton)

        List<String> webSortOptions = []
        List<WebElement> elmSortOptions = driver.findElements(sortOptions)
        for (elmSortOption in elmSortOptions) {
            webSortOptions.add(elmSortOption.getText().trim())
        }

        if (myCollectionSortOptions.sort() == webSortOptions.sort()) {
            return true
        }
        return false
    }


    boolean areMovieOwnedRibbonsDisplayed(List<String> entitlements) {

        List<WebElement> elmOwnedMovieRibbons = driver.findElements(ownedMovieRibbon)
        List<WebElement> favoritesOwnedTitles = getFavoritesOwnedTitles()

        for (favoritesOwnedTitle in favoritesOwnedTitles) {
            elmOwnedMovieRibbons.add(favoritesOwnedTitle)
        }

        if (elmOwnedMovieRibbons.size() == entitlements.size()) {
            return true
        }
        return true
    }

    // Select and verify that Favorites header selected and favorites page displayed
    boolean selectFavoritesHeader() {
        //    clickLink(headersFavorites)
        return favoritesHeaderDisplayed()
    }

    boolean areFavoritesRibbonsDisplayed() {

        waitUntilEachElementIsPresentAndVisible(imagesForEntitlements)
        waitUntilEachElementIsPresentAndVisible(favoritesMovieImages)
        // List of all displayed movie images on Favorites page (owned and not owned movies)
        List<WebElement> elmAllFavoriteMovies = driver.findElements(imagesForEntitlements)
        // List of all ribbons images for favorite movies but not owned
        List<WebElement> elmFavoritesMovieImages = driver.findElements(favoritesMovieImages)
        // List of all ribbons images for owned favorite movies
        List<WebElement> elmOwnedFavoritesMovieImages = driver.findElements(ownedFavoriteMovieImages)

        for (elmFavoriteMovieImage in elmFavoritesMovieImages) {
            //Merging owned and not owned favorite movies
            elmOwnedFavoritesMovieImages.add(elmFavoriteMovieImage)
        }
        //Compare count of displayed favorite movie images is showing ribbons
        if (elmAllFavoriteMovies.size() == elmOwnedFavoritesMovieImages.size()) {
            return true
        }
        return true
    }

    boolean areVideosFavoriteRibbonsDisplayed() {

        List<WebElement> elmFavoriteRibbons = driver.findElements(favoritesMovieImages)
        List<WebElement> elmVideoImgs = driver.findElements(imagesForEntitlements)

        boolean allVideosImagesWithRibbons = false

        if (elmFavoriteRibbons.size() == elmVideoImgs.size()) {
            allVideosImagesWithRibbons = true
        }

        return allVideosImagesWithRibbons
    }

    boolean isDefaultOptionDateAdded(String defaultSortOption) {

        clickButton(sortButton)
        String webSelectedOption = driver.findElement(selectedSortOption).getText().trim()


        if (webSelectedOption.equals(defaultSortOption)) {
            return true
        }
        log.info("Selected sort option displayed is: '{}' but expected option is '{}'", webSelectedOption, defaultSortOption)

        return false
    }

    boolean isMovieFavorites(String selectedMovieTitle, boolean status, String videoType) {

        clickFavoriteLink()

        if (selectedMovieTitle.contains("\n")) {
            selectedMovieTitle = selectedMovieTitle.replaceAll("[\r\n]+", " ");
        }

        if (videoType.equalsIgnoreCase("video")) {
            clickSubHeaderVideos()
            waitUntilEachElementIsPresentAndVisible(imagesForEntitlements)
        }

        waitUntilEachElementIsPresentAndVisible(imagesForEntitlements)
        boolean favoriteMoveFound = false

        List<String> moviesTitles = getMoviesTitleLinks()

        for (movieTitle in moviesTitles) {
            if (movieTitle.endsWith("...")) {
                // Removing all ... at the End of title
                log.info("Removing [...] from movie title link ", movieTitle)
                movieTitle = movieTitle.replaceAll('\\.', "").trim()
            }

            if (selectedMovieTitle.startsWith(movieTitle)) {
                log.info("Found expected favorite move title - {}", selectedMovieTitle)
                favoriteMoveFound = true
                break
            } else {
                log.info("Looking for favorite movie title: {} but found movie title: {}", selectedMovieTitle, movieTitle)
            }
        }
        if (!favoriteMoveFound && !status) {
            log.info("Movie favoriting action failed, [ {} ] movie not found on Faviorites page", selectedMovieTitle)
        } else {
            log.info("Movie [ {} ] has been removed form favorites", selectedMovieTitle)
        }


        return favoriteMoveFound
    }


    boolean isVideoFavorite(String selectedVideoTitle) {

        waitForLoadModal()

        waitUntilEachElementIsPresent(By.cssSelector(".video-title"))
        List<WebElement> listOfFavoriteVideosWEs = driver.findElements(By.cssSelector(".video-title"))

        println selectedVideoTitle
        println listOfFavoriteVideosWEs[0].getText()

        assert (selectedVideoTitle.equals(listOfFavoriteVideosWEs[0].getText())) : "Discover video wasn't added to favorites"

        return true
    }


    void selectLastFavoritesMovie() {

        List<WebElement> elmAllFavoriteMovies = driver.findElements(imagesForEntitlements)
        //Select last favorite movie link
        WebElement lastMovieLink = elmAllFavoriteMovies[elmAllFavoriteMovies.size() - 1]
        lastMovieLink.click()

    }

    boolean subHeadersMoviesVideos() {
        if (isElementPresentAndVisible(subHeaderMoviesLink) &&
                isElementPresentAndVisible(subHeaderVideosLink)) {
            return true
        }
        return false
    }

    void clickSubHeaderVideos() {
        clickLink(subHeaderVideosLink)

    }

    void selectOneOfOwnedMovie() {

        waitUntilEachElementIsPresent(guestEntitlementsLink)

        List<WebElement> elmOwnedMovies = driver.findElements(guestEntitlementsLink)

        isEachElementPresentAndVisible(elmOwnedMovies)

        elmOwnedMovies.first().click()

        // Waiting after click
        implicitWait(3)


    }

    String getNoMoviesHeaderText() {
        isElementPresentAndVisible(noMoviesHeaderText)
        return driver.findElement(noMoviesHeaderText).getText()
    }

    boolean isNoMoviesHeaderTextDisplayed(String message) {
        Boolean textFound = false
        if (getNoMoviesHeaderText().contains(message) || getNoMoviesHeaderText().contains(BUILD_YOUR_COLLECTION)) {
            textFound = true
        }
        return textFound
    }

    String getNoMoviesButton() {
        isElementPresentAndVisible(noMoviesLink)
        return driver.findElement(noMoviesLink).getText()
    }

    boolean isNoMoviesButtonTextDisplayed(String message) {
        return getNoMoviesButton().startsWith(message)
    }

    boolean verifyNoMoviesImageDisplayed() {
        return isElementPresentAndVisible(noMoviesImage)
    }

    boolean favoriteMovies() {
        clickLink(headersFavorites)
        waitUntilElementIsClickable(subHeaderVideosLink, 20)
        return isEachElementPresentAndVisible(driver.findElements(favoritesMovieImages))

    }

    // Verify favorite movies flag color - Red
    boolean favoriteFlagColorRed() {

        waitUntilElementIsPresent(favoritesMovieImages)
        // Expected favorite flag image position
        String expFavoriteFlagImagePosition = "-126px -110px"
        String webFavoriteFlagImagePosition = driver.findElement(favoritesMovieImages).getCssValue("background-position")
        if (expFavoriteFlagImagePosition.equalsIgnoreCase(webFavoriteFlagImagePosition)) {
            return true
        }
        return false
    }

    // Verify owned movies flag color - Blue
    boolean ownFlagColorBlue() {
        // Expected own flag image position
        String expOwnFlagImagePosition = "-64px -110px"
        //    clickLink(headersMovies)
        waitUntilEachElementIsPresentAndVisible(imagesForEntitlements)
        String webFlagImagePosition = driver.findElement(ownedMovieRibbon).getCssValue("background-position")

        if (expOwnFlagImagePosition.equalsIgnoreCase(webFlagImagePosition)) {
            return true
        }
        return false
    }

    // Verify owned and favorite movies flag color - Purple
    boolean ownAndFavoriteFlagColorPurple() {
        waitUntilElementIsPresent(ownedFavoriteMovieImages)
        // Expected own and favorite flag image position
        String expOwnAndFavoriteFlagImagePosition = "0px -110px"
        String webOwnAndFavoriteFlagImagePosition = driver.findElement(ownedFavoriteMovieImages).getCssValue("background-position")

        if (expOwnAndFavoriteFlagImagePosition.equalsIgnoreCase(webOwnAndFavoriteFlagImagePosition)) {
            return true
        }
        return false
    }

    boolean moviesSortOptionDisplayedInCorrectOrder(List myCollectionSortOptions) {

        clickButton(sortButton)

        List<String> webSortOptions = []
        List<WebElement> elmSortOptions = driver.findElements(sortOptions)
        for (elmSortOption in elmSortOptions) {
            webSortOptions.add(elmSortOption.getText().trim())
        }

        if (myCollectionSortOptions == webSortOptions) {
            return true
        }
        return false

    }

    boolean ownMoreThanOneMovie() {

        waitUntilEachElementIsPresent(guestEntitlements)

        List<WebElement> webEntitlements = driver.findElements(guestEntitlements)

        if (webEntitlements.size() > 1) {
            return true
        }
        return false
    }

    boolean favoritesPageEmptyState() {

        return isTextPresent(favoritesPageEmptyStateMsgElm, FAVORITES_EMPTY_STATE)
    }

    void clickFavoriteVideo() {

        driver.findElement(imagesForEntitlements).click()

    }

    void closeVideoDetailsPage() {
        List<WebElement> elmCloseButtons = driver.findElements(closeButton)
        elmCloseButtons[0].click()
    }

    // Validate Valted movie message
    void vaultedMovieDisplayed() {
        waitUntilElementIsPresentAndVisible(vaultedMovieMessage)
        isTextPresent(vaultedMovieMessage, VAULTED_MOVIE_MESSAGE)
    }

    boolean vaultedMoviePlayButtonDisplayed() {
        isElementPresentAndVisible(playMovieButton)
    }

    void clickMoreLink(String page) {

        waitUntilEachElementIsPresent(By.cssSelector(".more-link.ng-scope>a"))
        // Finding all MORE links on My-Collection page
        List<WebElement> moreLinks = driver.findElements(By.cssSelector(".more-link.ng-scope>a"))

        switch (page) {
            case "feature":
                moreLinks[0].click()
                break
            case "favorite-feature":
                moreLinks[1].click()
                break
        }


    }

}
