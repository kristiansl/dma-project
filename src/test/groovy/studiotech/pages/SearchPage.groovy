package studiotech.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class SearchPage extends DmaBasePage {

    private final By searchField = By.cssSelector("#q")
    private final By searchButton = By.cssSelector("#search")
    private final By clearSearch  = By.cssSelector(".icon-clear")
    private final By searchResultImages = By.cssSelector(".lazyload")
    private final By searchResultTitles = By.cssSelector(".movie-title.ellipsis")
    private final List<String> expectedCarsTitles = ['Cars 2','Cars Toon: Mater\'s Tall Tales','Cars']
    private final List<String> expectedToyStoryTitles = ['Toy Story 3','Toy Story 2','Toy Story']
    // Removed 'Toy Story Of Terror!' title until DE3882 will be fixed

    SearchPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {

    }

    @Override
    protected void isPageLoaded() throws Error {

    }

    void searchTitle(String title){

        waitUntilElementIsPresentAndVisible(searchField)
        driver.findElement(searchField).sendKeys(title)
        //  driver.findElement(searchButton).click()
        //  waitUntilEachElementIsPresentAndVisible(searchResultImages)
        waitUntilElementIsPresentAndVisible(By.cssSelector(".results"))

    }

    boolean resultLookupForCars(){

        waitUntilElementIsPresentAndVisible(By.cssSelector(".results"))

        driver.findElement(By.cssSelector(".allResults")).click()

        List<String> carTitles = []
        List<WebElement> webSearchResultTitles = driver.findElements(searchResultTitles)

        for(webSearchResultTitle in webSearchResultTitles){
            String webTitle = webSearchResultTitle.getText().trim()
            if(webTitle.startsWith("Cars")){
                carTitles.add(webTitle)
            }
        }


        if(carTitles.sort() == expectedCarsTitles.sort()){
            return true
        }
        return false
    }

    boolean searchResults(){

        waitUntilElementIsPresentAndVisible(By.cssSelector(".results"))

        driver.findElement(By.cssSelector(".allResults")).click()

        assert isElementPresentAndVisible(searchResultImages) : "Search Result is empty"

        List<String> toyStoryTitles = []
        List<WebElement> webSearchResultTitles = driver.findElements(searchResultTitles)

        for(webSearchResultTitle in webSearchResultTitles){
            String webTitle = webSearchResultTitle.getText().trim()
            if(webTitle.startsWith("Toy Story")){
                toyStoryTitles.add(webTitle)
            }
        }


        if(toyStoryTitles.size() >= expectedToyStoryTitles.size()){
            return true
        }
        return false

    }

    boolean searchArea(){
        return isElementPresentAndVisible(searchField)

    }

    boolean searchButton(){
        driver.findElement(searchField).sendKeys("Cars")
        driver.findElement(By.cssSelector('#search')).click()
        waitUntilElementIsPresentAndVisible(searchButton)
        return isElementPresentAndVisible(searchButton)
    }

    boolean cancelSearchButton(){
        return isElementPresentAndVisible(clearSearch)
    }

    boolean numberOfCharactersForSearch(String numberOfChar){

        String webSearchFieldTitle = driver.findElement(searchField).getAttribute("title")
        int webNumberOfCharacters = webSearchFieldTitle[0].toInteger()

        searchTitle(numberOfChar)

        if (webNumberOfCharacters == numberOfChar.size()){
            return true
        }
        return false
    }

    void clickCancelSearchButton() {
        clickButton(clearSearch)
    }

    boolean searchAreaEmpty() {
        return driver.findElement(searchField).getText().contentEquals('')
    }

}