package studiotech.steps

import static cucumber.api.groovy.EN.*

When(~'^an (.+) guest views all movies$') { String typeOfGuest ->
    if ("unauthenticated".equalsIgnoreCase(typeOfGuest)) {
        dmaDSL.moviesAllMovies()
    } else if ("authenticated".equalsIgnoreCase(typeOfGuest)) {
        // TODO implement the 'authenticated' work flow
    } else {
        throw new IllegalArgumentException("The type of guest is undefined.")
    }
}

Then(~'^the movies page shall be displayed$') { ->
    assert dmaDSL.areMoviesCategoriesDisplayed() : "The Movies page is missing one or more images from its categories!!"
}

Then(~'^a list of movies shall be displayed$') { ->

    assert dmaDSL.areAllMoviesDisplayed() : "Missing one or more images from the All Movies category!!"
}
When(~'^a guest browses each movies categories$') { ->
   assert dmaDSL.areMoviesCategoriesDisplayed(): "Missing one or more thumbnail images for movie categories"
}

Then(~'^the selected category page shall be displayed$') { ->
    assert dmaDSL.areMovieCategoryPageDisplayed() : "Some images are not being displayed on the category page"
}

Then(~'^the movies page shall display all the usual Disney categories$') { ->
    assert dmaDSL.areExpectedMovieCategoriesShowing() : "Some expected categories are not displayed on movies page"
}

Then(~'^the category page shall display page title relevant to the category$') { ->
    assert dmaDSL.isAnyMovieCategoryPageTitlesGenericallyHardcoded() : "Some category page/s do not have their own category title name"
}

When(~'^a guest hovers over movies in the main navigation$') { ->	
	assert dmaDSL.hoverOverMoviesMenuLink(): "The movie category preview list is not displayed"
}

Then(~'^after a slight delay a preview list of all movie categories will be displayed$') { ->
	assert dmaDSL.areSubMenuMoviesLinksDisplayed() : "The movie category preview list is incomplete"
}
