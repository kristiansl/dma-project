package studiotech.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class ContactPage extends DmaBasePage {

    private final String CONTACT_PAGE_TITLE = "Contact | Disney Movies Anywhere"       // Title
    private final String TERMS_OF_USE_TXT = "Yes, I agree to Terms Of Use"             // Term of Use label text
    private final String FORM_BODY_PLACEHOLDER_TXT = "Comments..."                     // Form body placeholder text
    private final String SEND_BTN_STATE = "disabled"                                   // default state for Send button
    private final String CONTACT_PAGE_TXT = "Contact"                                  // Header text
    private final String AGREE_TERMS_ERROR_MESSAGE_TEXT = "You must check the box to agree to the Terms of Use before you can send your comments."
    private final By contactCategoryBtn = By.cssSelector("#contact-category-btn")
    // Contact category button locator
    private final By contactPageTxt = By.cssSelector(".col-xs-12>h1")                             // Header text locator
    private final By elmContactCategory = By.cssSelector("#contactus-category-dropdown>ul>li>a")
    private final By checkBoxTermsOfUse = By.cssSelector(".icon-checkbox")             // Check box for terms of use
    private final By lblTermsOfUse = By.cssSelector("label[for=\"contact-terms\"]")    // Term
    private final By submitButton = By.cssSelector("#submitButton")                    // Submit button
    private final By formBody = By.cssSelector("#body")                               // Form textarea
    private final By termsErrorMessage = By.id("modal-header-text")
    private final By termsErrorMessageDescription = By.cssSelector(".modal-body")
    private final By termsErrorMessageOkButton = By.id("contactTermsModalOk")
    private final By termsErrorMedssageHeader = By.cssSelector(".modal-header")


    ContactPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {

    }

    @Override
    protected void isPageLoaded() throws Error {

    }

    boolean contactUsForm() {


        if (driver.getTitle().equalsIgnoreCase(CONTACT_PAGE_TITLE)) {
            return isTextPresent(contactPageTxt, CONTACT_PAGE_TXT)
        }
        return false
    }

    boolean contactUsCategory(List<String> expContactUsCategories) {

        driver.findElement(contactCategoryBtn).click()

        for (expContactUsCategory in expContactUsCategories) {
        }

        List<WebElement> webContactUsCategories = driver.findElements(elmContactCategory)
        List<String> webContactCategories = []
        for (webContactUsCategory in webContactUsCategories) {
            webContactCategories.add(webContactUsCategory.getText())
        }
        // Verifying contact category list
        if (expContactUsCategories == webContactCategories) {
            return true
        }
        return false
    }

    boolean termsOfUse() {

        // Verifying if check box is present
        if (isElementPresentAndVisible(checkBoxTermsOfUse)) {
            // Verifying if Text is present
            return isTextPresent(lblTermsOfUse, TERMS_OF_USE_TXT)
        }
        return false
    }

    boolean submitButton() {

        return isElementPresent(submitButton)
    }

    boolean formBodyEmpty() {
        String placeholder = driver.findElement(formBody).getAttribute("placeholder")

        if (placeholder.equalsIgnoreCase(FORM_BODY_PLACEHOLDER_TXT)) {
            return true
        }
        return false
    }

    boolean submitButtonDisabled() {

        return driver.findElement(submitButton).getAttribute("disabled")
    }

    void enterTextToFormBody(){
        if(formBodyEmpty()){
            driver.findElement(formBody).sendKeys("Automation Test")
        }
    }

    void clickOnSendButton() {
        clickButton(submitButton)
    }

    boolean contactUsFormTermsErrorMessage() {                                                                          //Error message is displayed when form is sent without agreeing terms
        List<WebElement> agreeTermsErrorMessageDetails = [termsErrorMedssageHeader ,termsErrorMessageDescription,termsErrorMessageOkButton]
        waitUntilElementIsPresentAndVisible(termsErrorMessage)
        isEachElementDisplayed(agreeTermsErrorMessageDetails)
    }

    boolean verifyAgreeTermsErrorMessage(){
        if(driver.findElement(termsErrorMessageDescription).getText().equals(AGREE_TERMS_ERROR_MESSAGE_TEXT)) {
            return true
        }
        return false
    }

}
