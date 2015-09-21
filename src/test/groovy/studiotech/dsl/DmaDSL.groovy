package studiotech.dsl

import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.ExpectedConditions
import studiotech.environment.TestConfiguration
import studiotech.environment.TestEnvironment
import studiotech.pages.*
import static java.lang.String.format

@Slf4j

class DmaDSL {
    private final WebDriver driver
    private final TestEnvironment testEnvironment
    private FeaturedMoviesPage featuredMoviesPage
    private GettingStartedPage gettingStartedPage
    private MoviesPage moviesPage
    private DiscoverPage discoverPage
    private MyCollectionPage myCollectionPage
    private RewardsPage rewardsPage
    private MovieDetailsPage movieDetailsPage
    private DisneyAccountSignInPage disneyAccountSignInPage
    private AccessPage accessPage
    private SupportPage supportPage
    private ContactPage contactPage
    private SearchPage searchPage
    private double imageErrorRate
    public List<String> moviesThumbnailImagesSize
    public CreateAccountPage createAccountPage
    private TakeTourPage takeTourPage

    DmaDSL(TestEnvironment tstEnv) {
        testEnvironment = tstEnv
        driver = testEnvironment.driver
        featuredMoviesPage = new FeaturedMoviesPage(driver)
        gettingStartedPage = new GettingStartedPage(driver)
        movieDetailsPage = new MovieDetailsPage(driver)
        createAccountPage = new CreateAccountPage(driver ,testEnvironment)
        accessPage = new AccessPage(driver)
        myCollectionPage = new MyCollectionPage(driver)
        rewardsPage = new RewardsPage(driver)
        supportPage = new SupportPage(driver)
        contactPage = new ContactPage(driver)
        disneyAccountSignInPage = new DisneyAccountSignInPage(driver)
        takeTourPage = new TakeTourPage(driver)
        driver.navigate().to(TestEnvironment.baseUrl)
    }

    void navigateToPage(String page) {
        navigateToPage(page, true)
    }

    void navigateToPage(String page, boolean refresh) {
        if (refresh) {
            refreshThePage()
        }

        if ("movies".equalsIgnoreCase(page)) {
            moviesPage = featuredMoviesPage.clickMoviesLink(testEnvironment.environment)
        } else if ("discover".equalsIgnoreCase(page)) {
            discoverPage = featuredMoviesPage.clickDiscoverLink()
        } else if ("my collection".equalsIgnoreCase(page)) {
            myCollectionPage = featuredMoviesPage.clickMyCollectionLink()
        } else if ("my movies".equalsIgnoreCase(page)) {
            myCollectionPage = featuredMoviesPage.clickMyCollectionLink()
            this.navigateToMyMoviesPage()
        } else if ("my favorite movies".equalsIgnoreCase(page)) {
            myCollectionPage = featuredMoviesPage.clickMyCollectionLink()
            this.navigateToMyFavoriteMoviesPage()
        } else if ("rewards".equalsIgnoreCase(page)) {
            rewardsPage = featuredMoviesPage.clickRewardsLink();
        } else if ("featured".equalsIgnoreCase(page)) {
            // Featured page, no need to navigate out
        } else if (page.endsWith(" movie details")) {
            String movieDetailSeoTitle = page.replace(" movie details", "")
            movieDetailsPage = featuredMoviesPage.goToMovieDetailsPage(movieDetailSeoTitle);
        } else if ('access'.equalsIgnoreCase(page)) {
            accessPage = featuredMoviesPage.clickAccessLink()
        } else if ('support'.equalsIgnoreCase(page)) {
            supportPage = featuredMoviesPage.clickSupportLink()
        } else if ('contact'.equalsIgnoreCase(page)) {
            contactPage = featuredMoviesPage.clickContactLink()
        } else if ('search'.equalsIgnoreCase(page)) {
            searchPage = featuredMoviesPage.clickSearchLink()
        } else {
            throw new IllegalArgumentException(format("The '%s' page is not supported at this time!", page))
        }
    }

    void signInAGuest(Guest guest, Boolean lightBox = false) {
        disneyAccountSignInPage = featuredMoviesPage.clickSignInLink(testEnvironment.environment, lightBox)
        enterSignInCredentials(guest)
    }

