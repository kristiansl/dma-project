package studiotech.pages

import com.disney.studio.qa.stbx.pageobject.Page
import com.disney.studio.qa.stbx.pageobject.SelectBy
import groovy.util.logging.Slf4j
import org.eclipse.jetty.util.log.Log
import org.junit.Assert
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.remote.server.handler.ClickElement
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.Select
import studiotech.dsl.Guest
import studiotech.environment.TestEnvironment

@Slf4j
class AccessPage extends DmaBasePage {


    List<String> accountSettingsCloseCaptionOptions
    List<String> accountHistoryEntitlements
    private final String BASIC_ACCESS_CONTROL_BASE = 'span#Limited-access-check'
    private final String PG13_CHECKBOX_BASE = "div#ratings label[for='pg-13-cb'] span"
    private final String TVPG_CHECKBOX_BASE = "div#ratings label[for='TV-PG-cb'] span"
    private final String PG_CHECKBOX_BASE = "div#ratings label[for='PG-cb'] span"
    private final String NR_CHECKBOX_BASE = "div#ratings label[for='NR-cb'] span"
    private final String UNCHECKED_CSS_CLASS = '.icon-checkbox'
    private final String CHECKED_CSS_CLASS = '.icon-checkbox-checked'
    private final String SAVE_BUTTON = 'form#accessForm button#submitButton'
    private final String LIGHTBOX = "//*[@id='disneyid-lightbox']"
    private final String SETTINGS_LINK_LOCATOR = '#settings div'
    private final String ACCOUNT_SETTINGS_LINK = '#nav-settings-account a'
    private final String VUDU_CONTINUE_BUTTON = "#openProviderModal div div div:nth-of-type(3) a"
    private final String VUDU_AGREE_BUTTON = ".custom-button-center"
    private final String VUDU_USERNAME = "div.vudu-text-box-with-label.email input"
    private final String VUDU_PASSWORD = "div.vudu-text-box-with-label.password.vudu-password-text-box-with-label input"
    private final String VUDU_SIGNIN_BUTTON = "//table[@class='green-button custom-button']/tbody/tr/td[text()='Sign In']"
    private final String VUDU_CONNECTACCOUNTS_BUTTON = ".custom-button-center"
    private final String VIEW_MYCOLLECTION = "#connectSuccess a"
    private final String VUDU_CONNECT_CHECKBOX = "label[for='VUDU-cb'] span"
    private final String DISCONNECT_BUTTON =  "#account-unlink .btn-primary"  // "#account-unlink div div div:nth-of-type(3) a"
    private final String DISCONNECT_OK_BUTTON = "#account-unlink-success strong"
    private final String ACCOUNT_HISTORY = "a[data-analytics-cta=purchaseHistory]"
    private final String ACCOUNT_HISTORY_ENTITLEMENTS = ".poster a"
    private final String ACCOUNT_HISTORY_ENTITLEMENTS_COUNT = "div.row.header-count span"
    private final String PROVIDER_PROMO = "div.provider.promo"
    private final String PURCHASE_DATE = "div.row.info-row  div.col-xs-8.title-info  div.purchase-date"
    private final String CLOSE_CAPTIONING = "a[data-analytics-cta=closedCaptioning]"
    private final String CLOSE_CAPTIONING_FORM = "#closed-captioning-form fieldset.row"
    private final String CLOSE_CAPTION_OPTIONS = "#closed-captioning-form fieldset.row legend.col-xs-6 span"
    private final String CLOSE_CAPTION_HEADER = "div.row h1.hidden-sm.hidden-xs"
    private final String CLOSE_CAPTION_PREVIEW_HEADER = "div.row h2.col-xs-2"
    private final String CLOSE_CAPTIONING_SAVE = "div.row div button.btn.btn-lg.yellow-btn.save"
    private By providersList = By.cssSelector('#provider-list>li')
    private final String VUDU_CHECKBOX = "//label[text()='VUDU']/span"
    private By vuduCheckBox = By.xpath(VUDU_CHECKBOX)
    private By saveButton = By.cssSelector(SAVE_BUTTON)
    private By lightbox = By.xpath(LIGHTBOX)
    private By settingsLink = By.cssSelector(SETTINGS_LINK_LOCATOR)
    private By accountSettingsLink = By.cssSelector(ACCOUNT_SETTINGS_LINK)
    private By vuduContinueButton = By.cssSelector(VUDU_CONTINUE_BUTTON)
    private By vuduAgreeButton = By.cssSelector(VUDU_AGREE_BUTTON)
    private final By vuduUsername = By.cssSelector(VUDU_USERNAME)
    private final By vuduPassword = By.cssSelector(VUDU_PASSWORD)
    private final By vuduSignInButton = By.xpath(VUDU_SIGNIN_BUTTON)
    private final By vuduConnectAccountsButton = By.cssSelector(VUDU_CONNECTACCOUNTS_BUTTON)
    private final By vuduSuccessConnectButton = By.cssSelector(VIEW_MYCOLLECTION)
    private final By vuduConnectCheckBox = By.cssSelector(VUDU_CONNECT_CHECKBOX)
    private final By disconnectButton = By.cssSelector(DISCONNECT_BUTTON)
    private final By disconnectOkButton = By.cssSelector(DISCONNECT_OK_BUTTON)
    private final By accountHistoryLink = By.cssSelector(ACCOUNT_HISTORY)
    private final By accounthistoryEntitlements = By.cssSelector(ACCOUNT_HISTORY_ENTITLEMENTS)
    private final By accountHistoryEntitlementsCount = By.cssSelector(ACCOUNT_HISTORY_ENTITLEMENTS_COUNT)
    private final By providerPromo = By.cssSelector(PROVIDER_PROMO)
    private final By purchaseDate = By.cssSelector(PURCHASE_DATE)
    private final By closeCaptioning = By.cssSelector(CLOSE_CAPTIONING)
    private final By closeCaptioningForm = By.cssSelector(CLOSE_CAPTIONING_FORM)
    private final By closeCapOptions = By.cssSelector(CLOSE_CAPTION_OPTIONS)
    private final By closeCaptionHeader = By.cssSelector(CLOSE_CAPTION_HEADER)
    private final By closeCaptionPrewiewHeader = By.cssSelector(CLOSE_CAPTION_PREVIEW_HEADER)
    private final By closeCaptionSave = By.cssSelector(CLOSE_CAPTIONING_SAVE)
    private final updateProfile = By.id("updateProfile")
    private final By closeCaptionPreview = By.id("preview")
    private final By closeCaptionReset = By.id("reset")
    private Map<String, String> checkboxes = new HashMap<String, String>()
    private final By vuduSuccessMessage = By.cssSelector('.success-message')
    private final String vuduSuccessMessageTxt = "Your VUDU and Disney accounts are ready to be connected."
    private final By loadingModal = By.cssSelector(".loading.modal-centered")
    private final By vuduLinked = By.cssSelector("#vudu-cb[checked='checked']")


