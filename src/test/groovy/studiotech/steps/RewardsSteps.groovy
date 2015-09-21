package studiotech.steps;

import static cucumber.api.groovy.EN.*


Then(~'^the rewards page shall be displayed$') { ->
    //assert dmaDSL.isSignInMenuItemDisplayed() : "The Sign In link is missing!!"
    assert dmaDSL.isCarouselDisplayed() : "Rewards Billboard Carousel is missing"
    assert dmaDSL.isEnterCodeFieldDisplayed() : "Enter Code is not displayed"
}

Then(~'^the unauthenticated guest shall have (\\d+) movie reward points$') { int points ->
    assert dmaDSL.isGetRewardsLinkDisplayed() : "The Get Rewards Link is missing"
    assert dmaDSL.verifyRewardPoints() : "The Reward points is not zero"
}

Then(~'^the authenticated guest shall have movie reward points$') { ->
    if(testEnvironment.environment.equalsIgnoreCase("dev")) {
        true
    }
    else {
        assert dmaDSL.isGetRewardsLinkDisplayed() : "The Get Rewards Link is missing"
        assert dmaDSL.verifyGuestRewardPoints() : "The Reward points are not match expected reward points of ${testEnvironment.guestRewards}"
    }
}

And(~'^guest entered a code: \'(.+)\' to redeem$') { String code->
    dmaDSL.applyRewardsCode(code)
}

And(~'^guest chooses to Get Rewards$') { ->
    dmaDSL.selectGetRewards()
}

Then(~'^guest redirected and auto signs in to DMR page$') { ->
   assert dmaDSL.verifyDmrUrl() : "Invalid DMR landing URL, expected URL is ${testEnvironment.dmrUrl}"
}

Then(~'^the rewards page shall be properly displayed$') { ->
    assert dmaDSL.isRewardsHeaderDisplayed() : "DMR Header is not displayed or changed"
    assert dmaDSL.isCarouselDisplayed() : "Rewards Billboard Carousel is missing"
    assert dmaDSL.isGetRewardsLinkDisplayed() : "The Get Rewards Link is missing"
    assert dmaDSL.isDMRLogoDisplayed() : "DMR logo is not displayed or missing"
    assert dmaDSL.isEnterCodeFieldDisplayed() : "Enter Code is not displayed"
    assert dmaDSL.isFirstBillboardPresent() : "Rewards Billboard is missing or not displayed"
    assert dmaDSL.isRewardsMovieSliderDisplayed() : "Rewards Movie Slider is missing or not displayed"
    assert dmaDSL.isCodeSubmitButtonDisplayed() : "Rewards code submit button is missing or not displayed"
    assert dmaDSL.isCodeCleanButtonDisplayed() : "Rewards code clean button is missing or not displayed"


}

Given(~'^a guest creates a new account and enters rewards code$') {  ->

    dmaDSL.openBaseUrl()
    assert dmaDSL.isSignInMenuItemDisplayed() : "The Sign In link is missing!!"
    dmaDSL.clickSignInButton()
    dmaDSL.switchToSignInFrame()
    dmaDSL.enterDateOfBirth()
    dmaDSL.clickContinueButton()
    dmaDSL.enterFieldsToCreateAccount(testEnvironment)
    dmaDSL.clickCreateAccountButton()
    dmaDSL.clickBackToSiteButton()
    dmaDSL.navigateToPage('rewards', false)
    dmaDSL.enterRewardsCode(testEnvironment)
    assert dmaDSL.isRewardsPlayMovieDisplayed() : "Rewards redemption failed!!!"

}


Then(~'^the guest gets the movie and reward points through Disney Movie Rewards$') { ->
        assert dmaDSL.verifyReceivedRewardPoints() : "The received rewards points don't match the expected reward points : ${testEnvironment.guestRewards}"
}

Given(~'^an existing guest enters rewards code$') { ->
    if(testEnvironment.environment.equalsIgnoreCase("dev")) {
        true
    }
    else {
        dmaDSL.clickSignInButton()
        dmaDSL.switchToSignInFrame()
        dmaDSL.logInWithExistingUser()
        dmaDSL.navigateToPage('rewards', false)
        dmaDSL.enterRewardsCode(testEnvironment)
    }
}

Then(~'^duplicate code message is displayed$') { ->
    if(testEnvironment.environment.equalsIgnoreCase("dev")) {
        true
    }
    else {
        assert dmaDSL.isDuplicateCodeMessageDisplayed() : "Duplicate code error message is not displayed"
    }
}

Given(~'^an existing guest enters invalid rewards code$') { ->
    if(testEnvironment.environment.equalsIgnoreCase("dev")) {
        true
    }
    else {
        dmaDSL.clickSignInButton()
        dmaDSL.switchToSignInFrame()
        dmaDSL.logInWithExistingUser()
        dmaDSL.navigateToPage('rewards', false)
        dmaDSL.enterInvalidRewardsCode(testEnvironment)
    }
}

Then(~'^error message is displayed for invalid rewards code$') { ->
    if(testEnvironment.environment.equalsIgnoreCase("dev")) {
        true
    }
    else {
        assert dmaDSL.isInvalidCodeErrorMessageDisplayed() : "Invalid code error message is not displayed"
    }
}




