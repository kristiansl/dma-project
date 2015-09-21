package com.disney.studio.qa.stbx.pageobject

import com.disney.studio.qa.stbx.pageobject.accessors.Accessor
import com.disney.studio.qa.stbx.pageobject.accessors.Html5Accessor
import groovy.util.logging.Slf4j
import org.openqa.selenium.Alert
import org.openqa.selenium.By
import org.openqa.selenium.Keys
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.interactions.Actions
import org.openqa.selenium.support.PageFactory
import org.openqa.selenium.support.ui.Clock
import org.openqa.selenium.support.ui.ExpectedCondition
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.SystemClock
import org.openqa.selenium.support.ui.WebDriverWait
import org.openqa.selenium.JavascriptExecutor
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.fail;

/**
 * <p>
 * The <code>Page</code> class encapsulates a set of behaviors that aid in using Selenium by formalizing
 * methods around accessing UI elements. In addition to this, methods exist that support more common tasks
 * such as retrieving the page title, refreshing the page or waiting for an element to be visible.
 * <p/>
 * <h3>Traditional Selenium</h3>
 * <p>
 * Typically, when one follows the Page Object Patten they do so by creating a series of page object classes that
 * implement one or more services that allow Selenium to interact with elements that reside on that page.  For
 * instance, using the example presented by the Selenium group, if one was on the login page and needed to enter a
 * username and password, the implementation looks like
 *
 * <pre>
 * // The login page contains several HTML elements that will be represented as WebElements.
 * // The locators for these elements should only be defined once.
 * By usernameLocator = By.id("username");
 * By passwordLocator = By.id("passwd");
 * By loginButtonLocator = By.id("login");
 *
 * // The login page allows the user to type their username into the username field
 * public LoginPage typeUsername(String username) {
 *   // This is the only place that "knows" how to enter a username
 *   driver.findElement(usernameLocator).sendKeys(username);
 *
 *   // Return the current page object as this action doesn't navigate to a page represented by another Page
 *   return this;
 * }
 *
 * // The login page allows the user to type their password into the password field
 * public LoginPage typePassword(String password) {
 *   // This is the only place that "knows" how to enter a password
 *   driver.findElement(passwordLocator).sendKeys(password);
 *
 *   // Return the current page object as this action doesn't navigate to a page represented by another Page
 *   return this;
 * }
 * </pre>
 * <p/>
 * <h3>Using the Selenium Toolbox API</h3>
 * <p>
 * Now, instead of this ... when using the <code>Page</code> class one could do something like
 *
 * <pre>
 * class LoginPage extends Page {
 *   private By passwordLocator = By.id('passwd');
 *
 *   public LoginPage(WebDriver driver) {
 *      super(driver);
 *   }
 *   LoginPage typePassword(String password) {
 *      sendText(passwordLocator, password);
 *      return this;
 *   }
 * }
 * </pre>
 * <p/>
 * <p>
 * The benefit of using <code>sendText()</code>, instead of a more traditional approach, resides in the fact that before
 * any text is entered into the text field, the framework waits until the web element (in this case, the text field),
 * is present and visible on the page. How is this possible? Well, the <code>sendText()</code> method has logic to wait
 * until the specified locator is present and visible on the page. The same can be said for any of the other
 * {@link Html5Accessor} methods.  This alleviates the need to populate your code with <em>sleep</em> conditions
 * and/or other types of wait conditions.
 * </p>
 * <h3>Choosing the appropriate Accessor</h3>
 * <p>
 * Selenium Toolbox supports the use of {@link Accessor}s to interact with some of the more common web elements
 * found on a page.  As of this writing, there are 3 types of accessors supported by the framework: {@link Html5Accessor}
 * and ExtJSAccessor.  Choosing which accessor to use depends upon the kind of web page you might
 * be dealing with.  For instance, let's assume that the login page used in the above example contains web elements
 * based on HTML5 standards. To interact with the page using {@link Html5Accessor}s, simply create a <code>LoginPage</code>
 * by supplying it with an instance of Selenium's WebDriver. If an accessor is not explicitly defined, then the default
 * behavior is to use the {@link Html5Accessor}.
 * <p>
 * <pre>
 * LoginPage loginPage = new LoginPage(driver);
 * </pre>
 * </p>
 * <p>
 * The use of <em>accessors</em> like the {@link com.disney.studio.qa.stbx.pageobject.accessors.Html5Accessor} is based upon Groovy's
 * <code>@Delegate</code> annotation. The <code>Page</code> class <b>delegates</b> responsibility of the web
 * controls (e.g., buttons, links, text fields, etc.) over to the appropriate {@link com.disney.studio.qa.stbx.pageobject.accessors.Accessor} class.  Essentially, this integrates
 * Accessor behavior into the Page class
 * <p/>
 * See <a href="http://www.ibm.com/developerworks/library/j-pg08259/">Practically Groovy: The Delegate annotation</a>
 * for a nice write-up on the <code>@Delegate</code> annotation
 * <p/>
 */
