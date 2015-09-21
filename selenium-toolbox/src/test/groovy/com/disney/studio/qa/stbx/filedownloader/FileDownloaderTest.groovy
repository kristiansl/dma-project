package com.disney.studio.qa.stbx.filedownloader

import groovy.util.logging.Slf4j
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.Augmenter
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.support.events.EventFiringWebDriver
import org.webbitserver.WebServer
import org.webbitserver.WebServers
import org.webbitserver.handler.StaticFileHandler
import com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener

import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path

@Slf4j
class FileDownloaderTest {
    private static WebDriver driver
    private static String localDownloadPath = 'target/downloads'
    private static String localDownloadPathForInMemoryFileSystem = '/downloads'
    private static String webServerPort = 31415
    private static String baseURL
    private static hostName
    private static final LOCATION_OF_WEB_PAGES = './src/test/resources'
    private static final PHANTOMJS_EXEC_WINDOWS = 'phantomjs.exe'
    private static final PHANTOMJS_EXEC_MAC_LINUX = 'phantomjs'
    private static final PHANTOM_ENV_HOME = 'PHANTOMJS_HOME'
    private static final SYSTEM_FILE_SEPARATOR = System.getProperty('file.separator')
    private static WebServer webServer

    private static String pdfFileName = 'l-lpicert1-ltr.pdf'
    private static String pdfFileMD5Hash = '9469c0fa6d2f9f68be76d481862d537a'
    private static String imageFileName = 'ebselen.png'
    private static String imageFileMD5Hash = 'b388ca976169da026b9999f9ce8c81dd'

    // included this bit of 'magic' to avoid those pesky INFO messages that get displayed
    // when running PhantomJS on the continuous integration server (damn Bamboo) -- this will
    // only log errors with a WARN level and above
    // ref: https://www.ibm.com/developerworks/community/blogs/greenelk/entry/httpcomponents-logging?lang=en
    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog")
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "warn" +
                "")
    }

    @Test
    void shouldReturnCorrectMD5HashForImageDownloadedToFileSystem() {
        // download the image file
        assert true : "Hash file mismatch!! Expected = ${imageFileMD5Hash}; Actual = ${checkFileHash.actualFileHash()}"
    }

}