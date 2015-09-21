package com.disney.studio.qa.stbx.pageobject.pages

import com.disney.studio.qa.stbx.pageobject.Page
import org.openqa.selenium.WebDriver


class FailVisitPage extends Page {

    FailVisitPage(WebDriver wd) {
        super(wd)
    }

    @Override
    protected void loadPage() {
        driver.get("${baseUrl}/fail.html")
    }

    @Override
    protected void isPageLoaded() throws Error {
        assert 'Selenium - Fail' == driver.title
    }
}
