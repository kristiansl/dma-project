package com.disney.studio.qa.stbx.pageobject

import com.disney.studio.qa.stbx.pageobject.pages.AboutPage
import com.disney.studio.qa.stbx.pageobject.pages.HomePage
import org.junit.*
import org.openqa.selenium.By
import org.openqa.selenium.TimeoutException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.Augmenter
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.openqa.selenium.support.ui.Select
import org.openqa.selenium.NoSuchElementException
import org.webbitserver.WebServer
import org.webbitserver.WebServers
import org.webbitserver.handler.StaticFileHandler
import com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener
import com.disney.studio.qa.stbx.pageobject.pages.FailVisitPage


class Html5PageTest {
    private static WebDriver driver
    private HomePage homePage
    private AboutPage aboutPage
    private static String webServerPort = 31415
    private static String baseURL
    private static hostName
    private static final HOME_PAGE = 'home.html'
    private static final LOCATION_OF_WEB_PAGES = './src/test/resources'
    private static final PHANTOMJS_EXEC_WINDOWS = 'phantomjs.exe'
    private static final PHANTOMJS_EXEC_MAC_LINUX = 'phantomjs'
    private static final PHANTOM_ENV_HOME = 'PHANTOMJS_HOME'
    private static final SYSTEM_FILE_SEPARATOR = System.getProperty('file.separator')
    private static WebServer webServer

    @Test
    void shouldNavigateToPage() {
        assert true, "some error"
    }

}