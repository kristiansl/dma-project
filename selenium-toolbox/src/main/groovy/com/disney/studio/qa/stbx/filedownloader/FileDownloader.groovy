package com.disney.studio.qa.stbx.filedownloader

import com.google.common.io.ByteStreams
import com.google.common.jimfs.Configuration
import com.google.common.jimfs.Jimfs
import groovy.util.logging.Slf4j
import org.apache.commons.io.FileUtils
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.protocol.HttpClientContext
import org.apache.http.impl.client.BasicCookieStore
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.impl.cookie.BasicClientCookie
import org.apache.http.protocol.BasicHttpContext
import org.openqa.selenium.Cookie
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.FileSystem
import java.nio.file.StandardOpenOption

/**
 * The contents of this class were copied from the blog
 * <a href='http://ardesco.lazerycode.com/index.php/2012/07/how-to-download-files-with-selenium-and-why-you-shouldnt/'>
 *     How To Download Files With Selenium And Why You Shouldn't</a>
 *
 */
@Slf4j
class FileDownloader {
    private WebDriver driver;
    private String localDownloadPath = FileUtils.getTempDirectoryPath();
    private boolean followRedirects = true;
    private boolean mimicWebDriverCookieState = true;
    private int httpStatusOfLastDownloadAttempt = 0;
    private TargetFileSystem targetFileSystem

    /**
     * A static property of the <code>FileDownloader</code> class used to access the <b>IN MEMORY</b>
     * file system.
     */
    static final FileSystem inMemoryFileSystem = Jimfs.newFileSystem(Configuration.unix())

    public FileDownloader(WebDriver driver, TargetFileSystem targetFileSystem) {
        this.driver = driver;
        this.targetFileSystem = targetFileSystem
    }

    /**
     * Specify if the FileDownloader class should follow redirects when trying to download a file
     *
     * @param value
     */
    public void followRedirectsWhenDownloading(boolean value) {
        this.followRedirects = value;
    }

    /**
     * Get the current location that files will be downloaded to.
     *
     * @return The filepath that the file will be downloaded to.
     */
    public String localDownloadPath() {
        return this.localDownloadPath;
    }

    /**
     * Set the path that files will be downloaded to.
     *
     * @param filePath The filepath that the file will be downloaded to.
     */
    public void localDownloadPath(String filePath) {
        this.localDownloadPath = filePath;
    }

    /**
     * Download the file specified in the href attribute of a WebElement
     *
     * @param element
     * @return
     * @throws Exception
     */
    public String downloadFile(WebElement element) throws Exception {
        return downloadFile(element, 'href');
    }

    public String downloadFile(WebElement element, String attribute) throws Exception {
        return downloader(element, attribute);
    }

    /**
     * Download the image specified in the src attribute of a WebElement
     *
     * @param element
     * @return
     * @throws Exception
     */
    public String downloadImage(WebElement element) throws Exception {
        return downloader(element, "src");
    }

    /**
     * Gets the HTTP status code of the last download file attempt
     *
     * @return
     */
    public int getHTTPStatusOfLastDownloadAttempt() {
        return httpStatusOfLastDownloadAttempt;
    }

    /**
     * Mimic the cookie state of WebDriver (Defaults to true)
     * This will enable you to access files that are only available when logged in.
     * If set to false the connection will be made as an anonymous user
     *
     * @param value
     */
    public void mimicWebDriverCookieState(boolean value) {
        this.mimicWebDriverCookieState = value;
    }

    /**
     * Load in all the cookies WebDriver currently knows about so that we can mimic the browser cookie state
     *
     * @param seleniumCookieSet
     * @return
     */
    private BasicCookieStore mimicCookieState(Set seleniumCookieSet) {
        BasicCookieStore mimicWebDriverCookieStore = new BasicCookieStore();
        seleniumCookieSet.each { Cookie seleniumCookie ->
            BasicClientCookie duplicateCookie = new BasicClientCookie(seleniumCookie.getName(), seleniumCookie.getValue());
            duplicateCookie.setDomain(seleniumCookie.getDomain());
            duplicateCookie.setSecure(seleniumCookie.isSecure());
            duplicateCookie.setExpiryDate(seleniumCookie.getExpiry());
            duplicateCookie.setPath(seleniumCookie.getPath());
            mimicWebDriverCookieStore.addCookie(duplicateCookie);
        }

        return mimicWebDriverCookieStore;
    }

