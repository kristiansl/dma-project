package studiotech.pages

import groovy.util.logging.Slf4j
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait

import java.awt.TexturePaintContext.Byte;

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions

@Slf4j
class MoviesPage extends DmaBasePage {

    private final By movieThumbnailImg = By.cssSelector('.movie-image>img')  // CSS locator movie page thumbnails images
    private final By categoryLinkLocator = By.cssSelector('.category-menuitem a')
    private final By heroTitleLocator = By.cssSelector('.hero-title')
    private final By moviesMenuLink = By.cssSelector('#nav-browse') // top menu Movies link locator
    private final By moviesSubMenuLinks = By.cssSelector('.category-menuitem a')
    private final By categoryThumbnailImg = By.cssSelector('.item >img')  // New locators in QA Environment
    private final By allMoviesLink = By.cssSelector(".ng-isolate-scope>a[href='/movies/all']")
    private final By loadingModal = By.cssSelector(".loading.modal-centered")
    WebDriverWait wait

    
    MoviesPage(WebDriver driver, String env) {
        super(driver)
        if(!env.equals('prod') || !env.equals('preview')){
           baseUrl = baseUrl.replace('/?home', '')
        }

        driver.get("${baseUrl}/movies")
        this.wait = new WebDriverWait(driver, 40)
    }

    @Override
    protected void loadPage() {
    }

    @Override
    protected void isPageLoaded() throws Error {
    }

    boolean areMoviesCategoriesDisplayed() {
            return areImagesDisplayed(categoryThumbnailImg)
    }

    MoviesPage clickAllMovies() {

        // scan through each category until we locate the 'all movies' category
        for (link in getCategoryLinks()) {
            if (link.contains("/movies/all")) {
                if(isElementPresentAndVisible(allMoviesLink)){
                    driver.findElement(allMoviesLink).click()
                }
                break
            }
        }

        return this
    }

    boolean areAllImagesDisplayed() {
            return areImagesDisplayed(movieThumbnailImg)

    }

    List<String> getCategoryLinks() {
        List<String> categoryUrls = []
        List<WebElement> movieCategories = driver.findElements(categoryLinkLocator)

        for(movieCategory in movieCategories){
            categoryUrls.add(movieCategory.getAttribute("href"))
        }
        return categoryUrls
    }

    List<String> getCategoryLinksMinusBaseUrl() {
        List<String> categoryNames = []
        List<WebElement> movieCategories = driver.findElements(categoryLinkLocator)

        for(movieCategory in movieCategories){
            String categoryName
            categoryName = movieCategory.getAttribute("href").split('/movies/')[1]
            categoryNames.add(categoryName)
        }
        return categoryNames
    }
    
    String getHeroTitle() {
        WebElement heroTitleTag = driver.findElement(heroTitleLocator)
        if(heroTitleTag != null) { 
            return heroTitleTag.text;
        }
        return "";    
    }

    String getPageTitle() {
        return driver.getTitle()
    }


    List<String> getMovieThumbnailsImagesSize() {

        waitUntilEachElementIsPresentAndVisible(movieThumbnailImg)
        List<WebElement> elmMovieThumbnailImages = driver.findElements(movieThumbnailImg)
        List<String> movieThumbnailImagesSize = []

        for (elmMovieThumbnailImage in elmMovieThumbnailImages) {
            String movieThumbnailImageSize = elmMovieThumbnailImage.getSize()
            movieThumbnailImagesSize.add(movieThumbnailImageSize)
        }
        return movieThumbnailImagesSize

    }

    boolean moviesLinkHoverOverAction(){

        waitUntilEachElementIsPresentAndVisible(categoryThumbnailImg)
        Actions actions = new Actions(driver)
        WebElement moviesLinkHover = driver.findElement(moviesMenuLink)
        actions.moveToElement(moviesLinkHover).build().perform()

        waitUntilElementIsClickable(moviesSubMenuLinks)

        List<WebElement> subNavLinks = driver.findElements(moviesSubMenuLinks)

        return isEachElementPresentAndVisible(subNavLinks)
    }

    boolean subMenuMoviesLinks(){
        List<WebElement> subNavLinks = driver.findElements(moviesSubMenuLinks)
        List<WebElement> moviesCategoryThumbnailLinks = driver.findElements(categoryLinkLocator)

        boolean isListComplete = false

        log.info("Total count of movies category subnav menu links {}",subNavLinks.size())
        log.info("Total count of movies category thumbnail links {}", moviesCategoryThumbnailLinks.size())
        if(subNavLinks.size() == moviesCategoryThumbnailLinks.size()){
            isListComplete = true
        }

        return isListComplete

    }

    boolean verifyIfAnyMovieCategoryPageTitlesGenericallyHardcoded() {

        List<String> categoryLinksOnBrowsePage = getCategoryLinks()
        for (categoryLink in categoryLinksOnBrowsePage) {

            driver.navigate().to(categoryLink)
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingModal))
            log.info("Navigating to category link: {}", categoryLink)

            log.info("categoryLink: {}", categoryLink)
            log.info("pageTitle: {}", getPageTitle())

            if (getPageTitle().equalsIgnoreCase("Watch Disney Movies | Disney Movies Anywhere")) {
                return false
            }
        }
        return true
    }

}