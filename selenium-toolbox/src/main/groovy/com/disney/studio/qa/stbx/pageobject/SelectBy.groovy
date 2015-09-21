package com.disney.studio.qa.stbx.pageobject

/**
 * An enumeration representing what should be selected from a drop down list or combo box. Reference Selenium's
 * <a href="http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/support/ui/Select.html">Select</a>
 * class for a peek into the methods used (e.g.,
 * <a href="http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/support/ui/Select.html#selectByIndex(int)">
 * selectByIndex()</a>)
 */
enum SelectBy {
    /**
     * Reference <a href="http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/support/ui/Select.html#selectByIndex(int)">
     * selectByIndex()</a>
     */
    INDEX,

    /**
     * Reference <a href="http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/support/ui/Select.html#selectByValue(java.lang.String)">
     * selectByValue()</a>
     */
    VALUE,

    /**
     * Reference <a href="http://selenium.googlecode.com/git/docs/api/java/org/openqa/selenium/support/ui/Select.html#selectByVisibleText(java.lang.String)">
     * selectByVisibleText()</a>
     */
    VISIBLE_TEXT
}