    void enterSignInCredentials(Guest guest) {
        disneyAccountSignInPage.enterUsername(guest.username)
        disneyAccountSignInPage.enterPassword(guest.password)
        featuredMoviesPage = disneyAccountSignInPage.clickLoginButton()
    }

    void signInFromPage(Guest guest , String page){
        switch (page){
            case 'take tour' : enterSignInCredentials(guest)
                break
            default: throw new IllegalArgumentException("The sign in from , '$page', page is not displayed!")
        }
    }

    double getImgErrorRate() {
        return imageErrorRate
    }

    void openBaseUrl() {
        driver.get(testEnvironment.baseUrl)
    }

    void removeAllCookies() {
        driver.manage().deleteAllCookies()
    }

    void refreshThePage() {
        driver.navigate().refresh()
    }

    //-- Beginning of--------------------- Featured page section --------------------------------------//

    void clickFeaturedMoviesLink() {
        featuredMoviesPage.clickFeaturedMoviesLink(testEnvironment)
    }

    boolean isGettingStartedPageDisplayed() {
        return gettingStartedPage.isGettingStartedPageDisplayed()
    }

    boolean isSignInMenuItemDisplayed() {
        return featuredMoviesPage.isSignInMenuItemDisplayed()
    }

    boolean isCarouselDisplayed() {
        return featuredMoviesPage.isCarouselDisplayed()
    }

    boolean correctNumberOfBannersDisplayed(int numberOfBanners) {
        return featuredMoviesPage.validateNumberOfBanners(numberOfBanners)
    }

    int getNumberOfSlidersDisplayed() {
        return featuredMoviesPage.getNumberOfSliders()
    }

    boolean areFeaturedMoviesDisplayed() {
        return featuredMoviesPage.areFeaturedMoviesDisplayed()
    }

    boolean isSettingsDisplayed() {
        return featuredMoviesPage.isSettingsDisplayed()
    }

    boolean isMovieDetailsBillboardExists() {
        return featuredMoviesPage.isMovieDetailsBillboardDisplayed()
    }

    void clickMovieDetailsLink() {
        featuredMoviesPage.clickOnMovieDetailsLink()
    }

    boolean isMoviesSliderDisplayed() {
        return featuredMoviesPage.isMoviesSliderDisplayed()
    }

    void selectFirstMovieOnSlider() {
        featuredMoviesPage.selectMovieOnSlider()
    }

    boolean isSignInBillboardDisplayed() {
        featuredMoviesPage.signInBillboard()
    }

    boolean isSignInFormDisplayed() {
        return disneyAccountSignInPage.signInForm()
    }

    void clickBillboardButton(String typeOfButton) {
        featuredMoviesPage.clickOnBillboardButton(typeOfButton)
    }

    boolean isConnetAccountsPageDisplayed() {
        return featuredMoviesPage.connectAccountsHeader()
    }


    List<String> getListOfBillboardButtonIndices(String typeOfButton) {
        featuredMoviesPage.listOfIndicesOfBillboard(typeOfButton)
    }

    boolean areMovieDetailsDisplayed(String typeOfButton) {
        featuredMoviesPage.verifyMovieDetailsDisplayed(typeOfButton)
    }

    List<String> getListOfBillboardWhiteButtonsIndices(String typeOfButton) {
        featuredMoviesPage.listOfWhiteButtonIndices(typeOfButton)
    }

    void clickBillboardWhiteButton(String typeOfButton){
        featuredMoviesPage.clickOnBillboardWhiteButton(typeOfButton)
    }


    //-- End of--------------------- Featured page section --------------------------------------------//


    //-- Beginning of--------------------- Take Tour page section ---------------------------------//

    boolean areTakeTourPagesVerified(){
        return takeTourPage.verifyTakeTourPages()
    }

    //-- End of--------------------- Take Tour page section --------------------------------------------//


    //-- Beginning of--------------------- Account creation page section ---------------------------------//
    void enterBirthDayAndYear() {
        createAccountPage.enterBirthDayAndYearDetails()
    }

    void enterBirthMonthAndYear() {
        createAccountPage.enterBirthMonthAndYearDetails()
    }

