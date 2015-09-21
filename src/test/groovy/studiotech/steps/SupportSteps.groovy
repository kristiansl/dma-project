package studiotech.steps

import cucumber.api.PendingException;

import static cucumber.api.groovy.EN.*


Then(~'^guest will be presented (.+) or more curated questions'){ int numberOfCuratedQuestions ->
    assert dmaDSL.isSupportPageDisplayed()    :  "Support page is not displayed"
    assert dmaDSL.areCuratedQuestionsDisplayed(numberOfCuratedQuestions) : "Curated questions are not displayed"
}

Then(~'^guest will see help topic links') {->
    assert dmaDSL.areHelpTopicsDisplayed() : "Expected help topics are missing on the page"
    assert dmaDSL.canClickEachHelpTopic() : "Guest can't click one or more help topics"
}

Given(~'^an unauthenticated guest navigates to KeyChest logo in the footer$') { ->
  dmaDSL.openBaseUrl()
  dmaDSL.isKeyChestLogoClicked()
}

Then(~'^guest is displayed with support page FAQ\'s$') { ->
  assert dmaDSL.isKeychestFaqInSupportPageDisplayed() : 'KeyChest FAQ not displayed'
}