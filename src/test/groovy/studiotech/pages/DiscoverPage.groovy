package studiotech.pages

import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import java.util.regex.Pattern


@Slf4j
class DiscoverPage extends DmaBasePage {

    private final String BACKGROUND_IMG = "starry-bg-red.png" // Discover page background image
    private final String VIDEO_RUNTIME = ".discoverContainer .category-item a .time"
    private final String VIDEO_DESCRIPTION = " div.bonusFooter p"            // CSS for Video Description
    private final String VIDEO_TITLE_POPUP = " div.bonusFooter h2"
    // CSS locator for video play button
    private final String PLAY_TIME = " div.bonusFooter div.pull-right"
    // CSS locator for video runtime on PopUp window
    private final String FAVORITE_BTN = ".myfavorite span"              // CSS locator for favorites button
    private final String SHARE_BTN = " div.bonusBody a.bonus-share-btn span"        // CSS locator for share button
    private
    final String VIDEO_POPUP_BASE = "//div[@aria-hidden='false']/div[@class='modal-dialog modal-centered']/div[@class='modal-content']/div[@class='bonusModalContent']"
    private final String VIDEO_POPUP_BASE_CSS = "div[aria-hidden='false'] div.modal-dialog div.bonusModalContent"
    private final String FAVORITE_YES = ".glyphicon.favorite-yes"  // CSS locator for Favorite button with status - yes
    private final String FAVORITE_NO = ".glyphicon.favorite-no"  // CSS locator for Favorite button with status - no
    private final String CLOSE_BUTTON = ".modal-dialog.modal-centered .modal-content .close"
    // CSS locator for closed button
    private
    final String DISCOVER_VIDEO_BASE = "#main-body > div.padded-container.container.discoverContainer > div.container.row > div:nth-of-type("
    private final String DISCOVER_VIDEO_TITLE = ") > a > h2"
    private final String RUN_TIME = ") > a > span"
    private
    final String POP_UP_VIDEO_DESCRIPTION = "div[aria-hidden=false] > div.modal-dialog.modal-centered > div.modal-content > div.bonusModalContent > div:nth-of-type(2) > h2"
    private
    final String POP_UP_RUN_TIME = "div[aria-hidden=false] > div.modal-dialog.modal-centered > div.modal-content > div.bonusModalContent > div:nth-of-type(2) > div > div.pull-right"
    private final String DISCOVER_PAGE_HEADER = "#main-body > div:nth-of-type(1) > div.container.hero-container > h1"
    private final By strSignIn = By.cssSelector('.signinRequiredMessage')
    // CSS locator for SignIn string on Discover page for unauthenticated user

    private By videoThumbnail = By.cssSelector('.lazyload')
   // private By videoRuntime = By.cssSelector(VIDEO_RUNTIME)
    private By videoTitle = By.cssSelector('.content>h2')  // CSS selector for video titles
    private By videoDescription = By.cssSelector(VIDEO_POPUP_BASE_CSS + VIDEO_DESCRIPTION)
    private By videoTitlePopUp = By.cssSelector(VIDEO_POPUP_BASE_CSS + VIDEO_TITLE_POPUP)
    private By playButton = By.cssSelector('.in .play-container')  // CSS selector for play button on video details page
    private By playTime = By.cssSelector(VIDEO_POPUP_BASE_CSS + PLAY_TIME)
    private By favoriteButton = By.cssSelector(FAVORITE_BTN)
    private By shareButton = By.cssSelector(VIDEO_POPUP_BASE_CSS + SHARE_BTN)
    private By yesFavorite = By.cssSelector(FAVORITE_YES)
    private By notFavorite = By.cssSelector(FAVORITE_NO)
    private By closeButton = By.cssSelector(CLOSE_BUTTON)
    private By videoPopUpWindow = By.xpath(VIDEO_POPUP_BASE)
    private By popUpVideoDescription = By.cssSelector(POP_UP_VIDEO_DESCRIPTION)
    private By popUpRuntime = By.cssSelector(POP_UP_RUN_TIME)
    private By discoverPageHeader = By.cssSelector(DISCOVER_PAGE_HEADER)
    private final shareDialogBox = By.cssSelector("#share > div")
    private final facebookShare = By.id("fb-share")
    private final twitterShare = By.id("twitter-share")
    private final emailShare = By.id("email-share")

