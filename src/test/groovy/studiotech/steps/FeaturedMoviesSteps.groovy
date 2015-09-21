package studiotech.steps

import studiotech.dsl.Guest;

import static cucumber.api.groovy.EN.*

def final numberOfSliders = 2;

Then(~'^the featured movies page shall be displayed with access to the guest\'s settings$') { ->
    assert dmaDSL.isSettingsDisplayed() : "The Settings do not appear to be available!!"
    assert dmaDSL.isCarouselDisplayed() : "The Featured Movie page is missing the carousel!!"
    assert dmaDSL.getNumberOfSlidersDisplayed() >= numberOfSliders :
            "The Featured Movie page is missing one or more sliders!! Expected 2 or more; Actual = ${dmaDSL.getNumberOfSlidersDisplayed()}"
}

Then(~'^the featured movies page shall be displayed$') { ->
    assert dmaDSL.isCarouselDisplayed() : "The Featured Movie page is missing the carousel!!"
    assert dmaDSL.getNumberOfSlidersDisplayed() >= numberOfSliders :
            "The Featured Movie page is missing one or more sliders!! Expected 2 or more; Actual = ${dmaDSL.getNumberOfSlidersDisplayed()}"
    assert dmaDSL.areFeaturedMoviesDisplayed() : "The Featured Movie page is missing one or more images!!"
}

And (~'^a movie details billboard exists$') { ->
    assert dmaDSL.isCarouselDisplayed(): "The Featured Movie page is missing the carousel!!"
    assert dmaDSL.isMovieDetailsBillboardExists(): "The movie details billboard is missing"
}

Then(~'^billboards shall display not less than (.+) banners$') { int numberOfBanners ->
    assert dmaDSL.correctNumberOfBannersDisplayed(numberOfBanners) : "Current number of banners is less then minimum expected banner count is "+numberOfBanners
}

When (~'^an authenticated guest chooses to view movie details$'){ ->
    dmaDSL.clickMovieDetailsLink()
}
Then (~'^the movie details page shall be displayed$') { ->
    assert dmaDSL.isMovieDetailsPageDisplayed() : "The movie details page is not displayed"
}

Then(~'^Sign In billboard should be present$') { ->
     dmaDSL.isSignInBillboardDisplayed()
}

Then(~'^the guest should be able to signed in from (.*)$') { String page ->
   assert dmaDSL.isSignInFormDisplayed() : "Sign in form not displayed"
    dmaDSL.signInFromPage(testEnvironment.guest , page)
    assert dmaDSL.isConnetAccountsPageDisplayed(): "Connect Accounts Page is not displayed"
}

Then(~'^the guest is displayed with (.*) page$') { String typeOfButton->
    dmaDSL.clickBillboardButton(typeOfButton)
    assert dmaDSL.isSignInFormDisplayed(): 'Sign in form not displayed'
    dmaDSL.enterSignInCredentials(testEnvironment.guest)
    //assert dmaDSL.isConnetAccountsPageDisplayed(): "Connect Accounts Page is not displayed"
}

And(~'^the guest selects (.*) option on billboard$') { String typeOfButton ->
    if('Take Tour'.equals(typeOfButton)){
       dmaDSL.getListOfBillboardWhiteButtonsIndices(typeOfButton)
    }
    else {
        dmaDSL.getListOfBillboardButtonIndices(typeOfButton)
    }
}

Then(~'^the movie details page, with (.*) option should be displayed$') { String typeOfButton ->
    assert dmaDSL.areMovieDetailsDisplayed(typeOfButton): "One or more movie details are not displayed"
}

Then(~'^the guest is displayed with (.*) pages$') { String typeOfButton->
        dmaDSL.clickBillboardWhiteButton(typeOfButton)
        assert dmaDSL.areTakeTourPagesVerified() : "One or more details in take tour pages are not displayed"

}