    void enterBirthDayAndMonth() {
        createAccountPage.enterBirthDayAndMonthDetails()
    }


    void clickContinueButton() {
        createAccountPage.clickContinueButtonToCreateAccount()
    }

    boolean isBirthDateErrorMessagesDisplayed() {
        return createAccountPage.birthDateErrorMessage()
    }

    void enterDateOfBirth() {
        createAccountPage.enterDateOfBirthDetails()
    }

    void enterFieldsToCreateAccount(TestEnvironment testEnvironment) {
        createAccountPage.enterFieldsToCreateAccount(testEnvironment.testAccountUsername)
    }

    void clickCreateAccountButton() {
        createAccountPage.clickOnCreateAccountButton()
    }

    boolean isAccountCreatedMessageDisplayed(String message) {
        return createAccountPage.accountCreatedMessageDisplayed(message)
    }

    void clickBackToSiteButton() {
        createAccountPage.clickOnBackToSiteButton()
    }

    boolean isAccountCreationVerified() {
        return createAccountPage.verifyAccountCreation()
    }

    boolean isAccountCreationErrorMessagesDisplayed() {
        return createAccountPage.errorMessagesAccountCreation()
    }

    void clickSignInButton() {
        createAccountPage.clickOnSignInButton()
    }

    void switchToSignInFrame(){
        createAccountPage.switchToSignInFrame()
    }

    void selectDateOfBirthForEligibilityCheck(int age){
        createAccountPage.selectDateOfBirthForEligibilityCheck(age)
    }

    boolean isAccountCreationFormDisplayed() {
        return createAccountPage.verifyAccountCreationFormIsDisplayed()
    }

    boolean isNotEligibleToCreateAccountMessageDisplayed(){
        return createAccountPage.verifyNotEligibleAgeToCreateAccount()
    }

    boolean isUsernameTextBoxDisplayed(){
        return createAccountPage.usernameTextBoxPresent()
    }

    //-- End of--------------------- Account creation section --------------------------------------------//

    //-- Beginning of--------------------- Movies page section ---------------------------------//

    boolean areMoviesCategoriesDisplayed() {
        return moviesPage.areMoviesCategoriesDisplayed()
    }

    boolean isMovieCategoryPreviewDisplayed() {
        return moviesPage.isMovieCategoryPreviewDisplayed()
    }

    void moviesAllMovies() {
        moviesPage.clickAllMovies()
    }

    boolean areAllMoviesDisplayed() {
        if (!testEnvironment.environment.equalsIgnoreCase("prod")) {
            moviesPage.areAllImagesDisplayed()
            imageErrorRate = moviesPage.imageErrorRate // set the calculated image error rate

            if (imageErrorRate > 0.10) {
                log.info("Error rate of: " + imageErrorRate + "% is more than critical error rate of 10%")
                return false
            }
            log.info "Error rate of: " + imageErrorRate + "% is less than critical error rate of 10%"
            return true
        } else {
            return moviesPage.areAllImagesDisplayed()
        }
    }

    boolean areMovieCategoryPageDisplayed() {
        int categoryCount = 0
        for (categoryLink in moviesPage.getCategoryLinks()) {
            driver.navigate().to(categoryLink)
            String currentUrl = driver.getCurrentUrl()

            println currentUrl

            log.info("Navigating to category link: {}", categoryLink)
            log.info("Current category URL: {}", currentUrl)
            categoryCount++

            if (!areAllMoviesDisplayed()) {
                log.info("Test failed, following category failed to load {}", currentUrl)
                return false
            }
        }
        log.info("Total Categories: {}", categoryCount)
        return true
    }

    boolean isAnyMovieCategoryPageTitlesGenericallyHardcoded() {

        return moviesPage.verifyIfAnyMovieCategoryPageTitlesGenericallyHardcoded()
    }


    boolean areExpectedMovieCategoriesShowing() {
        List<String> expectedCategories = testEnvironment.movieCategoryPaths;

        List<String> categoryLinksOnBrowsePage = moviesPage.getCategoryLinksMinusBaseUrl()

        log.info("Menu Links Categories: {}", categoryLinksOnBrowsePage)
        log.info("Expected Categories: {}", expectedCategories)

        return categoryLinksOnBrowsePage.containsAll(expectedCategories);
    }