    private List<WebElement> videoTitles = []        // List of WebElements  for video Titles
    private List<WebElement> videoThumbnails = []     // List of WebElements  for video Thumbnails
    private List<WebElement> infoButtons = []           // List of WebElements  for video Info  Button
    private List<WebElement> playButtons = []
    private List<String> imgUrls = []

    private By videoDetailsTitle = By.cssSelector(".bonusFooter > h2")
    private By videoDetailsRuntime = By.cssSelector(".bonusFooter .pull-right")

    private String currentGUID
    private String currentTitle

    static String selectedVideoTitle
    
    private final By videoInfoButton = By.cssSelector('.videoOneClickPlayInfo.info-icon')
    private final By videoPlayButton = By.cssSelector('.videoOneClickPlayInfo.info-icon')

    DiscoverPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {
        baseUrl = baseUrl.replace('?home', '')
        driver.get("${baseUrl}/discover")

    }

    @Override
    protected void isPageLoaded() throws Error {
        assert driver.currentUrl.contains('discover'): 'Discover page is not displayed!!'
    }

    boolean titlesDisplayed() {

        videoTitles = driver.findElements(videoTitle)
        videoThumbnails = driver.findElements(videoThumbnail)
        
        if (videoTitles.size() == videoThumbnails.size()) {
            return true
        }
        return false

    }

    boolean areInfoButtonsDisplayed() {

        infoButtons = driver.findElements(videoInfoButton)

        if (videoTitles.size() == infoButtons.size()) {
            return true
        }
        return false
    }

    boolean arePlayButtonsDisplayed() {

        playButtons = driver.findElements(videoPlayButton)

        if (videoTitles.size() == playButtons.size()) {
            return true
        }
        return false
    }

    boolean verifyVideoDescriptionDisplayed() {
        isElementPresentAndVisible(videoDescription)
    }

    boolean verifyVideoTitleDisplayed() {
        isElementPresentAndVisible(videoTitlePopUp)
    }

    boolean verifyVideoPlayButtonDisplayed() {
        isElementPresentAndVisible(playButton)
    }

    boolean verifyPlayTimeDisplayed() {
        isElementPresentAndVisible(playTime)
    }

    boolean verifyFavoriteButtonDisplayed() {
        isElementPresentAndVisible(favoriteButton)
    }


    boolean verifyShareButtonDisplayed() {
        isElementPresentAndVisible(shareButton)
    }

    // Selecting any random video on Discover page
    void selectRandomVideo() {

        isEachElementPresentAndVisible(videoThumbnail)

        List<WebElement> elmVideoInfoButtons = driver.findElements(videoInfoButton)

        int max = elmVideoInfoButtons.size()
        log.info("No. of discover videos: " + max)

        int randVideoLink = new Random().nextInt(max) - 18

        elmVideoInfoButtons[randVideoLink].click()
    }

    // selecting video base on video title
    void selectVideoByTitle() {
        isEachElementPresentAndVisible(videoThumbnail)
        WebElement titleLink = driver.findElement(By.partialLinkText(selectedVideoTitle))
        titleLink.click()
    }


    void addVideoToFavorites() {
        waitUntilElementIsClickable(favoriteButton)
        isVideoFavorites(false)
        log.info("Adding video to Favorites, clicking favorites button")
        clickFavoriteButton()
        isElementPresent(yesFavorite)
    }

    void clickDiscoverFavoriteButton() {
       // driver.navigate().refresh()
        isElementPresentAndVisible(videoPopUpWindow)
        getVideoTitle()
        clickFavoriteButton()
    }

    String getVideoTitle() {
        waitUntilElementIsPresentAndVisible(videoTitlePopUp)
        selectedVideoTitle = driver.findElement(videoTitlePopUp).getText()
        log.info("Selected Video title '{}'", selectedVideoTitle)
        return selectedVideoTitle
    }

    // close video's window
    void closePopUp() {
        List<WebElement> elmCloseButtons = driver.findElements(closeButton)
        elmCloseButtons[0].click()
        isEachElementPresentAndVisible(driver.findElements(videoTitle))
    }


