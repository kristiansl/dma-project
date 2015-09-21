package studiotech.steps

import cucumber.api.PendingException;

import static cucumber.api.groovy.EN.*


Then(~'^guest presented with contact us form') { ->
   assert dmaDSL.isContactFormDisplayed() : "Contact form is not displayed"
   assert dmaDSL.isContactCategoryDisplayed() : "Contact Category is not displayed or some options are missing"
   assert dmaDSL.isTermsOfUse() : "Contact form is missing Terms of Use label  or check box"
   assert dmaDSL.isSendButton() : "Submit button is missing"
}

And(~'^have not entered any text'){ ->
    assert dmaDSL.isFormBodyEmpty() : "Some text is entered in form body or place holder changed"
}

And(~'^have entered contact text'){ ->
    dmaDSL.enterTextToForm()
}

Then(~'^guest will be able to submit form') { ->
assert !dmaDSL.isSendButtonDisabled() : "Send button is not disabled"
}

Then(~'^guest will not be able to submit form') { ->
    assert dmaDSL.isSendButtonDisabled() : "Send button is not disabled"
}

And(~'^submits contact us form without agreeing terms of use$') { ->
    dmaDSL.enterTextToForm()
    dmaDSL.clickSendButton()
}


Then(~'^error message is displayed to agree terms$') { ->
    assert dmaDSL.isAgreeTermsErrorMessageDetailsDisplayed() : "Agree to terms error message details are not displayed"
    assert dmaDSL.isTermsErrorMessageVerified() : "Agree terms error message is not displayed properly"
}


When(~'^the guest start entering in the comment box$') { ->
    dmaDSL.enterTextToForm()
    dmaDSL.switchToSignInFrame()
}
Then(~'^the system should prompt to sign in$') { ->
    assert dmaDSL.isUsernameTextBoxDisplayed() : 'Sign in form not displayed'
}