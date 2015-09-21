package com.disney.studio.qa.stbx.pageobject.pages

import com.disney.studio.qa.stbx.pageobject.Page
import org.openqa.selenium.WebDriver


class AboutPage extends Page {
    /**
     * Initializes a Page constructor using the specified driver -- defaults to using the
     * {@link studiotech.pageobject.accessors.Html5Accessor}
     * @param driver
     * the web driver used to access elements on the page
     */
    AboutPage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {
        driver.get("${baseUrl}/about.html")
    }

    @Override
    protected void isPageLoaded() throws Error {
        assert driver.currentUrl.contains('about.html')
    }
}