    void allMoviesThumbnailsImages() {
        moviesThumbnailImagesSize = moviesPage.getMovieThumbnailsImagesSize()
    }


    boolean hoverOverMoviesMenuLink() {
        return moviesPage.moviesLinkHoverOverAction()
    }

    boolean areSubMenuMoviesLinksDisplayed() {
        return moviesPage.subMenuMoviesLinks()
    }

    //-----End of------Movies page section ------------------------------------------//

    //-- Beginning of--------------------- Movie Details page section ---------------------------------//

    boolean isMovieDetailsPageDisplayed() {
        return movieDetailsPage.isMovieDetailsPageDisplayed()
    }

    boolean isFavoritesButtonDisplayed() {
        return movieDetailsPage.isFavoritesButtonDisplayed()
    }

    void addMovieToFavorites() {
        movieDetailsPage.addToFavorites()
    }

    void removeMovieFromFavorites() {
        movieDetailsPage.removeFromFavorites()
    }

    void removeVideoFromFavorites() {
        myCollectionPage.selectFavoritesHeader()
        myCollectionPage.clickSubHeaderVideos()
        myCollectionPage.clickFavoriteVideo()
        movieDetailsPage.removeVideoFromFavorites()
    }

    void clickFavoriteButton() {
        movieDetailsPage.clickFavoriteButton()
    }


    boolean hasBonusFeaturesInCategories() {
        return movieDetailsPage.isBonusContentInCategories()
    }

    boolean hasBonusFeatureAsThumbnailLinks() {
        return movieDetailsPage.isBonusContentThumbnailLinks()
    }

    boolean isMovieDetailsPageBonusListingCountBetween1And(int maxCount) {
        return movieDetailsPage.isBonusListingCountBetween1And(maxCount)
    }

    boolean isBuyButtonRestricted() {
        return movieDetailsPage.isBuyButtonRestricted()
    }

    boolean isPlayButtonRestricted() {
        return movieDetailsPage.isPlayButtonRestricted()
    }

    boolean doesShareButtonShowRestriction() {
        return movieDetailsPage.doesShareButtonShowRestriction()
    }

    boolean isLightboxPresentAfterClickingUpdate() {
        movieDetailsPage.clickRestrictionModalUpdateButton()
        movieDetailsPage.closeLoginLightBox(testEnvironment.environment)
        return true
    }

    boolean arebonusContentVideoNamesDisplayed() {
        return movieDetailsPage.bonusContentVideoNames()
    }

    boolean isHdIconDisplayed()  {
        return movieDetailsPage.hdIconDisplayed()
    }

    //-- End of--------------------- Movie Details page section ---------------------------------------//

    //-- Beginning of--------------------- My-Collection page section ---------------------------------//

    boolean isMyCollectionSignInDisplayed() {
        return myCollectionPage.isMyCollectionSignInDisplayed()
    }

//    boolean isMovieOnUsDisplayed() {
//        return myCollectionPage.areImagesDisplayed(By.xpath("//*[@id='main-body']//div[@class='col-xs-5 text-right']/img"))
//    }

    void clickFavoritesLink() {
        myCollectionPage.clickFavoriteLink()
    }

    boolean isFavoriteItMessageDisplayed() {
        return myCollectionPage.getFavoriteMessage().contains('Like Something? Favorite It!')
    }

    boolean isFavoriteVideoLinkDisplayed() {
        return myCollectionPage.isFavoriteVideoLinkDisplayed()
    }

    boolean isGuestHasEntitlements() {
        return myCollectionPage.isGuestHasEntitlements(testEnvironment.guest.entitlements)
    }

    boolean areGuestEntitlementsImagesDisplayed() {
        return myCollectionPage.areGuestEntitlementsImagesDisplayed()
    }


    boolean selectMovieAndDetailsDisplayed() {
        myCollectionPage.selectMovieAndDisplayDetailsPage()
    }

    boolean isSortButtonDisplayed() {
        myCollectionPage.sortButtonDisplayed()
    }

    boolean areSortOptionsDisplayed() {
        myCollectionPage.sortOptionsAreDisplayed(testEnvironment.getMyCollectionSortOptions())
    }

