package com.disney.studio.qa.stbx.pageobject.accessors

import com.disney.studio.qa.stbx.pageobject.Page
import com.disney.studio.qa.stbx.pageobject.SelectBy
import groovy.util.logging.Slf4j
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.ui.Select

/**
 * This class implements the {@link Accessor} interface.  The class has been designed to interact with web pages that are
 * compatible with standard HTML. The methods contained within this class are not meant for web pages that are dependant
 * on javascript.  The implementation of each method relies upon WebDriver's <b>findElement</b> method.  As an example, let's have a look at how the
 * {@link com.disney.studio.qa.stbx.pageobject.accessors.Html5Accessor#clickElement(org.openqa.selenium.By, java.lang.Boolean) clickElement} method
 * was implemented:
 * <br>
 * <pre>
 * void clickElement(By locator, boolean useJs = false) {
 *    try {
 *        if (!useJs) {
 *            pageObject.waitUntilElementIsClickable(locator)
 *            driver.findElement(locator).click()
 *        } else {
 *            WebElement element = driver.findElement(locator)
 *            pageObject.javascriptExecutor.executeScript("arguments[0].click();", element)
 *        }
 *    } catch (Exception e) {
 *        logTheError(e)
 *        throw e
 *    }
 * }
 * </pre>
 * As you can see, the code is not much different than your <em>run of the mill</em> Selenium code, with one exception --
 * the conditional check that references <code>pageObject.waitUntilElementIsClickable()</code>.  This code happens to
 * leverage the {@link com.disney.studio.qa.stbx.pageobject.Page#waitUntilElementIsClickable(org.openqa.selenium.By) waitUntilElementIsClickable}
 * method, which is located in the {@link com.disney.studio.qa.stbx.pageobject.Page} class.
 */
@Slf4j
class Html5Accessor implements Accessor {
    /**
     * A reference to the page object
     */
    private Page pageObject

    /**
     * A reference to Selenium's {@link WebDriver}
     */
    private WebDriver driver

    /**
     * The column names contained in a table -- extracted using either the {@link #readTable(org.openqa.selenium.By locator)}
     * method or the {@link #readTable(org.openqa.selenium.By locator, groovy.lang.Closure closure)} method
     */
    List<String> tableColumnNames

    /**
     * Sets the {@link Page} reference, which is used throughout the class to gain access to
     * various methods like {@link Page#isElementPresentAndVisible(org.openqa.selenium.By) isElementPresentAndVisible}
     * @param pageObject
     * a reference to the page object
     */
    void setPageObject(Page pageObject) {
        this.pageObject = pageObject
    }


    /**
     * Sets the {@link WebDriver} reference, which is used to find elements within the web page.
     * @param driver
     * a reference to Selenium's {@link WebDriver}
     */
    void setDriver(WebDriver driver) {
        this.driver = driver
    }

    @Override
    void clickElement(By locator, boolean useJs = false) {
        try {
            if (!useJs) {
                pageObject.waitUntilElementIsClickable(locator)
                driver.findElement(locator).click()
            } else {
                WebElement element = driver.findElement(locator)
                pageObject.javascriptExecutor.executeScript("arguments[0].click();", element)
            }
        } catch (Exception e) {
            logTheError(e)
            throw e
        }
    }

    @Override
    void clickButton(By locator) {
        clickElement(locator)
    }
    
    @Override
    void clickLink(By locator) {
        clickElement(locator)
    }

    @Override
    void clickCheckbox(By locator) {
        clickElement(locator)
    }

    @Override
    boolean isCheckboxSelected(By locator) {
        try {
            pageObject.waitUntilElementIsClickable(locator)
            return driver.findElement(locator).isSelected()
        } catch (Exception e) {
            logTheError(e)
            return false
        }
    }

    @Override
    void clickRadioButton(By locator, String optionToSelect) {
        try {
            pageObject.waitUntilElementIsClickable(locator)
            String xpath = "//input[@value='${optionToSelect}']"
            driver.findElement(By.xpath(xpath)).click()
        } catch (Exception e) {
            logTheError(e)
            throw e
        }
    }