@Slf4j
abstract class Page {
    @Delegate(interfaces = false)
    private Accessor accessor
    @Delegate(interfaces = false)
    private Html5Accessor html5Accessor

    /**
     * Provides access to Selenium's {@link Actions} interface, so that a consumer of this library can
     * work with <a href='https://code.google.com/p/selenium/wiki/AdvancedUserInteractions'>Advanced User Interactions</a>
     */
    protected final Actions actions

    /**
     * Get an instance of the WebDriver set within each page's constructor
     */
    protected final WebDriver driver

    /**
     * An instance of the parent page object
     */
    protected final Page parentPage

    /**
     * Set or get the base URL of the web site. The base URL represents a class variable -- once set, it no longer
     * needs to be set again, and can be shared across all page objects that inherit from {@link Page}
     */
    static String baseUrl

    /**
     *  The timeout in seconds to wait. This is used in methods like {@link #isElementPresentAndVisible(By)},
     * {@link #isElementPresent(By)}, {@link #waitUntilElementIsPresent(By)}, {@link #waitUntilEachElementIsPresent(By)}, etc.
     * Defaults to 10 seconds.  This setting can be overridden.  However, keep in mind, if the setting is overridden,
     * because it is a <b>GLOBAL</b> variable, it will affect all timeout values associated with the
     * <code>waitUntil*</code>, <code>isElement*</code> and <code>isEachElement*</code> methods
     */
    long explicitGlobalTimeoutInSeconds = 10

    /**
     * Provides access to Selenium's {@link JavascriptExecutor} so that one can run javascript from
     * within any page object class
     */
    JavascriptExecutor javascriptExecutor

    /**
     * Initializes a Page constructor using the specified driver -- defaults to using the
     * {@link Html5Accessor}
     * @param wd
     * the web driver used to access elements on the page
     */
    Page(WebDriver wd) {
        this(wd, Html5Accessor)
    }

    /**
     * Initializes a Page constructor using the specified driver and the specified
     * {@link Accessor}
     * @param wd
     * the web driver used to access elements on the page
     * @param accessorName
     * the name of the {@link Accessor} to use -- accessor contain methods that access
     * web controls like buttons, links, text fields, etc.
     */
    Page(WebDriver wd, Class accessorName) {
        this(wd, accessorName, null)
    }

    /**
     * Initializes a Page constructor using the specified driver, {@link Accessor} and parent {@link Page}
     *
     * @param wd
     * the web driver used to access elements on the page
     * @param accessorName
     * the name of the {@link Accessor} to use -- accessor contain methods that access
     * web controls like buttons, links, text fields, etc.
     * @param parentPage
     * the parent's page object
     */
    Page(WebDriver wd, Class accessorName, Page parentPage) {
        PageFactory.initElements(wd, this)
        this.driver = wd
        this.javascriptExecutor = driver as JavascriptExecutor
        this.parentPage = parentPage
        this.actions = new Actions(this.driver)
        setAccessor(accessorName)
    }

