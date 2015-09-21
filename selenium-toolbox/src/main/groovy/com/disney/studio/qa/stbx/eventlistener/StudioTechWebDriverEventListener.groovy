package com.disney.studio.qa.stbx.eventlistener

import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.events.WebDriverEventListener

/**
 * This class is responsible for trapping WebDriver events.  These events include, but are
 * not limited to,
 * <ul>
 *     <li>{@link com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener#beforeNavigateTo(java.lang.String, org.openqa.selenium.WebDriver) beforeNavigateTo}
 *     <li>{@link com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener#beforeFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver) beforeFindBy}
 *     <li>{@link com.disney.studio.qa.stbx.eventlistener.StudioTechWebDriverEventListener#afterFindBy(org.openqa.selenium.By, org.openqa.selenium.WebElement, org.openqa.selenium.WebDriver)  afterFindBy}
 * </ul>
 * Not all events within this class have an implementation. For those that do, the implementation logs various aspects of the event to a log file contained within
 * the project's <em>target/logs</em> folder.
 */
@Slf4j
class StudioTechWebDriverEventListener implements WebDriverEventListener {
    @Override
    void beforeNavigateTo(String s, WebDriver webDriver) {
        log.info("Navigating to '{}'", s)
    }

    @Override
    void afterNavigateTo(String s, WebDriver webDriver) {

    }

    @Override
    void beforeNavigateBack(WebDriver webDriver) {

    }

    @Override
    void afterNavigateBack(WebDriver webDriver) {
        log.info("Navigated back to '{}'", webDriver.currentUrl)
    }

    @Override
    void beforeNavigateForward(WebDriver webDriver) {

    }

    @Override
    void afterNavigateForward(WebDriver webDriver) {
        log.info("Navigated forward to '{}'", webDriver.currentUrl)
    }

    @Override
    void beforeFindBy(By by, WebElement webElement, WebDriver webDriver) {
        if (webElement != null) {
            log.info("Attempting to locate element by '{}'", by)
        }
    }

    @Override
    void afterFindBy(By by, WebElement webElement, WebDriver webDriver) {
        if (webElement == null) {
            log.debug("Element could not be found by '{}'", by)
        } else {
            log.info("Found web element = '{}' by '{}'", webElement, by)
        }
    }

    @Override
    void beforeClickOn(WebElement webElement, WebDriver webDriver) {

    }

    @Override
    void afterClickOn(WebElement webElement, WebDriver webDriver) {
        log.info("Clicked on web element = '{}'", webElement)
    }

    @Override
    void beforeChangeValueOf(WebElement webElement, WebDriver webDriver) {
        def valueBeforeChange = webElement.getAttribute('value')
        if (!valueBeforeChange.isEmpty()) {
            log.info("Original value is '{}'", valueBeforeChange)
        }
    }

    @Override
    void afterChangeValueOf(WebElement webElement, WebDriver webDriver) {
        def valueAfterChange = webElement.getAttribute('value')
        if (!valueAfterChange.isEmpty()) {
            log.info("Value has been changed to '{}' for web element = '{}'", valueAfterChange, webElement)
        }
    }

    @Override
    void beforeScript(String script, WebDriver webDriver) {
        log.info("Attempting to execute script: {}", script.trim().replaceAll(/\s/, ' '))
    }

    @Override
    void afterScript(String script, WebDriver webDriver) {
    }

    @Override
    void onException(Throwable throwable, WebDriver webDriver) {

    }
}