    AccessPage(WebDriver driver) {
        super(driver)
        checkboxes.put('PG-13', PG13_CHECKBOX_BASE)
        checkboxes.put('TV-PG', TVPG_CHECKBOX_BASE)
        checkboxes.put('PG', PG_CHECKBOX_BASE)
        checkboxes.put('NR', NR_CHECKBOX_BASE)
        accountSettingsCloseCaptionOptions = []
        accountHistoryEntitlements = []
    }

    @Override
    protected void loadPage() {

    }

    @Override
    protected void isPageLoaded() throws Error {

    }
/**
 * Enables access restrictions and rating restrictions. Enables the
 * basic access restrictions if it is not already restricted. If ratings
 * are supplied, those ratings will also be selected for restriction
 *
 * @param ratings
 * 			the ratings to restrict
 */
    public void enableAccessRestrictions(TestEnvironment environment, String... ratings) {

        if (driver.findElements(By.cssSelector(BASIC_ACCESS_CONTROL_BASE + UNCHECKED_CSS_CLASS)).size() > 0) {
            // if not already access restricted, click it
            driver.findElement(By.cssSelector(BASIC_ACCESS_CONTROL_BASE + UNCHECKED_CSS_CLASS)).click()
            actionTimeOut()

        }

        for (String rating : ratings) {
            if (checkboxes.containsKey(rating) && driver.findElements(By.cssSelector(checkboxes.get(rating) + UNCHECKED_CSS_CLASS)).size() > 0) {
                // if not already restricted, click it
                driver.findElement(By.cssSelector(checkboxes.get(rating) + UNCHECKED_CSS_CLASS)).click()
            }
        }

        clickSaveButton(environment)
    }