    boolean videoNotFavorites() {
        return isElementPresentAndVisible(notFavorite)
    }

    // Favorite button status
    void isVideoFavorites(boolean status) {
        getVideoTitle()
        if (!status) {
            if (isElementPresent(yesFavorite)) {
                log.info("Removing video from Favorites, clicking favorite button")
                clickFavoriteButton()
                waitUntilElementIsPresentAndVisible(notFavorite)
            } else {
                log.info("The video is NOT a favorite")
            }

        } else {
            if (isElementPresent(yesFavorite)) {
                log.info("The video is a favorite")
            } else {
                log.info("Adding  video to favorites, clicking on favorite button")
                clickFavoriteButton()
                waitUntilElementIsPresentAndVisible(yesFavorite)
            }
        }
    }

    void closeLoginWindow(String env) {
        closeLoginLightBox(env)
    }

    boolean signInMessageDisplayed(String message) {
        return isTextPresent(strSignIn, message)
    }

    boolean backgroundWithRedAndPinkStarts() {

        Boolean imgFound = false

        // Getting CSS background-image params
        String webBackgroundImgRef = driver.findElement(By.id("main-body")).getCssValue("background-image")

        // Removing double quotes and brackets
        webBackgroundImgRef = webBackgroundImgRef.replace("url(\"", '').replace("\")", '')

        URL backgroundImgUrl = new URL(webBackgroundImgRef)

        // Parsing  URL - getting a file path
        String backgroundImgPath = backgroundImgUrl.getPath()

        List<String> path = backgroundImgPath.split("/")

        //Retrieving background image file name
        String backgroundImg = path[path.size() - 1]

        // Comparing background image names
        if (backgroundImg.equalsIgnoreCase(BACKGROUND_IMG)) {
            imgFound = true
        }

        return imgFound
    }

    boolean videoThumbnailSizesMatch(List<String> movieThumbnailImagesSize) {

        List<WebElement> elmVideoThumbnailImages = driver.findElements(videoThumbnail)
        List<String> videoThumbnailImagesSize = []

        // True | False for matching image size
        Boolean imgSizeMatch = false

        // Setting values to the List of Video Images Thumbnail  Size
        for (elmVideoThumbnailImage in elmVideoThumbnailImages) {
            String videoThumbnailImageSize = elmVideoThumbnailImage.getSize()
            videoThumbnailImagesSize.add(videoThumbnailImageSize)
        }

        // Verifying if total number of unique sizes of video thumbnail images are even number
        BigInteger uniqueSizeVideoImagesThumbnail = videoThumbnailImagesSize.unique().size()
        log.info("Total count of unique Video Thumbnail Images {}", uniqueSizeVideoImagesThumbnail)

        String movieThumbnailImageSize

        // List for unique sizes of video thumbnail images
        List<String> uniqueVideoImageSizes = videoThumbnailImagesSize.unique()
        // List for unique sizes of movies thumbnail images
        List<String> uniqueMovieImageSizes = movieThumbnailImagesSize.unique()

        log.info("Unique Sizes of movie thumbnails: {}", uniqueMovieImageSizes)
        log.info("Unique Sizes of video thumbnails: {}", uniqueVideoImageSizes)

        // if movie's thumbnail sizes are the same, than String movieThumbnailImageSize = List movieThumbnailImagesSize
        if (uniqueMovieImageSizes.size() == 1) {
            movieThumbnailImageSize = uniqueMovieImageSizes.toString().replaceAll("\\[|\\]", "")
            for (uniqueVideoImageSize in uniqueVideoImageSizes) {
                // verifying if the size of video's thumbnail images are the same as size of movie's thumbnail images
                if (uniqueVideoImageSize.equalsIgnoreCase(movieThumbnailImageSize)) {
                    imgSizeMatch = true
                }
            }
        } else {
            // verifying if list of video's thumbnails sizes are the same as list of movies thumbnails sizes
            if (uniqueVideoImageSizes == uniqueMovieImageSizes) {
                imgSizeMatch = true
            }

        }

        return imgSizeMatch
    }


    void clickShareButton() {
        clickButton(shareButton)

    }

    boolean shareDialogDisplayed() {
        isElementPresentAndVisible(shareDialogBox)
    }