    @Override
    boolean isRadioButtonSelected(By locator, String optionToSelect) {
        try {
            pageObject.waitUntilElementIsPresentAndVisible(locator)
            String xpath = "//input[@value='${optionToSelect}']"
            return driver.findElement(By.xpath(xpath)).isSelected()
        } catch (Exception e) {
            logTheError(e)
            return false
        }
    }

    @Override
    void sendText(By locator, String text) {
        try {
            pageObject.waitUntilElementIsPresentAndVisible(locator)
            driver.findElement(locator).clear()
            driver.findElement(locator).click()
            driver.findElement(locator).sendKeys(text)
        } catch (Exception e) {
            logTheError(e)
            throw e
        }
    }

    @Override
    String readText(By locator) {
        try {
            pageObject.waitUntilElementIsPresentAndVisible(locator)
            String value = driver.findElement(locator).getAttribute('value')
            if (!value) {
                value = driver.findElement(locator).getText()
            }
            return value
        } catch (Exception e) {
            logTheError(e)
            throw e
        }
    }

    @Override
    void select(By locator, SelectBy selectBy, Object optionToSelect) {
        try {
            switch (selectBy) {
                case SelectBy.INDEX:
                    pageObject.waitUntilElementIsPresentAndVisible(locator)
                    WebElement dropDownElement = driver.findElement(locator)
                    Select selector = new Select(dropDownElement)
                    selector.selectByIndex(optionToSelect as Integer)
                    break
                case SelectBy.VALUE:
                    pageObject.waitUntilElementIsPresentAndVisible(locator)
                    WebElement dropDownElement = driver.findElement(locator)
                    Select selector = new Select(dropDownElement)
                    selector.selectByValue(optionToSelect as String)
                    break
                case SelectBy.VISIBLE_TEXT:
                    pageObject.waitUntilElementIsPresentAndVisible(locator)
                    WebElement dropDownElement = driver.findElement(locator)
                    Select selector = new Select(dropDownElement)
                    selector.selectByVisibleText(optionToSelect as String)
                    break
                default:
                    throw new IllegalArgumentException("The supplied select by option, '${selectBy}', is not supported!")
            }
        } catch (Exception e) {
            logTheError(e)
            throw e
        }
    }

    @Override
    String selectedOption(By locator) {
        try {
            pageObject.waitUntilElementIsPresentAndVisible(locator)
            WebElement element = driver.findElement(locator)
            Select select = new Select(element)
            return select.getFirstSelectedOption().getText()
        } catch (Exception e) {
            logTheError(e)
            throw e
        }
    }