    /**
     * Disables access restrictions and rating restrictions. Disables the
     * basic access restrictions if the disableBasicAccess is true and it
     * is restricted. This will make rating restrictions also be removed.
     * If disableBasicAccess is false, then just the ratings that
     * are supplied will be unselected for restriction
     *
     * @param disableBasicAccess
     * 			true if all restrictions should be removed, false if just
     * 			the ratings provided should be removed
     * @param ratings
     * 			the ratings to unrestrict
     * @return
     * true if the Disney ID lightbox has popped up and is waiting
     * 			for login credentials
     */
    public boolean disableAccessRestrictions(boolean disableBasicAccess, TestEnvironment environment, String... ratings) {
        if (disableBasicAccess && driver.findElements(By.cssSelector(BASIC_ACCESS_CONTROL_BASE + CHECKED_CSS_CLASS)).size() > 0) {
            // if not already access restricted, click it
            driver.findElement(By.cssSelector(BASIC_ACCESS_CONTROL_BASE + CHECKED_CSS_CLASS)).click()
            // disabling the basic access negates the ratings, no need to uncheck
        } else if (!disableBasicAccess) {
            // not disabling the basic access, just remove the rating checkboxes
            for (String rating : ratings) {
                if (checkboxes.containsKey(rating) && driver.findElements(By.cssSelector(checkboxes.get(rating) + CHECKED_CSS_CLASS)).size() > 0) {
                    // if not already restricted, click it
                    driver.findElement(By.cssSelector(checkboxes.get(rating) + CHECKED_CSS_CLASS)).click()
                }
            }
        }

        return clickSaveButton(environment)
    }

    /**
     * Clicks the save preferences button. Clicks the button and
     * will check if the user needs to provide login credentials
     *
     * @return
     * true if the disney ID lightbox is shown; otherwise, false
     */
    public boolean clickSaveButton(TestEnvironment testEnvironment) {

        String environment = testEnvironment

        if (!driver.findElement(saveButton).getAttribute("disabled")) {
            clickButton(saveButton)
        }

        if (environment.equalsIgnoreCase("load")) {
            // Login iframe name for Load Environment is different
            //disneyid-responder-message-https-load-disneymoviesanywhere-com-didresponder-load-html
            return driver.findElements(By.cssSelector("#disneyid-responder-message-https-$environment-disneymoviesanywhere-com-didresponder-$environment-html")).size() > 0
        }
        return driver.findElements(By.cssSelector("#disneyid-responder-message-https-$testEnvironment.environment-disneymoviesanywhere-com-didresponder-html")).size() > 0
    }

    void goToAccountSettings() {
        clickLink(settingsLink)
        clickLink(accountSettingsLink)
    }


    boolean connectAccountWithVudu(String username, String password) {
        boolean canConnect
        waitUntilElementIsPresentAndVisible(vuduCheckBox, 20)
        WebElement checkbox = driver.findElement(vuduCheckBox)
        if (!isElementPresent(vuduLinked)) {
            checkbox.click()
            waitUntilElementIsPresentAndVisible(vuduContinueButton, 20)
            clickButton(vuduContinueButton)
            def vuduPage = driver.getWindowHandles()[1]
            driver.switchTo().window(vuduPage);
            waitUntilElementIsPresentAndVisible(vuduAgreeButton, 20)
            clickButton(vuduAgreeButton)
            waitUntilElementIsPresentAndVisible(vuduUsername, 20)
            sendText(vuduUsername, username)
            sendText(vuduPassword, password.capitalize())
            clickButton(vuduSignInButton)
            waitUntilTextIsPresent(vuduSuccessMessage, vuduSuccessMessageTxt)
            driver.findElement(vuduConnectAccountsButton).click()
            canConnect = true
        } else {
            log.info("User already Connected")
            canConnect = false
        }
        return canConnect
    }

