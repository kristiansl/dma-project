package studiotech.steps

import cucumber.api.PendingException

import static cucumber.api.groovy.EN.*
import static cucumber.api.groovy.Hooks.Before


Then(~'^the discover page shall be displayed$') { ->
//    if (testEnvironment.environment.equalsIgnoreCase("qa")
//            && !dmaDSL.areDiscoverThumbnailsDisplayed()
//            && dmaDSL.getImgErrorRate() > 0.20) {
//        assert false: "The Discover categories are missing one or more images!!"
//    } else {
//        assert true
//    }
//
//    if (!testEnvironment.environment.equalsIgnoreCase("qa")) {
    assert dmaDSL.areDiscoverThumbnailsDisplayed(): "The Discover categories are missing one or more images!!"
//    }
}



Then(~'^the Discover page shall be displayed with video thumbnails, titles, play and info buttons$') { ->
    assert dmaDSL.areDiscoverThumbnailsDisplayed(): "Some Discovery thumbnail(s) is/are not displayed"
    assert dmaDSL.areTitlesDisplayed(): "Discovery page video titles count does not match count of video thumbnails. Some video Title(s) is/are missing"
    assert dmaDSL.areVideoPlayButtonsDisplayed(): "Discovery page play buttons count does not match count of video thumbnails. Some video Play Button(s) is/are missing"
    assert dmaDSL.areVideoInfoButtonsDisplayed(): "Discovery page info buttons count does not match count of video thumbnails. Some video Info Button(s) is/are missing"
//    try {
//        assert dmaDSL.areVideosRunTimeDisplayed(): " Discovery page video run times count does not match video titles count. Some video run time(s) is missing" +
//                "currently test will fail due to - 'DE1935 - Discover - video run time is not showing up for Marvel's Thor: The Dark World: Two Deleted Scenes'"
//    } catch (AssertionError error) {
//        if (!testEnvironment.environment.equalsIgnoreCase("prod")) {
//            throw new PendingException(" Discovery page video run times count does not match video titles count. Some video run time(s) is missing" +
//                    "currently test will fail due to - 'DE1935 - Discover - video run time is not showing up for Marvel's Thor: The Dark World: Two Deleted Scenes'")
//        } else {
//            throw new AssertionError("One or more videos runtime is missing")
//        }
//    }
}

Then(~'^guest selects random video$') { ->
    dmaDSL.selectAnyVideoRandom()
}

Then(~'^popup window will be displayed video thumbnail with expected options$') { ->
    assert dmaDSL.isVideoDescriptionDisplayed(): "Video Description is missing or not found"
    assert dmaDSL.isVideoTitleDisplayed(): "Video Title is missing or not found"
    assert dmaDSL.isFavoriteButtonDisplayed(): "Favorite button is missing or not found"
//    assert dmaDSL.isShareButtonDisplayed() : "Share button is missing or not found"
    assert dmaDSL.isPlayTimeDisplayed(): "Run time is missing or not found"
    assert dmaDSL.isVideoPlayButtonDisplayed(): "Play Video button is missing or not found"
    dmaDSL.closeVideoPopUp()
}

And(~'^guest trying to add video to favorites$') { ->
    dmaDSL.selectAnyVideoRandom()
    dmaDSL.clickVideoFavoriteButton()
    dmaDSL.closeLoginPopUp()
}


Then(~'^guest selects the video which he wants to add to favorites$') { ->
    dmaDSL.selectVideoByVideoTitle()
}

And(~'^guest adds video to favorites$') { ->
    dmaDSL.addSelectedVideoToFavorites()
}

Then(~'^video appears under Favorites Videos section on MyCollection page$') { ->
    assert dmaDSL.isFavoritesVideoAppears(true): "Video is not added to favorites"
}

//Then(~'^guest presented with login and closes it$') { ->
//    dmaDSL.closeLoginPopUp()
//}

Then(~'^video not found in favorites$') { ->
    assert dmaDSL.isVideoNotFavorites(): "Video has been added to favorites"
}

Then(~'^message: (.+) is displayed') { String message ->
    assert dmaDSL.isSignInMessageDisplayed(message): "\"Please sign in to watch these videos.\" message is missing"
}

Given(~'^a guest has navigated to the discover page') { ->
    dmaDSL.navigateToPage('discover', false)
}

Then(~'^background and video thumbnails are displayed') { ->
    assert dmaDSL.isBackgroundDisplayed(): "Red and pink background starts are not displayed"
    assert dmaDSL.areDiscoverThumbnailsDisplayed(): "The Discover page is missing one or more thumbnails images!!"
    // assert dmaDSL.areVideoThumbnailSizesMatch()  : "Video thumbnail sizes and movie thumbnail sizes don’t match"
}

Before('@beforeSetUp') { scenario ->
    dmaDSL.navigateToPage('movies', false)
    dmaDSL.allMoviesThumbnailsImages()
}

When(~'^guest adds a random video to favorites') { ->
    dmaDSL.selectAnyVideoRandom()
    dmaDSL.addSelectedVideoToFavorites()
    dmaDSL.closeVideoPopUp()
}

When(~'^guest selects random video to share$') { ->
    dmaDSL.selectAnyVideoRandom()
    dmaDSL.clickingShareButton()
}

Then(~'^the share options are displayed to guest$') { ->
    assert dmaDSL.isShareDialogDisplayed(): "Share dialog is not displayed"
    assert dmaDSL.isShareOptionsDisplayed(): "Share options are not displayed"
}

Given(~'^an authenticated guest has navigated to the discover page to share a video$') {

}

When(~'^the guest selects latest discover video$') { ->
    if (testEnvironment.environment.equalsIgnoreCase("qa")) {
        true
    } else {
        dmaDSL.areDiscoverFirstFewVideosDisplayed()
    }
}

Then(~'^the  video details page should be displayed as expected$') { ->
    if (testEnvironment.environment.equalsIgnoreCase("qa")) {
        true
    } else {
        assert dmaDSL.backToDiscoverPage(): "Not landed on discover page"
    }
}


When(~'^the guest selects a new discover video$') { ->
    if (testEnvironment.environment.equalsIgnoreCase("qa")) {
        true
    } else {
        assert dmaDSL.areNewDiscoverVideosDisplayed(): "New discover missing video image"
    }

}

//When(~'^the guest selects each discover video$') { ->
//    if (testEnvironment.environment.equalsIgnoreCase("qa")) {
//        true
//    } else {
//        assert dmaDSL.areNewDiscoverVideosDisplayed() : "New discover missing video image"
//    }
//
//}


Then(~'^new discover videos are displayed correctly on the discover page') { ->

    assert dmaDSL.areNewDiscoverVideosDisplayed(testEnvironment.getVideoGUIDs()): "One or more discover videos are showing incorrectly, see log for more details"

}

Then(~'^each of new discover video details page displayed as expected') { ->

    assert dmaDSL.areNewDiscoverVideosDetailPageDisplayed(testEnvironment.getVideoGUIDs()): "One or more video details pages are showing incorrectly, see log for more details ${dmaDSL.currentVideoTitle()}"
    assert dmaDSL.areNewDiscoverVideosHeroImagesDisplayed(testEnvironment.getVideoGUIDs()) : "Hero Images are missing"

}