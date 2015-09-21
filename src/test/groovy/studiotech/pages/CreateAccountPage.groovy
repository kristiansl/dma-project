package studiotech.pages

import com.disney.studio.qa.stbx.pageobject.SelectBy
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.RandomStringUtils
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select
import studiotech.environment.TestEnvironment
import java.text.SimpleDateFormat

@Slf4j
class CreateAccountPage extends DmaBasePage{

    private String password
    private String guestFirstName
    private String guestMiddleName
    private String guestLastName
    private String gender
    private String postalCode
    private String month
    private String year
    private String day

    private final String NOT_ELIGIBLE_TO_CREATE_ACCOUNT_TEXT = "You are not eligible to register on this site. Your information has not been collected."
    private final By homePageSignInButton = By.cssSelector("li#nav-signin a")
    //private final By signInButtonFrame = By.id("disneyid-iframe")
    private final By birthMonth = By.id("birthdate-month")
    private final By birthDay = By.id("birthdate-day")
    private final By birthYear = By.id("birthdate-year")
    private final By continueButton = By.id("create-account-from-login")
    private final By dateOfBirthErrorMessage = By.cssSelector(".alertblock-dob-select")
    private final By emailId = By.id("email")
    private final By createAccountPassword = By.id("password")
    private final By reTypePassword = By.id("password-check")
    private final By firstName = By.id("firstName")
    private final By middleName = By.id("middleName")
    private final By lastName = By.id("lastName")
    private final By createAccountGender = By.id("gender")
    private final By createAccountPostalCode = By.id("addresses_0_postalCode")
    private final By createAccountButton = By.id("create-account")
    private final By createAccountCheckboxes = By.cssSelector("input[name=terms]")
    private final By accountCreationMessage = By.cssSelector(".green-check.value>strong")
    private final By passwordErrorMessage = By.cssSelector(".alertblock-password")
    private final By passwordCheckErrorMessage = By.cssSelector(".alertblock-password-check")
    private final By firstNameErrorMessage = By.cssSelector(".alertblock-firstName")
    private final By lastNameErrorMessage = By.cssSelector(".alertblock-firstName")
    private final By postalCodeErrorMessage = By.cssSelector(".alertblock-addresses_0_postalCode")
    private final By legalOptionsErrorMessage = By.cssSelector(".alertblock-legalOptions")
    private final By accountCreationErrorMessage = By.cssSelector(".alertblock-create-account")
    private final By notEligibleToCreateAccountMessage = By.cssSelector("#disneyid-messagediv>p")
    private final By backToSiteButton = By.id("done-button")
    private final By settingMenuItem = By.cssSelector(".icon-account")
    private final By SignOutMenuItem = By.cssSelector(".signout")
    private final By updateAccountButton = By.id("update-account")
    private final By disneyIdFormCloseButton = By.id("disneyid-close")
    private final By userName = By.id("username")


    CreateAccountPage(WebDriver driver , TestEnvironment testEnvironment) {

        super(driver)
        password = (testEnvironment.accountDetails.find {it.key == 'password'}).value
        guestFirstName = (testEnvironment.accountDetails.find {it.key == 'firstName'}).value
        guestMiddleName = (testEnvironment.accountDetails.find {it.key == 'middleName'}).value
        guestLastName = (testEnvironment.accountDetails.find {it.key == 'lastName'}).value
        gender = (testEnvironment.accountDetails.find {it.key == 'gender'}).value
        postalCode = (testEnvironment.accountDetails.find {it.key == 'postalCode'}).value
        month = (testEnvironment.accountDetails.find {it.key == 'birthMonth'}).value
        year = (testEnvironment.accountDetails.find {it.key == 'birthYear'}).value
        day = (testEnvironment.accountDetails.find {it.key == 'birthDay'}).value

    }

    @Override
    protected void loadPage() {

    }

    @Override
    protected void isPageLoaded() throws Error {

    }

    void enterBirthDayAndYearDetails() {
        waitUntilElementIsPresentAndVisible(birthDay)
        selectRequiredDateFields(false, true, true)
    }

    void enterBirthMonthAndYearDetails() {
        waitUntilElementIsPresentAndVisible(birthYear)
        selectRequiredDateFields(true, false, true)
    }

    void enterBirthDayAndMonthDetails() {
        waitUntilElementIsPresentAndVisible(birthMonth)
        selectRequiredDateFields(true, true, false)
    }

    boolean birthDateErrorMessage() {
        isElementPresentAndVisible(dateOfBirthErrorMessage)
    }

    void enterDateOfBirthDetails() {
        waitUntilElementIsPresentAndVisible(birthMonth)
        selectRequiredDateFields(true, true, true)
    }

    void selectDateOfBirthForEligibilityCheck(int age){

        year  =  Calendar.getInstance().get(Calendar.YEAR)- age
        month =  Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
        day   =  Calendar.getInstance().get(Calendar.DATE)

        selectRequiredDateFields(true, true, true)
    }

