package studiotech.steps

import studiotech.dsl.Guest;

import static cucumber.api.groovy.EN.*

When(~'^the guest accesses their favorites$') { ->
    dmaDSL.clickFavoritesLink()
}

Then(~'^the my collection page shall be displayed$') { ->
    assert dmaDSL.isMyCollectionSignInDisplayed() :  "The My Collection Sign In link is missing!!"
    //  assert dmaDSL.isMovieOnUsDisplayed() : "The \"Here's a movie on us\" is missing!!"
    assert  dmaDSL.isNewAndUpcomingSliderDisplayed() : "New and Upcoming slider wasn't displayed"
    assert dmaDSL.isNoMoviesImageDisplayed() : "The empty collection key image wasn't displayed"

}

Then(~'^the "(.+)" option shall be displayed$') { String buttonText ->
    // assert dmaDSL.isNoMoviesImageDisplayed() : "Promotional Movie Image is missing"
    assert dmaDSL.isNoMoviesButtonTextDisplayed(buttonText) : buttonText + " button is missing"
}


Then(~'^the favorite page shall be displayed$') { ->
    assert dmaDSL.isFavoriteItMessageDisplayed() : "The \"Like Something? Favorite It!\" is missing"
    assert dmaDSL.isFavoriteVideoLinkDisplayed() : "The Favorite Video link is missing "
}


Then(~'^the my collection page shall be displayed with a list of owned movies$')  { ->
    assert dmaDSL.areGuestEntitlementsImagesDisplayed() : "My-Collection page is not displaying one or more movie images!!!"
    assert dmaDSL.isGuestHasEntitlements() : "Entitlement mismatch!! Actual number of entitlements = ${dmaDSL.myCollectionEntitlements.size()}, " +
            "expected number of entitlements = ${testEnvironment.guestEntitlements.size()}. Expected entitlements = " +
            "${testEnvironment.guestEntitlements} : Actual entitlements = ${dmaDSL.myCollectionEntitlements}"
}

Then(~'^the Movies header selection is selected$') { ->
    assert dmaDSL.isMoviesHeaderSelected() : "Movies header is not selected when user navigate to My-Collection page"
}

Then(~'^my collection page navigation displayed$') { ->
    assert dmaDSL.isNavBarDisplayed() : "My-Collection page sub Navigation Bar is not displayed"
}

Then(~'^guest selects movie title and views movie details$') { ->
    assert dmaDSL.selectMovieAndDetailsDisplayed() : "Movie Details page not displayed"
}

And(~'^sorting is available on the my movies page$') {->
    assert dmaDSL.isSortButtonDisplayed() : "My Collection page sort button is not found"
}

Then(~'^sort options shall be displayed$') { ->
    assert dmaDSL.areSortOptionsDisplayed() : "Expected sort options are not displayed"
}

Then(~'^favorites sort options shall be displayed$') { ->
    assert dmaDSL.areFavoritesSortOptionsDisplayed() : "Expected favorites sort options are not displayed"
}

Then(~'^movies owned by the guest should appear with "My Collection" ribbon$') { ->
    assert dmaDSL.areMyCollectionRibbonsDisplayed() : "My Collection ribbons are missing or not displayed"
}

//And(~'^selects a favorites header$') { ->
//    assert dmaDSL.isFavoritesHeaderSelected() : "Favorites header is not selected"
//}

And(~'^a list of movies shall appear with "Favorites" ribbon$') { ->
    assert dmaDSL.areFavoritesRibbonsDisplayed() : "Favorites ribbons are missing or not displayed"
}

Then(~'^default sort option should be (.+)$') { String defaultSortOption ->
    assert dmaDSL.isDefaultOptionDateAdded(defaultSortOption) : "Sort by Date Added is not default sort option"
}

//Then(~'^Movies and Videos sub headers are showing$') { ->
//    assert dmaDSL.areMoviesVideosSubHeadersDisplayed() : "Movies and Videos sub headers are not displayed"
//}

//Then(~'^guest selects a Videos header$') { ->
//    dmaDSL.selectVideosSubHeader()
//}

Then(~'^all favorites videos with "Favorites" ribbon are displayed$') { ->
    assert dmaDSL.areGuestEntitlementsImagesDisplayed() : "Favorites videos are not displayed"
    assert dmaDSL.areFavoritesRibbonsDisplayed() : "Some videos displayed without Ribbon"
}

And(~'^the guest has a list of favorite movies') { ->
    assert dmaDSL.isFavoritesHeaderSelected() : "Favorites header is not selected"
    assert dmaDSL.areFavoritesRibbonsDisplayed() : "Favorites ribbons are missing or not displayed"
    dmaDSL.selectLastFavoriteMovie()
}

Then(~'^the promotional tile is displayed for "(.+)"$') { String message ->
    assert dmaDSL.isNoMoviesHeaderTextDisplayed(message) : message + " is missing"
}


//And(~'^guest owned and has favorite movies') { ->
//    assert dmaDSL.isGuestHasEntitlements() : "Guest has no Entitlements"
//    assert dmaDSL.isGuestHasFavorites() : "Guest has no Favorites"
//}

Then(~'^each owned movie image appears with proper flag color') { ->
    assert dmaDSL.isOwnAndFavoriteFlagPurple() : "Owned and Favorite flag color is not Purple"
    assert dmaDSL.isOwnFlagBlue()              : "Owned movies flag color is not Blue"
}

Then(~'^each favorite movie thumbnail image appears with proper flag color') { ->
    assert dmaDSL.isFavoriteFlagRed()            : "Favorite movie flag color is not Red"
    assert dmaDSL.isOwnAndFavoriteFlagPurple()   : "Owned and Favorite flag color is not Purple"

}

Then(~'^movies sorting option shall be displayed') { ->
    assert dmaDSL.isSortOptionDisplayedInCorrectOrder()  : "Movies sort option displayed with incorrect order, expected order[Date Added, Name, Release Date]"
}

And(~'^guest own more than one movie') { ->
    assert dmaDSL.isOwnMoreThanOneMovie() : "Guest own only one movie"
}

And(~'^lands on movies favorites page') { ->
    dmaDSL.clickFavoritesLink()
}

And(~'^lands on videos favorites page') { ->
    dmaDSL.clickFavoritesLink()
    dmaDSL.selectVideosSubHeader()
}

Then(~'^page shall display an empty state') { ->
    assert dmaDSL.isEmptyStateDisplayed() : "Favorites page for unauthenticated guest is not showing Empty State"
}

Given(~'^a guest owned a currently vaulted movie (.*)$') {  String seoTitle->
    dmaDSL.navigateToPage("$seoTitle movie details", false)
    dmaDSL.isVaultedMovieDisplayed()
    Guest vaultGuest = new Guest();
    vaultGuest.username = testEnvironment.testConfiguration.getVaultUsername();
    vaultGuest.password = testEnvironment.testConfiguration.getVaultPassword();
    dmaDSL.signInAGuest(vaultGuest);
}

Then(~'^guest should be able to play the movie$') { ->
    assert dmaDSL.isvaultedMoviePlayButtonDisplayed() : "Play movie button not displayed"
}