    boolean areFavoritesSortOptionsDisplayed() {
        myCollectionPage.sortOptionsAreDisplayed(testEnvironment.getFavoritesSortOptions())
    }

    List<String> getMyCollectionEntitlements() {
        return myCollectionPage?.webTitles
    }

    boolean areMyCollectionRibbonsDisplayed() {
        return myCollectionPage.areMovieOwnedRibbonsDisplayed(testEnvironment.guest.entitlements)
    }

    boolean isFavoritesHeaderSelected() {
        return myCollectionPage.selectFavoritesHeader()
    }

    boolean areFavoritesRibbonsDisplayed() {
        return myCollectionPage.areFavoritesRibbonsDisplayed()
    }

    boolean areVideosFavoriteRibbonsDisplayed() {
        return myCollectionPage.areVideosFavoriteRibbonsDisplayed()
    }

    boolean isNoMoviesHeaderTextDisplayed(String message) {
        return myCollectionPage.isNoMoviesHeaderTextDisplayed(message)
    }

    boolean isNoMoviesButtonTextDisplayed(String buttonText) {
        return myCollectionPage.isNoMoviesButtonTextDisplayed(buttonText)
    }

    boolean isNoMoviesImageDisplayed() {
        return myCollectionPage.verifyNoMoviesImageDisplayed()
    }

    boolean isNewAndUpcomingSliderDisplayed() {
        return myCollectionPage.verifyNewAndUpcomingSliderDisplayed()
    }

    boolean isDefaultOptionDateAdded(String defaultSortOption) {
        return myCollectionPage.isDefaultOptionDateAdded(defaultSortOption)
    }

    boolean isFavoritesMovieAppears(boolean favoriteStatus) {
        movieDetailsPage.isMovieDetailsPageDisplayed()
        myCollectionPage = featuredMoviesPage.clickMyCollectionLink()
        return myCollectionPage.isMovieFavorites(MovieDetailsPage.seoTitle, favoriteStatus, "movie")
    }

    boolean isFavoritesVideoAppears(boolean favoriteStatus) {
        myCollectionPage = featuredMoviesPage.clickMyCollectionLink()
        //return myCollectionPage.isMovieFavorites(DiscoverPage.selectedVideoTitle, favoriteStatus, "video")
        return myCollectionPage.isVideoFavorite(DiscoverPage.selectedVideoTitle)
    }

    void selectLastFavoriteMovie() {
        myCollectionPage.selectLastFavoritesMovie()
    }

    boolean areMoviesVideosSubHeadersDisplayed() {
        myCollectionPage.subHeadersMoviesVideos()
    }

    void selectVideosSubHeader() {
        myCollectionPage.clickSubHeaderVideos()
    }

    void selectOwnedMovie() {
        myCollectionPage.selectOneOfOwnedMovie()
    }

    boolean isGuestHasFavorites() {
        return myCollectionPage.favoriteMovies()
    }

    boolean isOwnFlagBlue() {
        return myCollectionPage.ownFlagColorBlue()

    }

    boolean isFavoriteFlagRed() {
        return myCollectionPage.favoriteFlagColorRed()
    }

    boolean isOwnAndFavoriteFlagPurple() {
        return myCollectionPage.ownAndFavoriteFlagColorPurple()
    }

    boolean isSortOptionDisplayedInCorrectOrder() {
        return myCollectionPage.moviesSortOptionDisplayedInCorrectOrder(testEnvironment.getMyCollectionSortOptions())
    }

    boolean isOwnMoreThanOneMovie() {
        return myCollectionPage.ownMoreThanOneMovie()
    }

    boolean isEmptyStateDisplayed() {
        return myCollectionPage.favoritesPageEmptyState()
    }

    void closeVideoDetailsPage() {
        myCollectionPage.closeVideoDetailsPage()
    }

    void isVaultedMovieDisplayed() {
        myCollectionPage.vaultedMovieDisplayed()
    }

    boolean isvaultedMoviePlayButtonDisplayed() {
        return myCollectionPage.vaultedMoviePlayButtonDisplayed()
    }

    void navigateToMyMoviesPage(){
        myCollectionPage.clickMoreLink("feature")
    }