    /**
     * Perform the file/image download.
     *
     * @param element
     * @param attribute
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    private String downloader(WebElement element, String attribute) throws IOException, NullPointerException, URISyntaxException {
        String fileToDownloadLocation = element.getAttribute(attribute);
        if (fileToDownloadLocation.trim().equals("")) throw new NullPointerException("The element you have specified does not link to anything!");

        URL fileToDownloadURL = new URL(fileToDownloadLocation);

        // BEGIN: The following is NOT PART of the ORIGINAL code -------------------------------------------------------
        String fileToDownloadPath = fileToDownloadURL.getFile()
        // replace all instances of '?&=' with dots (.) and add 'csv' to the file name, if it exists
        // we do this in order to create a legal file name -- one without '?&=', which is associated with a URL
        if (!fileToDownloadPath.findAll("[\\?&=]").isEmpty()) {
            if (fileToDownloadPath.contains('csv')) {
                fileToDownloadPath = "${fileToDownloadPath}=csv"
            }

            if (fileToDownloadPath.contains('xml')) {
                fileToDownloadPath = "${fileToDownloadPath}=xml"
            }

            fileToDownloadPath = fileToDownloadPath.replaceAll("[\\?&=]", "\\.")
        }
        // END: The following is NOT PART of the ORIGINAL code ---------------------------------------------------------

        switch (targetFileSystem) {
            case TargetFileSystem.FILE_SYSTEM:
                return downloadToFileSystem(fileToDownloadURL, fileToDownloadPath)
                break
            case TargetFileSystem.IN_MEMORY_FILE_SYSTEM:
                return downloadToInMemoryFileSystem(fileToDownloadURL, fileToDownloadPath)
                break
        }
    }

    private String downloadToInMemoryFileSystem(URL fileToDownloadURL, String fileToDownloadPath) {
        Path localTargetPath = inMemoryFileSystem.getPath(localDownloadPath, fileToDownloadPath)
        Files.createDirectories(localTargetPath.getParent())

        HttpClient client = HttpClientBuilder.create().build();
        BasicHttpContext localContext = new BasicHttpContext();

        log.info("Mimic WebDriver cookie state: {}", mimicWebDriverCookieState);
        if (mimicWebDriverCookieState) {
            localContext.setAttribute(HttpClientContext.COOKIE_STORE, mimicCookieState(driver.manage().getCookies()));
        }

        HttpGet httpget = new HttpGet(fileToDownloadURL.toURI());
        RequestConfig requestConfig = RequestConfig.custom()
                .setRedirectsEnabled(followRedirects)
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000).build();
        httpget.setConfig(requestConfig)

        log.info("Sending GET request for: {}", httpget.getURI());
        HttpResponse response = client.execute(httpget, localContext);
        httpStatusOfLastDownloadAttempt = response.getStatusLine().getStatusCode();
        log.info("HTTP GET request status: {}", httpStatusOfLastDownloadAttempt);
        log.info("Downloading file: {}", localTargetPath.getFileName());

        InputStream inputStream = response.getEntity().getContent();
        Files.write(localTargetPath, ByteStreams.toByteArray(inputStream), StandardOpenOption.CREATE)

        return localTargetPath.toAbsolutePath().toString()
    }

    private String downloadToFileSystem(URL fileToDownloadURL, String fileToDownloadPath) {
        File downloadedFile = new File(localDownloadPath + fileToDownloadPath);
        if (!downloadedFile.canWrite()) downloadedFile.setWritable(true);

        HttpClient client = HttpClientBuilder.create().build();
        BasicHttpContext localContext = new BasicHttpContext();

        log.info("Mimic WebDriver cookie state: {}", mimicWebDriverCookieState);
        if (mimicWebDriverCookieState) {
            localContext.setAttribute(HttpClientContext.COOKIE_STORE, mimicCookieState(driver.manage().getCookies()));
        }

        HttpGet httpget = new HttpGet(fileToDownloadURL.toURI());
        RequestConfig requestConfig = RequestConfig.custom()
                .setRedirectsEnabled(followRedirects)
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000).build();
        httpget.setConfig(requestConfig)

        log.info("Sending GET request for: {}", httpget.getURI());
        HttpResponse response = client.execute(httpget, localContext);
        httpStatusOfLastDownloadAttempt = response.getStatusLine().getStatusCode();
        log.info("HTTP GET request status: {}", httpStatusOfLastDownloadAttempt);
        log.info("Downloading file: {}", downloadedFile.getName());
        FileUtils.copyInputStreamToFile(response.getEntity().getContent(), downloadedFile);
        response.getEntity().getContent().close();

        String downloadedFileAbsolutePath = downloadedFile.getAbsolutePath();
        log.info("File downloaded to '{}'", downloadedFileAbsolutePath);

        return downloadedFileAbsolutePath;
    }
}
