package studiotech.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class RewardsPage extends DmaBasePage {
    private final String MOVIE_PROCESSING_TEXT = "There was a problem with your account or code. Please log in to DisneyMovieRewards.com and try re-entering your code."

    private final String ENTER_CODE_FIELD = "input#reward-code"       // CSS Locator for Enter Code field
    private final String REWARDS_LINK = "div.rewards-bg div.container div.points-counter + a"
    // CSS Locator for Get Rewards link
    private final String POINTS_DISPLAY = "span.points"               // CSS Locator for Rewards Point display 00000
    private final String DMR_SIGN_OUT = "a[href='/member/sign-out.htm']" // CSS Locator for Sign Out link on DMR page
    private final String EXISTING_USER_USERNAME = "rewards_web@autodma.com"
    private final String EXISTING_USER_PASSWORD = "abcd1234"
    private final String REWARDS_PLAYMOVIE_MODELBOX = "#successRewardModal div"
    private final String REWARDS_MODELBOX_CLOSE_BUTTON = "div.modal-content button.close"
    private final String DUPLICATE_CODE_ERROR_MESSAGE_HEADER = "#dmrErrorTitle"

    private By enterCodeField = By.cssSelector(ENTER_CODE_FIELD)
    private By rewardsLink = By.cssSelector(REWARDS_LINK)
    private By pointsDisplay = By.cssSelector(POINTS_DISPLAY)
    private By dmrSignOut = By.cssSelector(DMR_SIGN_OUT)
    private final By rewardsPlaymovieModelbox = By.cssSelector(REWARDS_PLAYMOVIE_MODELBOX)
    private final By rewardsModelBoxCloseButton = By.cssSelector(REWARDS_MODELBOX_CLOSE_BUTTON)
    private final By duplicateCodeErrorMessageHeader = By.cssSelector(DUPLICATE_CODE_ERROR_MESSAGE_HEADER)

    private final String HEADER_TXT = "Disney Movie Rewards"
    private final By dmrHeader = By.cssSelector("h1.hidden-xs.text-center")        // Locator for rewards header
    private final By dmrLogo = By.cssSelector(".logo-dmr")              // CLocator for DMR Logo
    private final By rewardsBillboard = By.cssSelector("#billboard-1")  // Locator for first billboard
    private final By rewardsSlider = By.cssSelector(".bxslider.poster li a img") // Locator for movie slider
    private final By rewardsCodeClear = By.cssSelector("#clear-reward-code-input")
    private final By rewardsCodeSubmit = By.cssSelector("#reward-code-input")

    private final By loginButton = By.id("log-in-button")
    private final By existingUsername = By.id("username")
    private final By exiatingUserPassword = By.id("password")
    private final By rewardsCode = By.id("reward-code")
    private final By codeEnterButton = By.id("reward-code-input")
    private final By playMovieButton = By.id("successRewardModalPlay")
    private final By duplicateErrorMessageCloseButton = By.id("dmrErrorNoneBtn")
    private final By invalidCodeErrorMessage = By.id("dmrErrorTitle")
    private final By invalidCodeErrorMessageDescription = By.id("dmrErrorDescription")
    private final By invalidCodeCancelButton = By.id("dmrErrorNoneBtn")
    private final By invalidCodeTryAgainButton = By.id("dmrErrorRedirectLink")

    
    private final String SUCCESS_MODAL_HEADER_TXT = 'Digital Copy Unlocked'
    private final String PROCESSING_MODAL_HEADER_TXT = 'Movie Processing'
    
    private final By successRewardModal = By.cssSelector('#successRewardModal .modal-centered')
    private final By movieProcessing = By.cssSelector('#movieProcessing .modal-centered')
    
    private final By closeButtonSuccessModal = By.cssSelector('.modal.fade.in .close')
    private final By okButtonProcessingModal = By.cssSelector('#movieProcessingOk')
    



    RewardsPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {
        baseUrl = baseUrl.replace('/?home', '')
        driver.get("${baseUrl}/rewards")
    }

    @Override
    protected void isPageLoaded() throws Error {
        assert driver.currentUrl.contains('rewards'): "The rewards page could not be displayed!!"
    }

    boolean isGetRewardsLinkDisplayed() {
        return isElementPresentAndVisible(rewardsLink)
    }

    boolean arePointDisplayed() {
        List<WebElement> points = driver.findElements(pointsDisplay)
        Iterator itr = points.iterator()

        while (itr.hasNext()) {
            String value = itr.next().text
            if (value != '0') {
                return false
            }
        }

        return true
    }

    boolean areGuestPointsDisplayed(String guestPoints) {
        List<WebElement> elmPoints = driver.findElements(pointsDisplay)
        List<String> webPoints = []
        String points = ""

        // Get guest's points
        for (elmPoint in elmPoints) {
            webPoints.add(elmPoint.getText())
        }

        // Convert points list to points string
        for (webPoint in webPoints) {
            points += webPoint
        }

        if (points.replaceAll("^0*", "").equals(guestPoints)) {
            return true
        }
        return false
    }

    boolean enterCodeFieldDisplayed() {
        return isElementPresentAndVisible(enterCodeField)
    }

    void applyRewardsCode(String code) {
        sendText(enterCodeField, code)
    }

    void clickGetRewards() {
        clickLink(rewardsLink)
    }

    boolean getDmrUrl(String dmrURL) {
        String childWindow = (String) driver.getWindowHandles().toArray()[1];
        // Switch to child window (DMR)
        driver.switchTo().window(childWindow)
        String landingDmrUrl = driver.getCurrentUrl()
        // Verify that Guest is logged into DMR site
        isElementPresentAndVisible(dmrSignOut)

        if (landingDmrUrl.equalsIgnoreCase(dmrURL)) {
            return true
        }
        return false
    }

    boolean rewardsHeader() {
        return isTextPresent(dmrHeader, HEADER_TXT)
    }

    boolean rewardsPointsCounter() {
        return isElementPresentAndVisible(pointsDisplay)
    }

    boolean dmrLogo(){
       return isElementPresentAndVisible(dmrLogo)
    }

    boolean rewardsBillboard(){
        return isElementPresent(rewardsBillboard)
    }

    boolean rewardsMovieSlider(){
        return isElementPresentAndVisible(rewardsSlider)
    }

    boolean codeSubmitButton(){
      driver.findElement(enterCodeField).sendKeys("QA")
        return isElementPresentAndVisible(rewardsCodeSubmit)
       
    }

    boolean codeClearButton(){
        driver.findElement(enterCodeField).sendKeys("QA")
        return isElementPresentAndVisible(rewardsCodeClear)
    }


    void enterRewardsCode(String code) {
        sendText(rewardsCode , code)                                                                                    //Entering valid rewards code
        clickButton(codeEnterButton)
   }

    Boolean rewardsPlayMovieDisplayed() { 
        Boolean redemptionSuccess                                                                                //Success redemption of movie from rewards code.
        waitUntilElementIsPresent(By.cssSelector('#main-body'), 20)
        
        if(isEachElementPresentAndVisible(successRewardModal)){
            clickButton(closeButtonSuccessModal)
            redemptionSuccess = true
            
        }else if (isEachElementPresentAndVisible(movieProcessing)){
            clickButton(okButtonProcessingModal)
            redemptionSuccess = true
        }else {
            log.info("Rewards redemption failed")
            redemptionSuccess = false
        }
        return redemptionSuccess

    }

    void signInExistingUser() {
        sendText(existingUsername , EXISTING_USER_USERNAME)
        sendText(exiatingUserPassword, EXISTING_USER_PASSWORD)
        clickButton(loginButton)
    }

    boolean duplicateCodeMessageDisplayed() {                                                                           //Duplicate code error message is displayed for the existing user when he uses rewards code.
        List<WebElement> duplicateCodeErrorMessageDetails = [duplicateCodeErrorMessageHeader ,duplicateErrorMessageCloseButton]
        isEachElementDisplayed(duplicateCodeErrorMessageDetails)
    }

    void enterInvalidRewardsCode(String invalidCode) {
        sendText(rewardsCode , invalidCode)                                                                             //Entering invalid rewards code
        clickButton(codeEnterButton)
    }

    boolean invalidCodeErrorMessage() {
        List<WebElement> invalidCodeErrorMessageDetails = [invalidCodeErrorMessageDescription ,invalidCodeTryAgainButton ,invalidCodeCancelButton]
        waitUntilElementIsPresentAndVisible(invalidCodeErrorMessage)                                                    //Invalid Code error message is displayed when invalid code is entered
        isEachElementDisplayed(invalidCodeErrorMessageDetails)
    }


}

