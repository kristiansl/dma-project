package studiotech.steps

import static cucumber.api.groovy.Hooks.*
import studiotech.dsl.Guest

import static cucumber.api.groovy.EN.Given

Given(~'^an (.+) guest has navigated to the (.+) page$') { String typeOfGuest, String page ->
    if ("unauthenticated".equalsIgnoreCase(typeOfGuest)) {
        dmaDSL.navigateToPage(page)
        assert dmaDSL.isSignInMenuItemDisplayed() : "The Sign In link is missing!!"
    } else if ("authenticated".equalsIgnoreCase(typeOfGuest)) {
        // TODO implement the 'authenticated' work flow
        dmaDSL.signInAGuest(testEnvironment.guest)
        dmaDSL.navigateToPage(page)
    } else {
        throw new IllegalArgumentException("The type of guest is undefined.")
    }
}


Given(~'^an authenticated guest with no movies has navigated to the (.+) page$') { String page ->
    dmaDSL.navigateToPage(page);
    Guest noMoviesGuest = new Guest();
    noMoviesGuest.username = testEnvironment.testConfiguration.getNoMoviesTestAccountUsername();
    noMoviesGuest.password = testEnvironment.testConfiguration.getNoMoviesTestAccountPassword();
    dmaDSL.signInAGuest(noMoviesGuest);
}

Before('@before') { scenario ->
    dmaDSL.navigateToPage("movies")
    dmaDSL.moviesAllMovies()

}
