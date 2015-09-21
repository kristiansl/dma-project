package studiotech.steps;

import static cucumber.api.groovy.EN.*
import static cucumber.api.groovy.Hooks.*

this.metaClass.mixin (cucumber.api.groovy.Hooks)

Given(~'^an authenticated guest has basic limited access turned (on|off)$') {  String accessOnOff ->
    // Express the Regexp above with the code you wish you had
    dmaDSL.signInAGuest(testEnvironment.guest)
	dmaDSL.navigateToPage('access', false)
	dmaDSL.enableAccessRestrictions()
}

When(~'^they navigate to the movie details page for (?:the unowned|restricted) title (.+)$') { String seoTitle->
	dmaDSL.navigateToPage("$seoTitle movie details", false)
}

After('@cleanupAccess') { scenario ->
	dmaDSL.cleanupAccess(testEnvironment.guest)
}

Given(~'^an authenticated guest wants to connect account with vudu$') { ->
    if(testEnvironment.environment.equalsIgnoreCase("qa")){
        dmaDSL.signInAGuest(testEnvironment.guest)

    }
    else {
        true
    }
}

Then(~'^the guest is connected with vudu$') { ->
    if(testEnvironment.environment.equalsIgnoreCase("qa")) {
        dmaDSL.navigateToSettings()
        assert dmaDSL.linkingVudu(testEnvironment.guest) : 'User already connected with VUDU'
        assert dmaDSL.isAccountConnectedWithVudu(): 'Test failed to connect user with VUDU'
    }
    else {
        true
    }
}

Given(~'^an authenticated guest wants to disconnect account with vudu$') { ->
    if(testEnvironment.environment.equalsIgnoreCase("qa")){
        dmaDSL.signInAGuest(testEnvironment.guest)
    }
    else {
        true
    }
}

Then(~'^the guest is disconnected with vudu$') { ->
    if(testEnvironment.environment.equalsIgnoreCase("qa")) {
        dmaDSL.navigateToSettings()
        assert dmaDSL.unlinkVudu() : "Account already disconnected from VUDU"
        assert !dmaDSL.isAccountConnectedWithVudu(): 'Account is not disconnected from VUDU'
    }
    else {
        true
    }
}

Given(~'^an authenticated guest wants to update profile with disney account$') { ->
    dmaDSL.signInAGuest(testEnvironment.guest)
    dmaDSL.navigateToSettings()
}

When(~'^the guest updates the profile with the name$') { ->
    dmaDSL.clickAccountProfile()
    dmaDSL.profileUpdated()
}

Then(~'^the guests disney account profile is updated with the name$') { ->
    dmaDSL.clickAccountProfile()
    assert dmaDSL.isUpdatedProfileVerified() : 'Profile not updated'
}

Given(~'^an authenticated guest navigates to account history$') { ->
    dmaDSL.signInAGuest(testEnvironment.guest)
    dmaDSL.navigateToSettings()
    dmaDSL.clickAccountHistory()
}

Then(~'^the movies owned by the guest are shown with its history$') { ->
    assert dmaDSL.areAccountHistoryDetailsDisplayed() : "Account History Details not displayed"
    assert dmaDSL.verifyAccountHistoryPurchasedEntitlements() : "Entitlements does not match !!"+ "Expected entitlements = " +
            "${testEnvironment.guestEntitlements} : Actual entitlements = ${dmaDSL.getAccountHistoryEntitlements()}"

}

Given(~'^an authenticated guest navigates to closed captioning display$') { ->
    dmaDSL.signInAGuest(testEnvironment.guest)
    dmaDSL.navigateToSettings()
    dmaDSL.clickCloseCaption()
}


Then(~'^closed captioning options are displayed with edit option$') { ->
    assert dmaDSL.isCloseCaptioningDetailsDisplayed() : "Close Captioning Details are Not Displayed"
    assert dmaDSL.areCloseCaptioningOptionsVerified(testEnvironment) : "Close Caption Options does not match !! "+ "Expected Close Caption Option = " +
    "${testEnvironment.closeCaptioningOptions} : Actual Close Caption Option = ${dmaDSL.getAccountSettingsCloseCaptionOptions()}"
}