    void navigateToMyFavoriteMoviesPage(){
        myCollectionPage.clickMoreLink("favorite-feature")
    }
    //-- End of--------------------- My-Collection page section ---------------------------------------//

    //** Beginning of ******************** Discovery page section ************************************//

    boolean areDiscoverThumbnailsDisplayed() {
        Boolean imagesDisplayed = discoverPage.areImagesDisplayed(By.cssSelector('.lazyload'))
        imageErrorRate = discoverPage.imageErrorRate  // set the calculated image error rate
        return imagesDisplayed
    }

    boolean areTitlesDisplayed() {
        discoverPage.titlesDisplayed()
    }

    boolean areVideoInfoButtonsDisplayed() {
        discoverPage.areInfoButtonsDisplayed()
    }

    boolean areVideoPlayButtonsDisplayed() {
        discoverPage.arePlayButtonsDisplayed()
    }

    void selectAnyVideoRandom() {
        discoverPage.selectRandomVideo()
    }

    boolean isVideoDescriptionDisplayed() {
        return discoverPage.verifyVideoDescriptionDisplayed()
    }

    boolean isVideoPlayButtonDisplayed() {
        return discoverPage.verifyVideoPlayButtonDisplayed()
    }

    boolean isVideoTitleDisplayed() {
        return discoverPage.verifyVideoTitleDisplayed()
    }

    boolean isPlayTimeDisplayed() {
        return discoverPage.verifyPlayTimeDisplayed()
    }

    boolean isFavoriteButtonDisplayed() {
        return discoverPage.verifyFavoriteButtonDisplayed()
    }

    boolean isShareButtonDisplayed() {
        return discoverPage.verifyShareButtonDisplayed()
    }

    void selectVideoByVideoTitle() {
        discoverPage.selectVideoByTitle()
    }

    void clickVideoFavoriteButton() {
        discoverPage.clickDiscoverFavoriteButton()
    }

    void addSelectedVideoToFavorites() {
        discoverPage.addVideoToFavorites()
    }

    void closeVideoPopUp() {
        discoverPage.closePopUp()
    }

    void closeLoginPopUp() {
        discoverPage.closeLoginWindow(testEnvironment.environment)
    }

    boolean isVideoNotFavorites() {
        return discoverPage.videoNotFavorites()
    }

    boolean isSignInMessageDisplayed(String message) {
        return discoverPage.signInMessageDisplayed(message)
    }

    boolean isBackgroundDisplayed() {
        return discoverPage.backgroundWithRedAndPinkStarts()
    }

    boolean areVideoThumbnailSizesMatch() {
        return discoverPage.videoThumbnailSizesMatch(moviesThumbnailImagesSize)
    }

    void clickingShareButton() {
        discoverPage.clickShareButton()
    }

    boolean isShareDialogDisplayed() {
        return discoverPage.shareDialogDisplayed()
    }

    boolean isShareOptionsDisplayed() {
        return discoverPage.shareOptionsDisplayed()
    }

    void areDiscoverFirstFewVideosDisplayed() {
        discoverPage.discoverFirstFewVideos()
    }
//
//    boolean areNewDiscoverVideoDisplayed() {
//        discoverPage.verifyNewDiscoverVideos()
//    }

    boolean backToDiscoverPage() {
        return discoverPage.backToDiscoverPage()
    }

    boolean areNewDiscoverVideosDisplayed(List<String> videoGUIDs){

        return discoverPage.verifyNewDiscoverVideosOnPage(videoGUIDs)

    }

    boolean areNewDiscoverVideosDetailPageDisplayed(List<String> videoGUIDs){
        return discoverPage.verifyEachNewDiscoverVideoDetailsPage(videoGUIDs)
    }

    boolean areNewDiscoverVideosHeroImagesDisplayed(List<String> videoGUIDs) {
        return discoverPage.verifyEachNewDiscoverVideoHeroImage(videoGUIDs)
    }

    String currentVideoTitle(){
        return discoverPage.getCurrentVideoTitle()
    }

    //-- End of--------------------- Discovery page section ----------------------------------------//

    //** Beginning of ************************* Rewards page section *******************************//

    boolean isGetRewardsLinkDisplayed() {
        return rewardsPage.isGetRewardsLinkDisplayed()
    }