    boolean shareOptionsDisplayed() {
        waitUntilElementIsClickable(facebookShare)
        waitUntilElementIsClickable(twitterShare)
        waitUntilElementIsClickable(emailShare)
    }

    void discoverFirstFewVideos() {
        //validating required number of videos from discover page
        def range = 1..4
        range.each { int i ->

            waitUntilElementIsPresentAndVisible(By.cssSelector(DISCOVER_VIDEO_BASE + "${i}" + DISCOVER_VIDEO_TITLE))
            def videoTitleBeforeClicking = driver.findElement(By.cssSelector(DISCOVER_VIDEO_BASE + "${i}" + DISCOVER_VIDEO_TITLE)).getText()
            def videoRunTimeBeforeClicking = driver.findElement(By.cssSelector(DISCOVER_VIDEO_BASE + "${i}" + RUN_TIME)).getText()
            clickLink(By.cssSelector(DISCOVER_VIDEO_BASE + "${i}" + DISCOVER_VIDEO_TITLE))

            waitUntilElementIsPresentAndVisible(favoriteButton)
//                               waitUntilElementIsPresentAndVisible(shareButton)
            waitUntilElementIsPresentAndVisible(playButton)
            def runtimeAfterPopup = driver.findElement(popUpRuntime).getText()
            assert (videoRunTimeBeforeClicking.equals(runtimeAfterPopup))                                   //verifying discover video description in thumbnail and pop-up
            def videoDescriptionAfterPopUp = driver.findElement(popUpVideoDescription).getText()
            assert (videoTitleBeforeClicking.equals(videoDescriptionAfterPopUp))                            //verifying discover run-time in thumbnail and pop-up
            clickButton(closeButton)

        }

    }

    boolean backToDiscoverPage() {
        isElementPresentAndVisible(discoverPageHeader)
        return true
    }


    void clickOnDesiredDiscoverVideo(int videoIndex) {

        WebElement discoverVideo = driver.findElement(By.cssSelector("a[data-analytics-index=\"${videoIndex}\"]"))

        discoverVideo.click()


    }


    Boolean verifyRunTimes(List<String> videoGUIDs) {

        boolean runTimesShowing = true
        int missingRunTimes = 0


        (1..videoGUIDs.size().toInteger()).each { int i ->

            try {

                By videoRuntime = By.cssSelector("a[data-guid=\"${videoGUIDs[i - 1]}\"] .time")

                assert isElementPresentAndVisible(videoRuntime): "Runtime video is missing"

                log.info"Video Runtime : " + driver.findElement(videoRuntime).getText()

            } catch (Exception NoSuchElementException) {


            }


            if (!isEachElementPresentAndVisible(videoRuntime)) {
                missingRunTimes += 1
                log.info("Missing video runtime for GUID:  ${videoGUIDs[i - 1]}")

            }
        }

        log.info("Total of videos with missing runtime: " + missingRunTimes)

        if (missingRunTimes > 0) {
            runTimesShowing = false
        }
        return runTimesShowing
    }


    Boolean verifyTitles(List<String> videoGUIDs) {

        boolean titlesShowing = true
        int missingTitles = 0

        (1..videoGUIDs.size().toInteger()).each { int i ->

            By videoTitle = By.cssSelector("a[data-guid=\"${videoGUIDs[i - 1]}\"] > h2")

            log.info("Video Title : " + driver.findElement(videoTitle).getText())

            if (!isEachElementPresentAndVisible(videoTitle)) {
                missingTitles += 1
                log.info("Missing video title for GUID: ${videoGUIDs[i - 1]}")

            }
        }

        log.info ("Total videos with missing titles: " + missingTitles)

        if (missingTitles > 0) {
            titlesShowing = false
        }
        return titlesShowing
    }


    Boolean verifyThumbNailImages(List<String> videoGUIDs) {

        boolean thumbNailImagesShowing = true
        int missingThumbNailImages = 0

        (1..videoGUIDs.size().toInteger()).each { int i ->

            WebElement thumbNailImage = driver.findElement(By.cssSelector("a[data-guid=\"${videoGUIDs[i - 1]}\"] > img"))

            log.info("Image source: " + thumbNailImage.getAttribute('data-source'))

            if (!thumbNailImage.getAttribute('data-source').contains(".jpg")) {
                missingThumbNailImages += 1
                log.info("Thumbnail image is missing for GUID: ${videoGUIDs[i - 1]}")

            }

        }

        log.info("Total videos with missing thumbnail image " + missingThumbNailImages)

        if (missingThumbNailImages > 0) {
            thumbNailImagesShowing = false
        }
        return thumbNailImagesShowing
    }