    /**
     * Reads the data from a table contained within each cell, using the specified locator. Locator strategies
     * supported: <b>By.id</b> and <b>By.cssSelector</b>.  This method also
     * supports Closure's -- more on how to use this below.
     * <h4>Example: Accessing an HTML table that contains hyperlinks</h4>
     *
     * In this example, we are going to access a table that contains text as well as hyperlinks.  Let's say
     * the first column of the table contains hyperlinks, and that the remaining columns contain text.
     * <br>
     * <br>
     * <table border="1" id="table-with-links">
     *       <thead>
     *       <tr>
     *         <th>Movie Title</th>
     *         <th>Director</th>
     *         <th>Actors</th>
     *      </tr>
     *      </thead>
     *      <tbody>
     *      <tr>
     *          <td><a href="http://www.imdb.com/title/tt0800369/?ref_=nv_sr_2">Thor</a></td>
     *          <td>Kenneth Branagh</td>
     *          <td>Chris Hemsworth, Anthony Hopkins, Natalie Portman</td>
     *      </tr>
     *      <tr>
     *          <td><a href="http://www.imdb.com/title/tt0325980/?ref_=nv_sr_2">Pirates of the Caribbean: The Curse of the Black Pearl</a></td>
     *          <td>Gore Verbinski</td>
     *          <td>Johnny Depp, Geoffrey Rush, Orlando Bloom, Keira Knightly</td>
     *      </tr>
     *      </tbody>
     * </table>
     * <br>
     * The table is once again labeled as <em>Movies</em>
     * <pre>
     *     By byTableWithLinks = By.id("table-with-links")
     *
     *     dataSet = pageObject.readTable(byTableWithLinks) <em> { columnCounter, cell ->
     *          if (columnCounter == 0) {
     *              return cell.findElement(By.tagName('a'))
     *          } else {
     *             return cell.getText()
     *          }
     *     } </em>
     *
     * </pre>
     * <p>
     * We are using a closure -- it's that syntax that follows the closing parentheses (<em>emphasized</em>) -- to define
     * a block of code (see <a href='http://groovy.codehaus.org/Closures'>Closures</a>). The code will be executed as part of the
     * loop that extracts the contents from each of the table's cells.  In the above example, the closure takes 2
     * arguments: <code>columnCounter</code> and <code>cell</code>. The <code>columnCounter</code> is used to determine
     * which column we're on. If we are on the first column, then return the hyperlink element.  Otherwise, return
     * whatever text value is contained within the cell.  The values returned from the closure are added to the data
     * that is eventually returned from the <code>table</code> method.
     * </p>
     * <p>
     * Keep in mind, that the data returned from the <code>readTable()</code> method contains a list of key/value
     * pairs.  Based on the above example, this would return
     * </p>
     * <p>
     * <pre>
     *     for (row in dataSet) {
     *          row['Movie Title'] // returns a Selenium WebElement object that contains a reference to the hyperlink
     *          row['Director']    // returns the director's name
     *          row['Actors']      // returns the actors names
     *     }
     * </pre>
     * Now, here is the interesting bit ... since, the first column's cell was returned as a <b>WebElement</b> (remember the
     * closure logic above), we can interact with the data stored in the first column just like any other Selenium
     * WebElement. So, we can do something like
     * <pre>
     *     row['Movie Title'].click() // we can click on the hyperlink
     * </pre>
     *
     * If you plan on using the closure option, there a couple of things to keep in mind
     * <ol>
     *     <li> The closure <b>requires</b> 2 arguments: <code>columnCounter</code> and <code>cell</code>
     *     <li> The last statement within the closure, whether it's from an <code>if-else</code> statement or not,
     *     should return the contents of the cell contained within the table.
     * </ol>
     * </p>
     *
     * @param locator
     * the locator that identifies which table to read from. Locator strategies supported: <b>By.id</b>
     * and <b>By.cssSelector</b>
     * @param closure
     * a block of code that is executed in order to extract customized data from within a table's cell
     * @return
     * a list that contains a mapping of the contents of each row. The values contained
     * within the list represent each row, and the contents of each row is represented by a map,
     * where the key is the name of the column, and the value is the content within that column
     */
    @Override
    List<Map<String, Object>> readTable(By locator, Closure closure) {
        List<Map<String, Object>> dataSet = []
        def locatorAsString = locator.toString()

        if (!locatorAsString.contains('By.id') && !locatorAsString.contains('By.selector')) {
            throw new IllegalArgumentException("Unsupported locator strategy: '$locator'. Supported strategy for " +
                    "locating a table is 'By.id' and 'By.selector'")
        }

        if (locatorAsString.contains('By.selector')) {
            def tablePattern = ~/\b(thead|tbody|tr|th|td)\b/
            if (tablePattern.matcher(locatorAsString).find()) {
                throw new IllegalArgumentException("Unsupported locator strategy: '$locator'.  " +
                        "It would appear that you are attempting to use a CSS Selector to locate " +
                        "the table, but you have included values like 'thead', 'tbody', 'tr', 'th', or 'td', within the selector. Try " +
                        "creating a CSS Selector that only locates the table (e.g., 'div[1] > div[2] > table' or '#table-id') ")
            }
        }

        pageObject.waitUntilElementIsPresentAndVisible(locator)

        def table = driver.findElement(locator)
        tableColumnNames = extractColumnNamesFromTable(table)


        def tableBody = table.findElement(By.tagName('tbody'))
        def tableRows = tableBody.findElements(By.tagName('tr'))

        def rowCounter = 0
        for (row in tableRows) {
            def cells = row.findElements(By.tagName('td'))
            def rowHashMap = [:]

            def columnCounter = 0
            def cellResult
            for (cell in cells) {
                if (closure != null) {
                    cellResult = closure.call(columnCounter, cell)
                } else {
                    cellResult = cell.text
                }

                // we don't have a column name that matches up with the table cell's value, so...
                // add an 'OverflowColumn', and then store the cell's result into the overflow column
                if (!tableColumnNames[columnCounter]) {
                    tableColumnNames.add("OverflowColumn" + columnCounter)
                }
                rowHashMap.put(tableColumnNames[columnCounter], cellResult)
                columnCounter++
            }

            dataSet.add(rowCounter, rowHashMap)
            rowCounter++
        }


        return dataSet
    }

