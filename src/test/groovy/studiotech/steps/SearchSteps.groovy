package studiotech.steps;

import static cucumber.api.groovy.EN.*



And(~'^searches for the movie title (.+)') { String seoTitle ->
   dmaDSL.searchForTitle(seoTitle)
}

Then(~'^the search results shall display the cars titles') { ->
    assert dmaDSL.isSearchResultForCarsTitleDisplayed() : "Cars movie titles are not found in search result"
}

Then(~'^the search results shall be displayed') { ->
    assert dmaDSL.isSearchResultsDisplayed() : "Search result is not displayed"
}

Then(~'^the search page shall be displayed') { ->
    assert dmaDSL.isSearchAreaDisplayed() : "Area to enter text to search is not displayed"
    assert dmaDSL.isSearchBtnDisplayed() : "Search button is missing"
    assert dmaDSL.isCancelSearchBtnDisplayed() : "Cancel search button is missing"
}

And(~'^have typed minimum of (.+) characters for search') { String minSearchCharacters->
    assert dmaDSL.minNumberOfCharactersForSearch(minSearchCharacters) : "have typed 2 characters"
}

When(~'^the search is cancelled$') { ->
    assert dmaDSL.isSearchBtnDisplayed() : "Search button is missing"
    dmaDSL.isCancelSearchButtonClicked()
}

Then(~'^the search page shall not be displayed$') { ->
    assert dmaDSL.isSearchAreaEmpty() : "Search area is not cleared"
}