    Boolean verifyNewDiscoverVideosOnPage(List<String> videoGUIDs) {

        return verifyRunTimes(videoGUIDs) && verifyTitles(videoGUIDs) && verifyThumbNailImages(videoGUIDs)

    }


    Boolean verifyEachNewDiscoverVideoDetailsPage(List<String> videoGUIDs) {

        Boolean videoDetails = true

        (1..videoGUIDs.size().toInteger()).each { int i ->

            currentGUID = videoGUIDs[i - 1]

            clickOnSpecifiedVideo(currentGUID)

            if (!verifyVideoDetails()) {

                videoDetails = false

            }
        }

        return videoDetails

    }


    void clickOnSpecifiedVideo(String guid) {

        WebElement video = driver.findElement(By.cssSelector("a[data-guid=\"${guid}\"]"))

        video.click()

        getBackgroundImagesUrl()

    }


    void getBackgroundImagesUrl() {

        WebElement backgroundImg = driver.findElement(By.cssSelector(".bonusBody"))

        String backgroundUrl = backgroundImg.getAttribute("style")

        backgroundUrl = backgroundUrl.split(/\"/)[1].split(/\"/)[0]

        imgUrls.add(backgroundUrl)

    }

    boolean verifyEachNewDiscoverVideoHeroImage(List<String> videoGUIDs) {

        Boolean imageUrl = true


        (1..videoGUIDs.size().toInteger()).each { int i ->

            String imgURL = imgUrls[i - 1]
            By heroImgSrc = By.cssSelector(".decoded")

            log.info("Hero img URL: [ **  " + imgURL + " ** ]")

            if (imgUrls[i - 1] != '') {

                javascriptExecutor.executeScript(("javascript:window.open('${imgURL}', '_blank');"))

                ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles())

                driver.switchTo().window(tabs.get(1))

                sleep 15000

                if (!isElementPresentAndVisible(heroImgSrc)) {
                    log.info("****************  Missing Hero Image  *********************")
                    log.info("Video with GUID + ${videoGUIDs[i - 1]} is missing hero image")
                    log.info("****************  ------------------  *********************")

                    imageUrl = false

                    assert imageUrl.equals(driver.findElement(heroImgSrc).getAttribute("src")): "URL : ${imgURL} is missing Hero image "

                    sleep 5000
                }

                driver.close()

                driver.switchTo().window(tabs.get(0))

            } else {

                sleep 5000
                log.info("****************  Missing Hero Image  *********************")
                log.info("Video with GUID: [ ${videoGUIDs[i - 1]} ] is missing hero image")
                log.info("****************  ------------------  *********************")
                imageUrl = false

                assert imageUrl: "Hero image URL is missing"
            }

            return imageUrl


        }
    }


    Boolean verifyVideoDetails() {

        Boolean elementPresent = true

        List<WebElement> videoDetailsElements = [notFavorite, playButton, videoDetailsTitle, videoDescription, videoDetailsRuntime]
        WebElement backgroundImg = driver.findElement(By.cssSelector(".bonusBody"))

        waitUntilElementIsPresentAndVisible(notFavorite)

        String currentTitle = driver.findElement(videoDetailsTitle).getText()
        for (videoDetailsElement in videoDetailsElements) {

            if (!isElementPresentAndVisible(videoDetailsElement)) {

                elementPresent = false
                break
            }
        }

        String backgroundUrl = backgroundImg.getAttribute("style")

        backgroundUrl = backgroundUrl.split(/\"/)[1].split(/\"/)[0]

        if (backgroundUrl == '') {

            getCurrentVideoTitle()
            log.info( "Hero URL is empty ${currentTitle}")
            elementPresent = false


        }

        return elementPresent
    }


    String getCurrentGUID() {

        return currentGUID
    }

    String getCurrentVideoTitle() {

        return currentTitle
    }


}