    /**
     * Sets the accessor by using the supplied name
     * @param accessorName
     * the name of the {@link Accessor}
     */
    void setAccessor(Class accessorName) {
        switch (accessorName) {
            case Html5Accessor:
                this.accessor = new Html5Accessor()
                html5Accessor = (this.accessor as Html5Accessor)
                html5Accessor.driver = driver
                html5Accessor.pageObject = this
                break
            default:
                throw new IllegalArgumentException("The supplied accessor, '${accessorName}', " +
                        "is not supported at this time! Please reach out to the maintainers of Selenium-Toolbox for " +
                        "further information on how to add accessors.")
                break
        }
    }

    /**
     * Visits the page and ensures that the page is currently loaded.
     *
     * @return
     * the page that's loaded.
     *
     * @throws
     * Error when the page cannot be loaded.
     */
    Page visit() {
        Clock clock = new SystemClock()
        try {
            isPageLoaded()
            return this
        } catch (Error ignored) {
            loadPage()
        }

        long end = clock.laterBy(SECONDS.toMillis(explicitGlobalTimeoutInSeconds))

        while (clock.isNowBefore(end)) {
            try {
                isPageLoaded()
                return this
            } catch (Error ignored) {
                // skip
            }

            sleepDelay(200)
        }

        isPageLoaded()
        return this
    }

    /**
     * When this method returns, the page modeled by the subclass should be fully loaded. This
     * subclass is expected to navigate to an appropriate page should this be necessary.
     */
    protected abstract void loadPage()

    /**
     * Determine whether or not the page is loaded. When the page is loaded, this method
     * will return, but when it is not loaded, an Error should be thrown. This also allows for complex
     * checking and error reporting when loading a page, which in turn supports better error reporting
     * when a page fails to load.
     *
     * <p>
     * This behaviour makes it readily visible when a page has not been loaded successfully, and
     * because an error and not an exception is thrown tests should fail as expected. By using Error,
     * we also allow the use of junit's "Assert.assert*" methods
     *
     * @throws
     * Error when the page is not loaded.
     */
    protected abstract void isPageLoaded() throws Error

    /**
     * Switches focus to the specified frame
     * @param frameIdentifier
     * the frame id, name or index to switch to
     * @return
     * This driver focused on the given frame
     */
    WebDriver switchToFrame(frameIdentifier) {
        log.info("Switching to the frame identified by '{}'", frameIdentifier)
        if (frameIdentifier instanceof String) {
            driver.switchTo().frame(frameIdentifier as String)
        } else {
            driver.switchTo().frame(frameIdentifier as Integer)
        }

    }

