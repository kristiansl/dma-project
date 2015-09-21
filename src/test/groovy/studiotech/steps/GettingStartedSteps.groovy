package studiotech.steps;

import static cucumber.api.groovy.EN.*

Given(~'^an unauthenticated guest has never visited DMA$') { ->
    // The expectation here is that we have opened a browser and navigated to DMA's base URL page, which
    // should pop-up the 'Getting Started' page -- an indication that the guest HAS NOT Signed In
    dmaDSL.removeAllCookies()
}

Given(~'^an unauthenticated guest has returned to DMA$') { ->
    // DMA's base URL should have been loaded into the browser at this point ....
    // which means that the 'Getting Started' page is displayed.  Let's make sure
    // we move on to the Featured Movies page
    dmaDSL.clickFeaturedMoviesLink()
    assert dmaDSL.isSignInMenuItemDisplayed() : "The Sign In link is missing!!"
}

When(~'^an unauthenticated guest enters the DMA site$') { ->
    dmaDSL.openBaseUrl()
    assert dmaDSL.isSignInMenuItemDisplayed() : "The Sign In link is missing!!"
}

Then(~'^the getting started page shall be displayed$') { ->
    assert dmaDSL.isGettingStartedPageDisplayed() : "Unable to verify that the getting started page is displayed."

}

