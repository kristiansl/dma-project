package studiotech.steps

import static cucumber.api.groovy.EN.*

And(~'^movies slider is displayed$'){ ->
    assert dmaDSL.isMoviesSliderDisplayed() : "Movies slider is missing"
}

Then(~'^movie details page displayed with favorite option$'){ ->
    assert dmaDSL.isMovieDetailsPageDisplayed() : "Movie Details page not displayed"
    assert dmaDSL.isFavoritesButtonDisplayed() : "Add to favorites button is missing"
}

Then(~'^guest select a favorite  movie$') { ->
     dmaDSL.selectLastFavoriteMovie()
}

And(~'^guest trying to add selected movie to favorites$') { ->
    dmaDSL.clickFavoriteButton()
}

Then(~'^guest presented with login lightbox and signs in$') { ->
    dmaDSL.signInAGuest(testEnvironment.guest, true)
}

When(~'^an (.+) guest chooses to view a movie (.*)with bonus content \\((.+)\\)$') { String guestType, String ownership, String seoTitle ->
	dmaDSL.navigateToPage(seoTitle + " movie details")
}

Then(~'^there will be a listing of the bonus content available for that title, up to (.+) items$') { int maxCount ->
	assert dmaDSL.isMovieDetailsPageBonusListingCountBetween1And(maxCount) : "Bonus listings should be between 1 and " + maxCount + " (inclusive)"
}

Then(~'^the bonus features shall be grouped in categories$') { ->
	assert dmaDSL.hasBonusFeaturesInCategories() : "Bonus features should be grouped in categories"
}

Then(~'^clickable thumbnail images shall be shown for each bonus item in the bonus categories$') { ->
	assert dmaDSL.hasBonusFeatureAsThumbnailLinks() : "Bonus features should be links with thumbnails"
}

Then(~'^they cannot purchase the movie$') { ->
	assert dmaDSL.isBuyButtonRestricted() : 'The buy button is not restricted.'
}

Then(~'^they cannot share the movie$') { ->
	assert dmaDSL.doesShareButtonShowRestriction() : 'The share button is not restricted. Should show restriction modal.'
}

Then(~'^the play button is restricted behind a password prompt$') { ->
	assert dmaDSL.isPlayButtonRestricted() : 'The play button is not restricted.'
	assert dmaDSL.isLightboxPresentAfterClickingUpdate() : 'Clicking "Update" did not display the Disney login.'
    assert dmaDSL.isMovieDetailsPageDisplayed() : "Movie Details page not displayed"
}

Then(~'^the guest removes a favorite movie$') { ->
    dmaDSL.removeMovieFromFavorites()
}

Then(~'^guest removes a favorite video$') { ->
    dmaDSL.removeVideoFromFavorites()
    dmaDSL.closeVideoDetailsPage()
}

Then(~'^the selected movie shall not be displayed in their collection$') { ->
assert !dmaDSL.isFavoritesMovieAppears(false) : "Movie is not removed from favorites"
}

When(~'^guest adds movie to favorites$') { ->
    dmaDSL.selectFirstMovieOnSlider()
    assert dmaDSL.isMovieDetailsPageDisplayed() : "Movie Details page not displayed"
    assert dmaDSL.isFavoritesButtonDisplayed() : "Add to favorites button is missing"
    dmaDSL.addMovieToFavorites()
}


Then(~'^selected movie shall be displayed in their collection$') { ->
    assert dmaDSL.isMovieDetailsPageDisplayed() : "Movie Details page not displayed"
    assert dmaDSL.isFavoritesMovieAppears(true) : "Selected movie not found under favorites"
}

And(~'^guest wants to add movie to favorites$') { ->
    assert dmaDSL.isMoviesSliderDisplayed() : "Movies slider is missing"
    dmaDSL.selectFirstMovieOnSlider()
    assert dmaDSL.isMovieDetailsPageDisplayed() : "Movie Details page not displayed"
    assert dmaDSL.isFavoritesButtonDisplayed() : "Add to favorites button is missing"
    dmaDSL.clickFavoriteButton()
}

And(~'^guest adds owned movie to favorites$') { ->
    dmaDSL.selectOwnedMovie()
    assert dmaDSL.isMovieDetailsPageDisplayed() : "Movie Details page not displayed"
    assert dmaDSL.isFavoritesButtonDisplayed() : "Add to favorites button is missing"
    dmaDSL.addMovieToFavorites()
}

Then(~'^guest should be able to see the title of the bonus video within in each bonus category$') { ->
    assert dmaDSL.arebonusContentVideoNamesDisplayed() : "Bonus video names not displayed"
}

When(~'^guest navigates to movie detail page of his owned movie$') { ->
    dmaDSL.selectOwnedMovie()
}

Then(~'^HD icon should be displayed to notify guest that its HD version$') { ->
    assert dmaDSL.isHdIconDisplayed() : "HD icon is not displayed"
}