    /**
     * Waits for the frame, specified by the locator, to be available. If the {@link #explicitGlobalTimeoutInSeconds} is
     * exceeded, then a {@link org.openqa.selenium.TimeoutException} is thrown
     * @param locator
     * the locator used to identify the frame to wait for
     */
    void waitForFrame(By locator) {
        log.info("Wating for the frame located '{}'", locator)
        WebDriverWait wait = new WebDriverWait(driver, explicitGlobalTimeoutInSeconds, 500)
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator))
    }

    /**
     * Switches focus to the alert, prompt or confirmation
     */
    void switchToAlert() {
        // Get a handle to the open alert, prompt or confirmation
        Alert alert = driver.switchTo().alert();
        // Get the text of the alert or prompt
        alert.getText();
        // And acknowledge the alert (equivalent to clicking "OK")
        alert.accept();
    }

    /**
     * Switches focus to the specified window name
     * @param windowName
     * the window name to switch to
     * @return
     * This driver focused on the given window
     */
    WebDriver switchToWindow(String windowName) {
        log.info("Switching to the window identified by '{}'", windowName)
        driver.switchTo().window(windowName)
    }

    /**
     * Retrieves the page title
     * @return
     * a page title
     */
    String getPageTitle() {
        driver.getTitle()
    }

    /**
     * Deletes <b>all</b> of the browser's cookies
     */
    void deleteCookies() {
        log.info("Deleting ALL of the browser's cookies")
        driver.manage().deleteAllCookies()
    }

    /**
     * Navigates back
     */
    void back() {
        driver.navigate().back();
    }

    /**
     * Navigates forward
     */
    void forward() {
        driver.navigate().forward()
    }

    /**
     * Refreshes the page
     */
    void refresh() {
        driver.navigate().refresh()
    }

    /**
     * Goes to the specified URL.
     * @param url
     * the URL, represented as a String, to go to.
     */
    void goTo(String url) {
        driver.navigate().to(url)
    }

    /**
     * Wait until the element, specified by the locator, is
     * clickable.
     * @param locator
     * used to locate the element
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded,
     * a {@link org.openqa.selenium.TimeoutException} is thrown.
     * @return
     * the web element found by the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    WebElement waitUntilElementIsClickable(By locator, long timeoutInSeconds) {
        assert timeoutInSeconds >= 1
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, 500)
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator))
        log.info("The element located {} is clickable", locator)
        return element
    }

    /**
     * Wait until the element, specified by the locator, is
     * clickable. Timeout defaults to 10 seconds.
     * @param locator
     * used to locate the element
     * @return
     * the web element found by the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    WebElement waitUntilElementIsClickable(By locator) {
        return waitUntilElementIsClickable(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Determines if the element, specified by the locator, is invisible (i.e., not
     * present on the page)
     * @param locator
     * used to locate the element
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, return <code>false</code>
     * @return
     * <code>true</code> if the element is invisible, otherwise <code>false</code>
     */
    boolean isElementInvisible(By locator, long timeoutInSeconds) {
        try {
            assert timeoutInSeconds >= 1
            waitUntilElementIsInvisible(locator, timeoutInSeconds)
            return true
        } catch (TimeoutException ignored) {
            log.warn("The element located {} is NOT invisible", locator)
            return false
        }
    }

    /**
     * Determines if the element, specified by the locator, is invisible (i.e., not
     * present on the page).
     * @param locator
     * used to locate the element
     * @return
     * <code>true</code> if the element is invisible, otherwise <code>false</code>
     */
    boolean isElementInvisible(By locator) {
        return isElementInvisible(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Waits until the element is no longer visible (i.e., not present on the page) within a certain amount of time,
     * using the supplied locator & timeout.
     * @param locator
     * used to locate the element
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, a {@link org.openqa.selenium.TimeoutException} is thrown.
     * @return
     * <code>true</code> if the element is invisible, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    boolean waitUntilElementIsInvisible(By locator, long timeoutInSeconds) {
        assert timeoutInSeconds >= 1
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, 500)
        boolean isInvisible = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator))
        log.info("The element located {} is invisible", locator)
        return isInvisible
    }

    /**
     * Waits until the element is no longer visible (i.e., not present on the page), using the specified locator.
     * Timeout defaults to 10 seconds.
     * @param locator
     * used to locate the element
     * @return
     * <code>true</code> if the element is invisible, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    boolean waitUntilElementIsInvisible(By locator) {
        return waitUntilElementIsInvisible(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Determines if the text is present on the page within a certain amount of time, using the supplied locator & timeoutInSeconds.
     * to find the text.
     * @param locator
     * used to locate the element
     * @param text
     * the text to find
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, return <code>false</code>
     * @return
     * <code>true</code> if the text is present, otherwise <code>false</code>
     */
    boolean isTextPresent(By locator, String text, long timeoutInSeconds) {
        try {
            assert timeoutInSeconds >= 1
            waitUntilTextIsPresent(locator, text, timeoutInSeconds)
            return true
        } catch (TimeoutException ignored) {
            log.warn("The text, '{}',  located {} is NOT present", text, locator)
            return false
        }
    }

    /**
     * Determines if the text is present on the page by using the supplied locator
     * to find the text.
     * @param locator
     * used to locate the element
     * @param text
     * the text to find
     * @return
     * <code>true</code> if the text is present, otherwise <code>false</code>
     */
    boolean isTextPresent(By locator, String text) {
        return isTextPresent(locator, text, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Waits until the specified text is present within a certain amount of time, using the supplied locator & timeout.
     * @param locator
     * used to locate the element
     * @param text
     * the text to wait for
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     * @return
     * <code>true</code> if the text is present, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    boolean waitUntilTextIsPresent(By locator, String text, long timeoutInSeconds) {
        assert timeoutInSeconds >= 1
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, 500)
        boolean isTextPresent = wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text))
        log.info("The text, '{}', located {} is present", text, locator)
        return isTextPresent
    }

    /**
     * Waits until the specified text is present, using the supplied locator.
     * Timeout defaults to 10 seconds.
     * @param locator
     * used to locate the element
     * @param text
     * the text to wait for
     * @return
     * <code>true</code> if the text is present, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    boolean waitUntilTextIsPresent(By locator, String text) {
        return waitUntilTextIsPresent(locator, text, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Determines if the element is present <b>AND</b> visible, using the specified
     * locator
     * @param locator
     * used to locate the element
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, return <code>false</code>
     * @return
     * <code>true</code> if the element is present and visible, otherwise <code>false</code>
     */
    boolean isElementPresentAndVisible(By locator, long timeoutInSeconds) {
        try {
            assert timeoutInSeconds >= 1
            waitUntilElementIsPresentAndVisible(locator, timeoutInSeconds)
            return true
        } catch (TimeoutException ignored) {
            log.warn("The element located {} is NOT present and visible", locator)
            return false
        }
    }

    /**
     * Determines if the element is present <b>AND</b> visible, using the specified
     * locator
     * @param locator
     * used to locate the element
     * @return
     * <code>true</code> if the element is present and visible, otherwise <code>false</code>
     */
    boolean isElementPresentAndVisible(By locator) {
        return isElementPresentAndVisible(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Waits until the element is present <b>AND</b> visible on the page, using the specified locator.
     * @param locator
     * used to locate the element
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     * @return
     * the web element found by the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    WebElement waitUntilElementIsPresentAndVisible(By locator, long timeoutInSeconds) {
        assert timeoutInSeconds >= 1
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, 500)
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator))
        log.info("The element located {} is present and visible", locator)
        return element
    }

    /**
     * Waits until the element is present <b>AND</b> visible on the page, using the specified locator.
     * Timeout defaults to 10 seconds.
     * @param locator
     * used to locate the element
     * @return
     * the web element found by the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    WebElement waitUntilElementIsPresentAndVisible(By locator) {
        return waitUntilElementIsPresentAndVisible(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Determines if <em>ALL</em> the elements are present <b>AND</b> visible, using the
     * specified locator
     * @param locator
     * used to locate each of the elements. <b>NOTE:</b> it is assumed that the specified
     * locator returns a <b><u>list of elements</u></b>.  It's expected that all
     * elements which match the locator are present and visible on the web page
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, return <code>false</code>
     * @return
     * <code>true</code> if all the elements that match the locator are present and visible, otherwise <code>false</code>
     */
    boolean isEachElementPresentAndVisible(By locator, long timeoutInSeconds) {
        try {
            assert timeoutInSeconds >= 1
            waitUntilEachElementIsPresentAndVisible(locator, timeoutInSeconds)
            return true
        } catch (TimeoutException ignored) {
            log.warn("Each element located {} is NOT present and visible", locator)
            return false
        }
    }

    /**
     * Determines if <em>ALL</em> the elements are present <b>AND</b> visible, using the
     * specified locator
     * @param locator
     * used to locate each of the elements. <b>NOTE:</b> it is assumed that the specified
     * locator returns a <b><u>list of elements</u></b>.  It's expected that all
     * elements which match the locator are present and visible on the web page
     * @return
     * <code>true</code> if all the elements that match the locator are present and visible, otherwise <code>false</code>
     */
    boolean isEachElementPresentAndVisible(By locator) {
        return isEachElementPresentAndVisible(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Waits until <em>ALL</em> the elements are present <b>AND</b> visible on the page, using the specified
     * locator.
     * @param locator
     * used to locate each of the elements. <b>NOTE:</b> it is assumed that the specified
     * locator returns a <b><u>list of elements</u></b>.  It's expected that all
     * elements which match the locator are present and visible on the web page
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     * @return
     * a list of web elements found using the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    List<WebElement> waitUntilEachElementIsPresentAndVisible(By locator, long timeoutInSeconds) {
        assert timeoutInSeconds >= 1
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, 500)
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator))
        log.info("Each element located {} is present and visible", locator)
        return elements
    }

    /**
     * Waits until <em>ALL</em> the elements are present <b>AND</b> visible on the page, using the specified
     * locator. Timeout defaults to 10 seconds.
     * @param locator
     * used to locate each of the elements. <b>NOTE:</b> it is assumed that the specified
     * locator returns a <b><u>list of elements</u></b>.  It's expected that all
     * elements which match the locator are present and visible on the web page
     * @return
     * a list of web elements found using the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    List<WebElement> waitUntilEachElementIsPresentAndVisible(By locator) {
        return waitUntilEachElementIsPresentAndVisible(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Determines if <em>ALL</em> the elements are present <b>and</b> visible, using the
     * list of web elements
     * @param elements
     * the list of web elements used to determine if each element is present and visible on the
     * web page
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, return <code>false</code>
     * @return
     * <code>true</code> if all the elements contained in the list are present and visible, otherwise <code>false</code>
     */
    boolean isEachElementPresentAndVisible(List<WebElement> elements, long timeoutInSeconds) {
        try {
            assert timeoutInSeconds >= 1
            waitUntilEachElementIsPresentAndVisible(elements, timeoutInSeconds)
            return true
        } catch (TimeoutException ignored) {
            log.warn("Each of the elements {} is NOT present and visible", elements)
            return false
        }
    }

    /**
     * Determines if <em>ALL</em> the elements are present <b>and</b> visible, using the
     * list of web elements
     * @param elements
     * the list of web elements used to determine if each element is present and visible on the
     * web page
     * @return
     * <code>true</code> if all the elements contained in the list are present and visible, otherwise <code>false</code>
     */
    boolean isEachElementPresentAndVisible(List<WebElement> elements) {
        return isEachElementPresentAndVisible(elements, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Waits until <em>ALL</em> the elements are present <b>AND</b> visible on the page, using the supplied list of
     * elements.
     * @param elements
     * the list of web elements used to determine if each element is present and visible on the
     * web page
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     * @return
     * a list of web elements, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    List<WebElement> waitUntilEachElementIsPresentAndVisible(List<WebElement> elements, long timeoutInSeconds) {
        assert timeoutInSeconds >= 1
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, 500)
        List<WebElement> foundElements = wait.until(ExpectedConditions.visibilityOfAllElements(elements))
        log.info("Each of the elements {} is present and visible", elements)
        return foundElements
    }

    /**
     * Waits until <em>ALL</em> the elements are present <b>AND</b> visible on the page, using the supplied list of
     * elements. Timeout defaults to 10 seconds.
     * @param elements
     * the list of web elements used to determine if each element is present and visible on the
     * web page
     * @return
     * a list of web elements, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    List<WebElement> waitUntilEachElementIsPresentAndVisible(List<WebElement> elements) {
        return waitUntilEachElementIsPresentAndVisible(elements, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Determines if the element is present, using the specified locator. This method
     * <b>does not</b> mean that the element is visible -- to determine that condition,
     * use {@link #isElementPresentAndVisible(org.openqa.selenium.By)} instead.
     * @param locator
     * used to locate the element
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, return <code>false</code>
     * @return
     * <code>true</code> if the element is present, otherwise <code>false</code>
     */
    boolean isElementPresent(By locator, long timeoutInSeconds) {
        try {
            assert timeoutInSeconds >= 1
            waitUntilElementIsPresent(locator, timeoutInSeconds)
            return true
        } catch (TimeoutException ignored) {
            log.warn("The element located {} is NOT present", locator)
            return false
        }
    }

    /**
     * Determines if the element is present, using the specified locator. This method
     * <b>does not</b> mean that the element is visible -- to determine that condition,
     * use {@link #isElementPresentAndVisible(org.openqa.selenium.By)} instead.
     * @param locator
     * used to locate the element
     * @return
     * <code>true</code> if the element is present, otherwise <code>false</code>
     */
    boolean isElementPresent(By locator) {
        return isElementPresent(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Waits until the element is present on the page, using the specified locator.
     * @param locator
     * used to locate the element
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     * @return
     * the web element found using the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    WebElement waitUntilElementIsPresent(By locator, long timeoutInSeconds) {
        assert timeoutInSeconds >= 1
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, 500)
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator))
        log.info("The element located {} is present", locator)
        return element
    }

    /**
     * If the timeout is exceeded, a {@link org.openqa.selenium.TimeoutException} is thrown.
     * Timeout defaults to 10 seconds.
     * @param locator
     * used to locate the element
     * @return
     * the web element found using the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    WebElement waitUntilElementIsPresent(By locator) {
        return waitUntilElementIsPresent(locator, explicitGlobalTimeoutInSeconds)
    }
    /**
     * Determines if each element is present, using the specified locator.  It's assumed that
     * the locator being passed identifies a list of elements. For example, let's say you wanted to know
     * if a table contained rows, then you might use the following locator
     * <br/>
     * <pre>
     *   By tableRowLocator = By.cssSelector('#simple-table > tbody > tr')
     * </pre>
     * @param locator
     * used to locate each of the elements
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, return <code>false</code>
     * @return
     * <code>true</code> if the elements are present, otherwise <code>false</code>
     */
    boolean isEachElementPresent(By locator, long timeoutInSeconds) {
        try {
            assert timeoutInSeconds >= 1
            waitUntilEachElementIsPresent(locator, timeoutInSeconds)
            return true
        } catch (TimeoutException ignored) {
            log.warn("Each element located {} is NOT present", locator)
            return false
        }
    }

    /**
     * Determines if each element is present, using the specified locator.  It's assumed that
     * the locator being passed identifies a list of elements. For example, let's say you wanted to know
     * if a table contained rows, then you might use the following locator
     * <br/>
     * <pre>
     *   By tableRowLocator = By.cssSelector('#simple-table > tbody > tr')
     * </pre>
     * @param locator
     * used to locate each of the elements
     * @return
     * <code>true</code> if the elements are present, otherwise <code>false</code>
     */
    boolean isEachElementPresent(By locator) {
        return isEachElementPresent(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Waits until <em>ALL</em> the elements are present on the page, using the specified locator.
     * If the timeout is exceeded, a {@link org.openqa.selenium.TimeoutException} is thrown.
     * @param locator
     * used to locate each of the elements. <b>NOTE:</b> it is assumed that the specified
     * locator returns a <b><u>list of elements</u></b>.  It's expected that all
     * elements which match the locator are present and visible on the web page
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     * @return
     * a list of web elements found using the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    List<WebElement> waitUntilEachElementIsPresent(By locator, long timeoutInSeconds) {
        assert timeoutInSeconds >= 1
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, 500)
        List<WebElement> elements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator))
        log.info("Each element located {} is present", locator)
        return elements
    }

    /**
     * Waits until <em>ALL</em> the elements are present on the page, using the specified locator.
     * Timeout defaults to 10 seconds.
     * @param locator
     * used to locate each of the elements. <b>NOTE:</b> it is assumed that the specified
     * locator returns a <b><u>list of elements</u></b>.  It's expected that all
     * elements which match the locator are present and visible on the web page
     * @return
     * a list of web elements found using the specified locator, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    List<WebElement> waitUntilEachElementIsPresent(By locator) {
        return waitUntilEachElementIsPresent(locator, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Determines if the element is no longer attached to the DOM
     * @param element
     * the web element on the page
     * @return
     * <code>true</code> if the element is no longer attached to the DOM, otherwise <code>false</code>
     */
    boolean isElementStale(WebElement element) {
        try {
            waitUntilElementIsStale(element)
            return true
        } catch (TimeoutException ignored) {
            log.warn("The element '{}' is NOT stale", element)
            return false
        }
    }

    /**
     * Waits until the element is no longer attached to the DOM.
     * @param element
     * the web element on the page
     * @param timeoutInSeconds
     * the number of seconds to wait before timing out. If the timeout is exceeded, a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     * @return
     * <code>true</code> if the element is stale, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    boolean waitUntilElementIsStale(WebElement element, long timeoutInSeconds) {
        assert timeoutInSeconds >= 1
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds, 500)
        boolean isElementStale = wait.until(ExpectedConditions.stalenessOf(element))
        log.info("The element '{}' is stale")
        return isElementStale
    }

    /**
     * Waits until the element is no longer attached to the DOM. Timeout defaults to 10 seconds.
     * @param element
     * the web element on the page
     * @return
     * <code>true</code> if the element is stale, otherwise a
     * {@link org.openqa.selenium.TimeoutException} is thrown.
     */
    boolean waitUntilElementIsStale(WebElement element) {
        return waitUntilElementIsStale(element, explicitGlobalTimeoutInSeconds)
    }

    /**
     * Pauses WebDriver for number of seconds.
     * If the timeout is exceeded, nothing happens.
     * @param seconds
     * the number of seconds to pause
     */
    void pauseWebDriver(long seconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, seconds, 1000)
            wait.until(isAlwaysFalse())
        } catch (Exception ignored) {
            //TODO: Perhaps some logging should go here acknowledging the wait has been completed.
        }
    }

    /**
     * Presses the TAB key
     * @return
     * an instance of the page you're currently on
     */
    Page tabKey() {
        actions.sendKeys(Keys.TAB).build().perform()
        return this
    }

    /**
     * Presses the ESCAPE key
     * @return
     * an instance of the page you're currently on
     */
    Page escapeKey() {
        actions.sendKeys(Keys.ESCAPE).build().perform()
        return this
    }

    /**
     * Presses the ARROW_DOWN key
     * @return
     * an instance of the page you're currently on
     */
    Page arrowDownKey() {
        actions.sendKeys(Keys.ARROW_DOWN).build().perform()
        return this
    }

    /**
     * Presses the ARROW_UP key
     * @return
     * an instance of the page you're currently on
     */
    Page arrowUpKey() {
        actions.sendKeys(Keys.ARROW_UP).build().perform()
        return this
    }

    /**
     * Presses the RETURN key
     * @return
     * an instance of the page you're currently on
     */
    Page returnKey() {
        actions.sendKeys(Keys.RETURN).build().perform()
        return this
    }

    /**
     * Presses the SPACE bar key
     * @return
     * an instance of the page you're currently on
     */
    Page spaceBarKey() {
        actions.sendKeys(Keys.SPACE).build().perform()
        return this
    }

    private ExpectedCondition<Boolean> isAlwaysFalse() {
        return new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return false
            }
        };
    }

    private static void sleepDelay(long timeInMilliseconds) {
        try {
            Thread.sleep(timeInMilliseconds)
        } catch (InterruptedException e) {
            fail(e.getMessage())
        }
    }
    
}
