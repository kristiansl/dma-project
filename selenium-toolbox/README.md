## Table of Contents ##
- [Prerequisites] (https://github.disney.com/ST/qa-selenium-toolbox-lib/blob/master/README.md#prerequisites) -- **READ THIS SECTION _BEFORE_ PROCEEDING**
- [Description] (https://github.disney.com/ST/qa-selenium-toolbox-lib/blob/master/README.md#description)
- [Usage] (https://github.disney.com/ST/qa-selenium-toolbox-lib/blob/master/README.md#usage)
- [IDE Configuration] (https://github.disney.com/ST/qa-selenium-toolbox-lib/blob/master/README.md#ide-configuration)
- [Reviewing Test Results] (https://github.disney.com/ST/qa-selenium-toolbox-lib/blob/master/README.md#reviewing-test-results)
- [API Documentation] (https://github.disney.com/ST/qa-selenium-toolbox-lib/blob/master/README.md#api-documentation)
 
## Prerequisites ##

### How to Setup Test Environment ###

- Refer to the following wiki documentation: [How to Setup Test Environment to Support Cucumber and Selenium] (https://tech.studio.disney.com/wiki/display/QA/How+to+Setup+Test+Environment+to+Support+Cucumber+and+Selenium)
- Install a copy of Firefox, **version 27 or older**

### Maven settings.xml file ###

If you do not have a __settings.xml__ file located at `C:/Users/[user name]/.m2` (Windows), or `/Users/[user name]/.m2` (Mac) that points to Disney's Studio Technology artifactory, then follow the steps outlined below to get your copy

1. open up a browser and navigate to https://tech.studio.disney.com/artifactory
2. from the navigation pane on the left side, click the _Maven Settings_ link
3. select the _Mirror Any_ check box and then select __repo__ from the drop down list
4. click _Generate Settings_ button
5. select _Download Settings_ and save the generated _settings.xml_ file to `C:/Users/[user name]/.m2` (Windows), or `/Users/[user name]/.m2` (Mac)

## Description ##

The Selenium Toolbox is a framework designed to encapsulate a set of behaviors that aid in using [Selenium] (http://docs.seleniumhq.org/).  The framework has been designed: 

- To support working with web elements like buttons, links, drop down lists, text boxes, etc.
- To support working with file downloads
- To support WebDriver's event-based logging

### Working with Web Elements ###

The functionality that surrounds working with web elements has been abstracted away into the `Page` class.  All of the methods contained within this class interact with elements on a web page in some shape or form. Some example methods from the `Page` class include, but are not limited to,

- `sendText(By locator, String text)`
- `clickButton(By locator)`
- `clickLink(By locator)`
- `waitUntilElementIsPresentAndVisible(By locator)`
- `isElementPresentAndVisible(By locator)`
- `waitUntilEachElementIsPresentAndVisible(List<WebElement> elements)`
- `isEachElementPresentAndVisible(List<WebElement> elements)`

Using these methods within your own project is a simple matter of extending from the `Page` class. Refer to the [Usage] (https://github.disney.com/ST/qa-selenium-toolbox-lib/blob/master/README.md#usage) section below for examples.

By default, the framework supports interacting with standard HTML web elements (i.e., controls that conform to the HTML5 standards and that are **not based on third party** JavaScript libraries). In addition to the standard HTML web elements supported, Selenium Toolbox also supports interacting with controls designed by thrid party libraries like:

- [ExtJS] (http://www.sencha.com/products/extjs/) -- _Coming Soon_

Working with web applications based on these frameworks is no different than working with web applications based on standard HTML.  The method signatures used to interact with standard HTML web elements are the same as those used to interact with Kendo UI elements.

- <code>sendText(By locator, String text)</code>
- <code>clickButton(By locator)</code>

The difference in how these methods behave between standard HTML and the Kendo UI is contained within the implementation of each method.

### Working with File Downloads ###

Certain web applications within the Studio have the ability to generate a file (excel, pdf, or otherwise).  These files are then downloaded via the web application to a users local drive.  Handling this sort of scenario using Selenium is next to impossible because Selenium can't deal with OS level dialogues, which is typically the component used to handle the actual downloading of a file. In order to solve this problem, Selenium Toolbox has implemented a solution based on the most excellent [How To Download Files With Selenium And Why You Shouldn’t] (http://ardesco.lazerycode.com/index.php/2012/07/how-to-download-files-with-selenium-and-why-you-shouldnt/) blog. 

Essentially, this solution uses a basic HTTP client to retrieve the file from a web page and download it to a specific location on the user's local drive. Once the file has been downloaded, it's MD5 hash is compared to a file with the expected MD5 hash.  And, if the two hashes match we have a valid download.  If, on the other hand, the files do not match, we have a failed download.  Providing this kind of support allows someone using Selenium Toolbox to automate the process of file validation -- see the [Usage] (https://github.disney.com/QA/selenium_toolbox/blob/master/README.md#usage) section below for examples.

### Event-based Logging ###

Selenium Toolbox leverages WebDriver's [event listener interface] (http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/support/events/WebDriverEventListener.html). What does this mean? 

It means that events such as navigating to a page, finding an element, changing a value in a text field, executing a JavaScript will be logged.  This assumes, of course, that your project has a `log4j.properties` file configured -- without it, logging these events will not be possible.  To see what this might look like, here is an example of the log file generated after running Selenium Toolbox's unit tests.

```text
INFO  [main] 2014-04-09 11:15:04.034: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Navigating to 'http://w7-2ua4031cyt:31415/home.html'
INFO  [main] 2014-04-09 11:15:07.324: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Attempting to locate element by 'By.tagName: option'
INFO  [main] 2014-04-09 11:15:07.345: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Found web element = '[[PhantomJSDriver: phantomjs on XP (df54fc80-c012-11e3-8f1e-7f320030f6c6)] -> id: project-template]' by 'By.tagName: option'
INFO  [main] 2014-04-09 11:15:07.430: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Clicked on web element = '[[[[PhantomJSDriver: phantomjs on XP (df54fc80-c012-11e3-8f1e-7f320030f6c6)] -> id: project-template]] -> tag name: option]'
INFO  [main] 2014-04-09 11:15:07.468: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Attempting to locate element by 'By.tagName: option'
INFO  [main] 2014-04-09 11:15:07.485: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Found web element = '[[PhantomJSDriver: phantomjs on XP (df54fc80-c012-11e3-8f1e-7f320030f6c6)] -> id: project-template]' by 'By.tagName: option'
INFO  [main] 2014-04-09 11:15:07.532: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Navigating to 'http://w7-2ua4031cyt:31415/home.html'
INFO  [main] 2014-04-09 11:15:09.767: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Navigating to 'http://w7-2ua4031cyt:31415/home.html'
INFO  [main] 2014-04-09 11:15:09.996: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Value has been changed to 'Timmy' for web element = '[[PhantomJSDriver: phantomjs on XP (df54fc80-c012-11e3-8f1e-7f320030f6c6)] -> name: firstname]'
INFO  [main] 2014-04-09 11:15:10.184: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Value has been changed to 'Tester' for web element = '[[PhantomJSDriver: phantomjs on XP (df54fc80-c012-11e3-8f1e-7f320030f6c6)] -> name: lastname]'
INFO  [main] 2014-04-09 11:15:10.240: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Navigating to 'http://w7-2ua4031cyt:31415/home.html'
INFO  [main] 2014-04-09 11:15:10.315: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Navigating to 'http://w7-2ua4031cyt:31415/home.html'
INFO  [main] 2014-04-09 11:15:10.448: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Clicked on web element = '[[PhantomJSDriver: phantomjs on XP (df54fc80-c012-11e3-8f1e-7f320030f6c6)] -> id: car-checkbox]'
INFO  [main] 2014-04-09 11:15:10.526: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Navigating to 'http://w7-2ua4031cyt:31415/home.html'
INFO  [main] 2014-04-09 11:15:10.652: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Navigating to 'http://w7-2ua4031cyt:31415/home.html'
INFO  [main] 2014-04-09 11:15:10.685: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Navigating to 'http://w7-2ua4031cyt:31415/about.html'
INFO  [main] 2014-04-09 11:15:10.762: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Attempting to locate element by 'By.tagName: td'
INFO  [main] 2014-04-09 11:15:10.778: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Found web element = '[[PhantomJSDriver: phantomjs on XP (df54fc80-c012-11e3-8f1e-7f320030f6c6)] -> css selector: table#table-with-links tbody tr]' by 'By.tagName: td'
INFO  [main] 2014-04-09 11:15:10.779: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Attempting to locate element by 'By.tagName: a'
INFO  [main] 2014-04-09 11:15:10.795: [com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener]: Found web element = '[[[[PhantomJSDriver: phantomjs on XP (df54fc80-c012-11e3-8f1e-7f320030f6c6)] -> css selector: table#table-with-links tbody tr]] -> tag name: td]' by 'By.tagName: a'
```

## Usage ##

### Installation ###

To install Selenium Toolbox within your project, simply include the following dependency within your `pom.xml` file

```pom
        <dependency>
            <groupId>com.disney.studio.qa</groupId>
            <artifactId>selenium-toolbox</artifactId>
            <version>-- enter version of selenium toolbox here --</version>
        </dependency>
```

where `-- enter version of selenium toolbox here --` MUST be replaced with the version of Selenium Toolbox you would like to use.

If an existing selenium dependency already exists within your pom file, remove it.  The same goes for `slf4j`.  So, if you already have something in your pom file that resembles

```pom
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-server</artifactId>
            <version>2.40.0</version>
        </dependency>
        
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.2</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.2</version>
        </dependency>
```

**REMOVE** it.

### Example of Accessing Standard HTML ###

Let's say your project is built using standard HTML. The following example demonstrates how one might access various web elements on the page using the [Page Object Pattern] (https://code.google.com/p/selenium/wiki/PageObjects).

```java
public class LoginPage extends Page {
    // The login page contains several HTML elements that will be represented as WebElements.
    // The locators for these elements should only be defined once.
    private final By usernameLocator = By.id("username");
    private final By passwordLocator = By.id("passwd");
    private final By loginButtonLocator = By.id("login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    // The login page allows the user to type their username into the username field
    public LoginPage typeUsername(String username) {
        // enter the username
        sendText(usernameLocator, username);

        // Return the current page object as this action doesn't navigate to a page represented by another PageObject
        return this;    
    }

    // The login page allows the user to type their password into the password field
    public LoginPage typePassword(String password) {
        // enter the password
        sendText(passwordLocator, password);

        // Return the current page object as this action doesn't navigate to a page represented by another PageObject
        return this;    
    }

    // The login page allows the user to submit the login form
    public HomePage submitLogin() {
        // submit the form
        clickButton(loginButtonLocator);

        // Return a new page object representing the destination. Should the login page ever
        // go somewhere else (for example, a legal disclaimer) then changing the method signature
        // for this method will mean that all tests that rely on this behaviour won't compile.
        return new HomePage(driver);    
    }  
}


LoginPage loginPage = new LoginPage(driver);                          (1)
loginPage.typeUsername("hello");                                      (2) 
loginPage.typePassword("world");                                      (3)
loginPage.submitLogin();                                              (4) 
```

1. Create a login page object, supplying it an instance of the `WebDriver` object. Since the `LoginPage` extends from the `Page` class, creating a new instance of the `LoginPage`, without specifying an accessor, sets up the page to use a standard HTML accessor by default.
2. Type _hello_ into the username field
3. Type _world_ into the password field
4. Submit/Click the login button


### Example of Visiting a Page ###

Any class that extends from the `Page` class, will be required to include the _abstract_ methods: `loadPage` and `isPageLoaded`.  These methods, along with the `visit` method, are used to load the page.  Implementation of these methods is **optional**. However, for ease of use, its best to go ahead and implement each method.

```java
public class LoginPage extends Page {
    // The login page contains several HTML elements that will be represented as WebElements.
    // The locators for these elements should only be defined once.
    private final By usernameLocator = By.id("username");
    private final By passwordLocator = By.id("passwd");
    private final By loginButtonLocator = By.id("login");

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    
    @Override
    protected void loadPage() {
        driver.get("http://path/to/login-page.html");
    }

    @Override
    protected void isPageLoaded() throws Error {
        assert driver.currentUrl.contains('login-page.html');
    }   
}

LoginPage loginPage = new LoginPage(driver);                          (1)
loginPage.visit();                                                    (2)
```    

1. Create a login page object, supplying it an instance of the `WebDriver` object. Since the `LoginPage` extends from the `Page` class, creating a new instance of the `LoginPage`, without specifying an accessor, sets up the page to use a standard HTML accessor by default.
2. Visit the login page. Calling the `visit` method will do two things: (1) it will first check to see if the page is loaded, by calling the `isPageLoaded` method, and (2) if the page is not loaded, it will load the page by calling the `loadPage` method.

### Example of Downloading a File ###

#### To a File System ####

Certain web applications within the Studio have the ability to generate a file (excel, pdf, or otherwise). These files are then downloaded via the web application to a users local drive. The following is an example of how one might retrieve an excel document.

```java
    import studiotech.filedownloader.FileDownloader
    import studiotech.filedownloader.CheckFileHash
    import studiotech.filedownloader.HashType
    import java.nio.file.FileSystems
    import java.nio.file.Files
    import java.nio.file.Path

    String excelFileMD5Hash = '9469c0fa6d2f9f68be76d481862d537a'
    String excelFileName = 'my-spreadsheet.xls'
    String downloadPage = 'http://some.page.com/where/the/file/resides.html'
    String localDownloadPath = 'target/downloads'

    FileDownloader fileDownloader = new FileDownloader(driver, , TargetFileSystem.FILE_SYSTEM)
    // set the download path to something other than the temp directory
    fileDownloader.localDownloadPath(localDownloadPath)

    driver.get(downloadPage)

    // locate the download link
    WebElement downloadLink = driver.findElement(By.id('fileToDownload'))
    // download file
    String downloadedFileAbsoluteLocation = fileDownloader.downloadFile(downloadLink)

    String excelFile = "/some/local/path/to/" + excelFileName

    CheckFileHash checkFileHash = new CheckFileHash()
    checkFileHash.registerFileToCheck(FileDownloader.inMemoryFileSystem.getPath(excelFile)) // register the file we want to check
    checkFileHash.registerHashDetails(excelFileMD5Hash, HashType.MD5) // register the details of the hash

    assert checkFileHash.isHashValid()
```

#### To an In-Memory File System (PREFERRED Method) ####

Leveraging Google's [jimfs](https://github.com/google/jimfs) library, we can now download files to an in-memory file system.

```java
    import studiotech.filedownloader.FileDownloader
    import studiotech.filedownloader.CheckFileHash
    import studiotech.filedownloader.HashType
    import java.nio.file.FileSystems
    import java.nio.file.Files
    import java.nio.file.Path

    String excelFileMD5Hash = '9469c0fa6d2f9f68be76d481862d537a'
    String excelFileName = 'my-spreadsheet.xls'
    String downloadPage = 'http://some.page.com/where/the/file/resides.html'
    String localDownloadPathForInMemoryFileSystem = '/downloads'

    FileDownloader fileDownloader = new FileDownloader(driver, TargetFileSystem.IN_MEMORY_FILE_SYSTEM)
    // set the download path to something other than the temp directory
    fileDownloader.localDownloadPath(localDownloadPathForInMemoryFileSystem)

    driver.get(downloadPage)

    // locate the download link
    WebElement downloadLink = driver.findElement(By.id('fileToDownload'))
    // download file
    String downloadedFileAbsoluteLocation = fileDownloader.downloadFile(downloadLink)

    String excelFile = "${localDownloadPathForInMemoryFileSystem}/" + excelFileName
    
    CheckFileHash checkFileHash = new CheckFileHash(TargetFileSystem.IN_MEMORY_FILE_SYSTEM) // set for IN MEMORY file system
    checkFileHash.registerFileToCheck(FileDownloader.inMemoryFileSystem.getPath(excelFile)) // register the file we want to check
    checkFileHash.registerHashDetails(excelFileMD5Hash, HashType.MD5) // register the details of the hash

    assert checkFileHash.isHashValid()
```


## IDE Configuration ##

Since this project uses Groovy code, there are a few customizations that should be applied to your IDE
before proceeding.

### IntelliJ IDEA ###

1. clone this project
2. open IntelliJ and __Import Project...__
3. navigate to the folder containing the project you've just cloned and select it -- this should open up the Import Project window
4. click the __Import project from external model__ radio button
5. select a _Maven_ project from the list presented
6. on the next screen, make sure that the __Use Maven output directories__ and the __Import Maven Projects automatically__ are checked, and select _Don't detect_ from the __Generated sources folder__ dropdown
7. finish the import -- this should open up your project within IntelliJ
8. once IntelliJ has opened the project, in the Project pane (left-hand side), expand the _test->groovy_ folder and right-click on the _groovy_ directory
9. select __Mark Directory As-> Test Source Root__

### Eclipse ###

TBD

## Reviewing Test Results ##

The test results are available within the _target/logs_ directory of the project.  Simply navigate to that directory after running the tests (`mvn clean test`), and open up the `unit_test.log` file to review the latest test results.

## API Documentation ##

Learn more about Selenium Toolbox by reviewing the [latest API documenation] (https://tech.studio.disney.com/artifactory/libs-release-local/com/disney/studio/qa/selenium-toolbox/4.3.0.2.44.0/selenium-toolbox-4.3.0.2.44.0-javadoc.jar!/index.html)