    boolean verifyRewardPoints() {
        return rewardsPage.arePointDisplayed()
    }

    boolean isEnterCodeFieldDisplayed() {
        rewardsPage.enterCodeFieldDisplayed()
    }

    boolean verifyGuestRewardPoints() {
        rewardsPage.areGuestPointsDisplayed(testEnvironment.guest.points)
    }

    boolean verifyReceivedRewardPoints() {
        rewardsPage.areGuestPointsDisplayed(testEnvironment.guest.receivedRewards)
    }

    void applyRewardsCode(String code) {
        rewardsPage.applyRewardsCode(code)
    }

    void selectGetRewards() {
        rewardsPage.clickGetRewards()
    }

    boolean verifyDmrUrl() {
        rewardsPage.getDmrUrl(testEnvironment.dmrUrl)
    }

    boolean isRewardsHeaderDisplayed() {
        return rewardsPage.rewardsHeader()
    }

    boolean isRewardsPointsCounterDisplayed() {
        return rewardsPage.rewardsPointsCounter()
    }

    boolean isDMRLogoDisplayed() {
        return rewardsPage.dmrLogo()
    }

    boolean isFirstBillboardPresent() {
        return rewardsPage.rewardsBillboard()
    }

    boolean isRewardsMovieSliderDisplayed() {
        return rewardsPage.rewardsMovieSlider()
    }

    boolean isCodeSubmitButtonDisplayed() {
        return rewardsPage.codeSubmitButton()
    }

    boolean isCodeCleanButtonDisplayed() {
        return rewardsPage.codeClearButton()
    }

    void enterRewardsCode(TestEnvironment testEnvironment) {
        rewardsPage.enterRewardsCode(testEnvironment.rewardsCode)
    }

    Boolean isRewardsPlayMovieDisplayed() {
        rewardsPage.rewardsPlayMovieDisplayed()
    }

    void logInWithExistingUser() {
        rewardsPage.signInExistingUser()
    }

    boolean isDuplicateCodeMessageDisplayed() {
        return rewardsPage.duplicateCodeMessageDisplayed()
    }

    void enterInvalidRewardsCode(TestEnvironment testEnvironment) {
        rewardsPage.enterInvalidRewardsCode(testEnvironment.invalidRewardsCode)
    }

    boolean isInvalidCodeErrorMessageDisplayed() {
        return rewardsPage.invalidCodeErrorMessage()
    }

    //-- End of--------------------- Rewards page section ---------------------------------------//
    //** Beginning of **************** Settings - Access page section ***************************//

    void enableAccessRestrictions() {
        accessPage.enableAccessRestrictions(testEnvironment, 'PG-13', 'TV-PG', 'PG', 'NR')
    }

    boolean disableAccessRestrictions(boolean disableBasicAccess) {
        return accessPage.disableAccessRestrictions(disableBasicAccess, testEnvironment, 'PG-13', 'TV-PG', 'PG', 'NR')
    }

    void cleanupAccess(Guest guest) {
        sleep(2 * 1000)
        navigateToPage('access', false)
        boolean lightboxDisplayed = disableAccessRestrictions(true)
        if (lightboxDisplayed) {
            disneyAccountSignInPage = featuredMoviesPage.clickSignInLink(testEnvironment.environment, lightboxDisplayed)
            disneyAccountSignInPage.enterUsername(guest.username)
            disneyAccountSignInPage.enterPassword(guest.password)
            featuredMoviesPage = disneyAccountSignInPage.clickLoginButton()
        }
    }

    void navigateToSettings() {
        accessPage.goToAccountSettings()
    }

    boolean linkingVudu(Guest guest) {
        accessPage.connectAccountWithVudu(guest.username ,guest.password)
    }

    boolean isAccountConnectedWithVudu() {
        return accessPage.accountConnectedWithVudu()
    }

    boolean unlinkVudu() {
        return accessPage.disconnectAccountWithVudu()
    }

    boolean isAccountDisconnectedWithVudu() {
        return accessPage.accountDisconnectedWithVudu()
    }

    void clickAccountProfile() {
        accessPage.clickOnAccountProfile()
    }