    boolean accountConnectedWithVudu() {

        wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingModal))
        return isElementPresentAndVisible(vuduLinked)
    }

    boolean disconnectAccountWithVudu() {
        boolean canDisconnect
        waitUntilElementIsPresentAndVisible(providersList, 40)
        if (isElementPresent(vuduLinked)) {
            driver.findElement(vuduConnectCheckBox).click()
            waitUntilElementIsPresentAndVisible(disconnectButton, 40)
            clickButton(disconnectButton)
            waitUntilElementIsPresentAndVisible(disconnectOkButton, 40)
            clickButton(disconnectOkButton)
            canDisconnect = true
        } else {
            log.info("Already disconnected")
            canDisconnect = false
        }
        return canDisconnect
    }

    boolean accountDisconnectedWithVudu() {
        waitUntilElementIsPresentAndVisible(vuduCheckBox)
        WebElement checkbox = driver.findElement(vuduCheckBox)
        if (!isElementPresent(vuduLinked)) {
            log.info("Account disconnected to vudu")
            println("Account disconnected to vudu")
            return true
        }
        return false
    }

    void clickOnAccountProfile() {
        waitUntilElementIsPresentAndVisible(updateProfile)
        clickLink(updateProfile)
    }

    void clickOnAccountHistory() {
        waitUntilElementIsPresentAndVisible(accountHistoryLink)
        clickLink(accountHistoryLink)
    }

    boolean accountHistoryDetails() {
        //Account history details purchase date and provider promo
        waitUntilEachElementIsPresentAndVisible(providerPromo)
        isElementPresentAndVisible(purchaseDate)
    }

    boolean accountHistoryPurchasedEntitlements(List entitlements) {
        def url = driver.getCurrentUrl().split("setting/account/purchase-history")[0]
        def accountHistoryMoviesUrl = url + "movie/"

        List<WebElement> webEntitlements = driver.findElements(accounthistoryEntitlements)
        //Entitlements from account history
        def numberOfEntitlements = webEntitlements.size()
        def entitlementsCount = driver.findElement(accountHistoryEntitlementsCount).getText().split(" ")[0]
        //Account history entitlements counter)
        if (numberOfEntitlements == entitlementsCount) {
            log.info("Displayed entitlements matches total number of entitlements")
        }
        for (webEntitlement in webEntitlements) {
            accountHistoryEntitlements.add((webEntitlement.getAttribute("href").split(accountHistoryMoviesUrl)[1]))
        }
        if ((entitlements.sort()).equals(accountHistoryEntitlements.sort())) {
            return true
        }
        return false
    }

    void clickOnCloseCaption() {
        waitUntilElementIsPresentAndVisible(closeCaptioning)
        clickLink(closeCaptioning)
    }

    boolean closeCaptioningDetails() {
        List<WebElement> closeCaptionDetails = [closeCaptionHeader, closeCaptionReset, closeCaptionPrewiewHeader, closeCaptionSave, closeCaptionPreview]
        //Close captioning page details
        isEachElementDisplayed(closeCaptionDetails)
    }

    boolean verifyCloseCaptionOptions(List<String> closeCaptionOptions) {
        waitUntilElementIsPresentAndVisible(closeCaptioningForm)
        List<WebElement> closeCaptioningOptions = driver.findElements(closeCapOptions)
        //Close caption options in account settings
        for (closeCapOpt in closeCaptioningOptions) {
            accountSettingsCloseCaptionOptions.add(closeCapOpt.getText())
        }
        if ((closeCaptionOptions.sort()).equals(accountSettingsCloseCaptionOptions.sort())) {
            return true
        }
        return false
    }
}

//void editCloseCaptionOptions() {
//
//        List<String> editLinks = []
//        List<String> closeLinks = []
//
//        def fontBeforeEdit = driver.findElement(fontType).getText()
//        println(fontBeforeEdit)
//
//        List<WebElement> editOptions = driver.findElements(closeCapOptionEdit)
//        for (editOption in editOptions) {
//            editLinks.add(editOption.click())
//        }
//
//        waitUntilElementIsPresentAndVisible(selectFontType)
//        Select text = new Select(driver.findElement(selectFontType))
//        text.selectByIndex(3)
//
//        driver.findElement(closeCaptionSave).click()
//
//        List<WebElement> closeOptions = driver.findElements(closeCapOptionClose)
//        for (closeOption in closeOptions) {
//            closeLinks.add(closeOption.click())
//        }
//
//        def fontAfterEdit = driver.findElement(fontType).getText()
//        println(fontAfterEdit)
//
//        if(fontBeforeEdit.equalsIgnoreCase(fontAfterEdit)){
//            println("Not updated")
//        }
//        else {
//            println("Updated")
//        }

//   }
