package com.disney.studio.qa.stbx.pageobject.pages

import com.disney.studio.qa.stbx.pageobject.Page
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class HomePage extends Page {
    private By firstnameLocator = By.name('firstname')
    private By lastnameLocator = By.name('lastname')

    /**
     * Initializes a Page constructor using the specified driver -- defaults to using the
     * {@link studiotech.pageobject.accessors.Html5Accessor}
     * @param driver
     * the web driver used to access elements on the page
     */
    HomePage(WebDriver driver) {
        super(driver)
    }

    @Override
    protected void loadPage() {
        driver.get("${baseUrl}/home.html")
    }

    @Override
    protected void isPageLoaded() throws Error {
        assert driver.currentUrl.contains('home.html')
    }

    HomePage enterFirstName(String firstName) {
        sendText(firstnameLocator, firstName)
        return this
    }

    HomePage enterLastName(String lastName) {
        sendText(lastnameLocator, lastName)
        return this
    }

    String readFirstName() {
        return readText(firstnameLocator)
    }

    String readLastName() {
        return readText(lastnameLocator)
    }

}