    void profileUpdated() {
        createAccountPage.updateGuestProfile()
    }

    boolean isUpdatedProfileVerified() {
        return createAccountPage.verifyUpdatedProfile()
    }

    void clickAccountHistory() {
        accessPage.clickOnAccountHistory()
    }

    boolean verifyAccountHistoryPurchasedEntitlements() {
        return accessPage.accountHistoryPurchasedEntitlements(testEnvironment.guest.entitlements)
    }

    Boolean areAccountHistoryDetailsDisplayed() {
        return accessPage.accountHistoryDetails()
    }

    void clickCloseCaption() {
        accessPage.clickOnCloseCaption()
    }

    boolean areCloseCaptioningOptionsVerified(TestEnvironment testEnvironment) {
        return accessPage.verifyCloseCaptionOptions(testEnvironment.closeCaptioningOptions)
    }

    boolean isCloseCaptioningDetailsDisplayed() {
        return accessPage.closeCaptioningDetails()
    }

    List<String> getAccountSettingsCloseCaptionOptions() {
        return accessPage?.accountSettingsCloseCaptionOptions
    }

    List<String> getAccountHistoryEntitlements(){
        return accessPage?.accountHistoryEntitlements
    }
    //-- End of -------------------- Settings - Access page section -----------------------------//

    //** Beginning of **************** Contact page section *************************************//

    boolean isContactFormDisplayed() {
        contactPage.contactUsForm()
    }

    boolean isContactCategoryDisplayed() {
        contactPage.contactUsCategory(testEnvironment.getContactUsCategory())
    }

    boolean isTermsOfUse() {
        contactPage.termsOfUse()
    }

    boolean isSendButton() {
        contactPage.submitButton()
    }

    boolean isFormBodyEmpty() {
        contactPage.formBodyEmpty()
    }

    boolean isSendButtonDisabled() {
        contactPage.submitButtonDisabled()
    }

    void enterTextToForm() {
        contactPage.enterTextToFormBody()
    }

    void clickSendButton() {
        contactPage.clickOnSendButton()
    }

    boolean isAgreeTermsErrorMessageDetailsDisplayed() {
        return contactPage.contactUsFormTermsErrorMessage()
    }

    boolean isTermsErrorMessageVerified(){
        return contactPage.verifyAgreeTermsErrorMessage()
    }


    //-- End of --------------------- Contact page section -------------------------------------//

    //** Beginning of **************** Support  page section *************************************//

    boolean isSupportPageDisplayed() {

        supportPage.pageDisplayed()
    }


    boolean areCuratedQuestionsDisplayed(int numberOfCuratedQuestions) {

        supportPage.curatedQuestions(numberOfCuratedQuestions)
    }


    boolean areHelpTopicsDisplayed() {
        supportPage.helpTopics()
    }

    boolean canClickEachHelpTopic() {
        supportPage.clickEachHelpTopic()
    }

    void isKeyChestLogoClicked() {
        supportPage.clickKeyChestLogo()
    }

    boolean isKeychestFaqInSupportPageDisplayed() {
        return supportPage.keychestFaqInSupportPage()
    }

    //-- End of --------------------- Support page section -------------------------------------//

    //** Beginning of **************** Support  page section *************************************//

    void searchForTitle(String seoTitle) {
        searchPage.searchTitle(seoTitle)
    }

    boolean isSearchResultForCarsTitleDisplayed() {
        return searchPage.resultLookupForCars()
    }

    boolean isSearchResultsDisplayed() {
        return searchPage.searchResults()
    }

    boolean isSearchAreaDisplayed() {
        return searchPage.searchArea()
    }

    boolean isSearchBtnDisplayed() {
        return searchPage.searchButton()
    }

    boolean isCancelSearchBtnDisplayed() {
        return searchPage.cancelSearchButton()
    }

    boolean minNumberOfCharactersForSearch(String numberOfChar) {
        return searchPage.numberOfCharactersForSearch(numberOfChar)
    }

    void isCancelSearchButtonClicked() {
        searchPage.clickCancelSearchButton()
    }

    boolean isSearchAreaEmpty() {
        return searchPage.searchAreaEmpty()
    }
    //-- End of --------------------- Support page section -------------------------------------//
}