    /**
     * Specify true or false when you want to select Month, Day or Year
     * @param selectMonth  true / false
     * @param selectDay    true / false
     * @param selectYear   true / false
     */

    void selectRequiredDateFields(Boolean selectMonth, Boolean selectDay, Boolean selectYear){

        // Select Day
        if (selectDay == true) {
            select(birthDay,SelectBy.VISIBLE_TEXT, day)
        }
        //Select Month
        if (selectMonth == true){
            select(birthMonth ,SelectBy.VISIBLE_TEXT ,month)
        }
        //Select Year
        if(selectYear == true){
            select(birthYear,SelectBy.VISIBLE_TEXT ,year)
        }
    }

    void clickContinueButtonToCreateAccount() {
        waitUntilElementIsPresentAndVisible(continueButton)
        clickButton(continueButton)
    }

    boolean errorMessagesAccountCreation() {
        List<WebElement> expErrorMessages = [passwordCheckErrorMessage, firstNameErrorMessage,lastNameErrorMessage, postalCodeErrorMessage,
                                             legalOptionsErrorMessage ,accountCreationErrorMessage]
        def charsetErrPassword = (('A'..'Z') + ('0'..'9')).join('')
        Integer errPasswordLength = 5
        String errPassword = RandomStringUtils.random(errPasswordLength, charsetErrPassword.toCharArray())
        sendText(createAccountPassword , errPassword)
        driver.findElement(createAccountPassword).sendKeys(Keys.TAB)
        waitUntilElementIsPresentAndVisible(passwordErrorMessage)
        clickButton(createAccountButton)
        waitUntilElementIsPresentAndVisible(passwordCheckErrorMessage)
        isEachElementDisplayed(expErrorMessages)
    }

    void enterFieldsToCreateAccount(String username) {

        def timeStamp = System.currentTimeMillis()
        def email = timeStamp + username
        sendText(emailId , email)
        sendText(createAccountPassword ,password )
        sendText(reTypePassword , password)
        sendText(firstName,guestFirstName)
        sendText(middleName,guestMiddleName)
        sendText(lastName,guestLastName)
        select(createAccountGender ,SelectBy.VISIBLE_TEXT,gender)
        sendText(createAccountPostalCode , postalCode)
        List<WebElement> checkBoxList=driver.findElements(createAccountCheckboxes)
        for(checkBox in checkBoxList)
        {
            checkBox.click()
        }

    }

    void clickOnCreateAccountButton() {
        clickButton(createAccountButton)
    }

    boolean accountCreatedMessageDisplayed(String message) {
        waitUntilElementIsPresent(accountCreationMessage)
           if( driver.findElement(accountCreationMessage).getText().equals(message)){
               return true
           }
        return false
    }

    void clickOnBackToSiteButton() {
        waitUntilElementIsPresentAndVisible(backToSiteButton, 15)
        clickButton(backToSiteButton)
    }

    boolean verifyAccountCreation() {
        clickButton(settingMenuItem)
        isElementPresentAndVisible(SignOutMenuItem)
    }

    void clickOnSignInButton(){
        clickButton(homePageSignInButton)
    }

    void switchToSignInFrame(){
        driver.switchTo().frame("disneyid-iframe")
        waitUntilElementIsPresentAndVisible(birthMonth)
    }

    boolean verifyAccountCreationFormIsDisplayed() {
        if(isElementPresentAndVisible(emailId)){
            log.info("Eligible to create account")
            return true
        }
        return false
    }

    boolean verifyNotEligibleAgeToCreateAccount(){
        waitUntilElementIsPresentAndVisible(notEligibleToCreateAccountMessage)
        if( driver.findElement(notEligibleToCreateAccountMessage).getText().equals(NOT_ELIGIBLE_TO_CREATE_ACCOUNT_TEXT)) {
            log.info(driver.findElement(notEligibleToCreateAccountMessage).getText())
            return true
        }
        return false
    }

    void switchToAccountCreationDetailsPage(){
        driver.switchTo().frame("disneyid-iframe")
        waitUntilElementIsPresentAndVisible(middleName)
    }

    void updateGuestProfile() {
        switchToAccountCreationDetailsPage()
        driver.findElement(middleName).clear()
        sendText(middleName ,guestMiddleName)
        clickButton(updateAccountButton)
        driver.switchTo().defaultContent()
        waitUntilElementIsPresentAndVisible(disneyIdFormCloseButton)
        clickButton(disneyIdFormCloseButton)
    }

    boolean verifyUpdatedProfile(){
        switchToAccountCreationDetailsPage()
        String updatedMiddleName = driver.findElement(middleName).getAttribute("value")
        if(updatedMiddleName.equals(guestMiddleName)){
            return true
        }
        return false
    }

    boolean usernameTextBoxPresent(){
        isElementPresentAndVisible(userName)
    }

}
