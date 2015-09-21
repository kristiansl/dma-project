package com.disney.studio.qa.stbx.by

import org.openqa.selenium.By
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.SearchContext
import org.openqa.selenium.WebElement

class ByJQuery extends By {
    private final String jQuerySelector

    private ByJQuery(String jQuerySelector) {
        this.jQuerySelector = jQuerySelector
    }

    public static ByJQuery jqSelector(String jQuerySelector) {
        return new ByJQuery(jQuerySelector)
    }

    @Override
    public WebElement findElement(SearchContext searchContext) {
        String jQuery = "return " + jQuerySelector+ ".get(0);"
        WebElement element = (WebElement) ((JavascriptExecutor) searchContext).executeScript(jQuery)
        if (element == null) {
            throw new NoSuchElementException("No element found matching JQuery selector " + jQuerySelector)
        }
        return element
    }

    @Override
    List<WebElement> findElements(SearchContext searchContext) {
        String jQuery = "return " + jQuerySelector + ".get();";
        return (List<WebElement>) ((JavascriptExecutor) searchContext).executeScript(jQuery);
    }

    @Override
    public String toString() {
        return "By.jQuerySelector: " + jQuerySelector;
    }
}
