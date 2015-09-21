package studiotech.steps

import cucumber.api.PendingException;

import static cucumber.api.groovy.EN.*

Given(~'^a guest has not signed in$') { ->
    assert dmaDSL.isSignInMenuItemDisplayed() : "The Sign In link is missing!!"
}

When(~'^a guest signs in with their Disney account credentials$') { ->
    dmaDSL.signInAGuest(testEnvironment.guest)
}

Given(~'^a new user wishes to create a Disney Account$') {->
    dmaDSL.openBaseUrl()
    assert dmaDSL.isSignInMenuItemDisplayed() : "The Sign In link is missing!!"
}

And(~'^the user continues to create a Disney account$') { ->
    dmaDSL.clickContinueButton()
}

Then(~'^"The birth date selected is not a valid date" message shall be displayed$') { ->
    assert  dmaDSL.isBirthDateErrorMessagesDisplayed() : "Birth date error message not displayed"
}

When(~'^the required information is supplied$') { ->
    dmaDSL.clickSignInButton()
    dmaDSL.switchToSignInFrame()
    dmaDSL.enterDateOfBirth()
    dmaDSL.clickContinueButton()
    assert dmaDSL.isAccountCreationErrorMessagesDisplayed() : "Account Creation - Error messages are not displayed properly"
    dmaDSL.enterFieldsToCreateAccount(testEnvironment)
}

And(~'^the user creates an account$') {->
    dmaDSL.clickCreateAccountButton()
}

Then(~'^message: (.+) will be displayed') { String message ->
    assert dmaDSL.isAccountCreatedMessageDisplayed(message) : "Your account has been created!\" message is missing"
    dmaDSL.clickBackToSiteButton()
    assert dmaDSL.isAccountCreationVerified() : "SignOut is not displayed"
}

When(~'^the (.*) is not supplied$') {String birthDate ->
    dmaDSL.clickSignInButton()
    dmaDSL.switchToSignInFrame()
         switch (birthDate){
         case 'month' :  dmaDSL.enterBirthDayAndYear()
                         break
         case 'day'   :  dmaDSL.enterBirthMonthAndYear()
                         break
         case 'year'  :  dmaDSL.enterBirthDayAndMonth()
                         break
         case 'month, day, year' : break
         default : throw new IllegalArgumentException("The supplied, '$birthDate', information is incorrect!")
         }
}

When(~'^the user selects (.*) with (.*) years$') {String dateOfBirth ,int age ->
    dmaDSL.clickSignInButton()
    dmaDSL.switchToSignInFrame()
    switch (dateOfBirth){
        case 'eligible age'         :  dmaDSL.selectDateOfBirthForEligibilityCheck(age)
                                       break
        case 'exact eligible age'   :  dmaDSL.selectDateOfBirthForEligibilityCheck(age)
                                       break
        case 'not eligible age'     :  dmaDSL.selectDateOfBirthForEligibilityCheck(age)
                                       break
        default: throw new IllegalArgumentException("The supplied, '$dateOfBirth' '$age', is incorrect!")
    }

}

Then(~'^the account is created based on eligible (.*)$') { int age ->
    dmaDSL.clickContinueButton()
    if (age <= 12) {
        assert dmaDSL.isNotEligibleToCreateAccountMessageDisplayed(): "Not eligible to create account message is not displayed"
    }
      else {
            assert dmaDSL.isAccountCreationFormDisplayed(): " Account Creation form is not displayed"
        }
}