    /**
     * Reads the data from a table contained within each cell, using the specified locator. Locator strategies
     * supported: <b>By.id</b> and <b>By.cssSelector</b>
     * <h4>Example: Accessing a simple HTML table</h4>
     *
     * Let's say we needed to access a table labeled <em>Movies</em>
     * <br>
     * <br>
     * <table border="1" id="movies">
     *       <thead>
     *       <tr>
     *         <th>Movie Title</th>
     *         <th>Director</th>
     *         <th>Actors</th>
     *         <th>MPAA Rating</th>
     *      </tr>
     *      </thead>
     *      <tbody>
     *      <tr>
     *          <td>Thor</td>
     *          <td>Kenneth Branagh</td>
     *          <td>Chris Hemsworth, Anthony Hopkins, Natalie Portman</td>
     *          <td>PG-13</td>
     *      </tr>
     *      <tr>
     *          <td>Pirates of the Caribbean: The Curse of the Black Pearl</td>
     *          <td>Gore Verbinski</td>
     *          <td>Johnny Depp, Geoffrey Rush, Orlando Bloom, Keira Knightly</td>
     *          <td>PG-13</td>
     *      </tr>
     *      </tbody>
     * </table>
     * <br>
     * <pre>
     *     By byMoviesTable = By.id("movies")
     *
     *     dataSet = pageObject.readTable(byMoviesTable)
     * </pre>
     * <p>
     * The method returns a data structure that is represented by a list of key/value pairs. In our
     * example, the data structure is assigned to the <code>dataSet</code> variable. Now, to access the data
     * is a simple matter of looping over the list and extracting out the column data by accessing the keys.
     * Something like:
     * </p>
     * <pre>
     *     for (row in dataSet) {
     *        println row['Movie Title']
     *        println row['Director']
     *        println row['Actors']
     *        println row['MPAA Rating']
     *     }
     * </pre>
     *
     * @param locator
     * the locator that identifies which table to read from. Locator strategies supported: <b>By.id</b>
     * and <b>By.cssSelector</b>
     * @return
     * a list that contains a mapping of the contents of each row. The values contained
     * within the list represent each row, and the contents of each row is represented by a map,
     * where the key is the name of the column, and the value is the content within that column
     */
    @Override
    List<Map<String, Object>> readTable(By locator) {
        try {
            return readTable(locator, null)
        } catch (Exception e) {
            logTheError(e)
            throw e
        }
    }

    private static List extractColumnNamesFromTable(WebElement table) {
        def tableHead = table.findElements(By.tagName('thead'))
        def columnNames = []

        if (tableHead.size() == 0 || tableHead.size() > 1) {
            throw new UnsupportedOperationException("Cannot extract column names from table -- malformed table! " +
                    "This table appears to have '${tableHead.size()}' headers.")
        }

        def tableHeaderRow = tableHead[0].findElements(By.tagName('tr'))

        if (tableHeaderRow.size() == 0 || tableHeaderRow.size() > 1) {
            throw new UnsupportedOperationException("Cannot extract column names from table -- malformed table! " +
                    "This table appears to have '${tableHeaderRow.size()}' rows.")
        }

        def tableHeaderCells = tableHeaderRow[0].findElements(By.tagName('th'))

        def cellText
        def columnCounter = 1
        for (cell in tableHeaderCells) {
            cellText = cell.text
            if (cellText.isEmpty()) {
                columnNames.add("ColumnName" + columnCounter)
            } else {
                columnNames.add(cellText)
            }
            columnCounter++
        }

        return columnNames
    }

    private static void logTheError(Exception e) {
        def messageWithFirstNewlineReplaced = e.message.replaceFirst(/\n/, ' :: ')
        println e.message
        log.error(messageWithFirstNewlineReplaced.replaceAll(/\n/, ' '))
    }
}
