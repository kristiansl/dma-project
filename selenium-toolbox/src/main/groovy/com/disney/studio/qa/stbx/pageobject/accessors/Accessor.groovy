package com.disney.studio.qa.stbx.pageobject.accessors

import com.disney.studio.qa.stbx.pageobject.SelectBy
import org.openqa.selenium.By
import org.openqa.selenium.WebElement


/**
 * <p>
 * The <code>Accessor</code> interface contains a set of methods for interacting with web elements (UI components) that
 * include, but are not limited to,
 * <ul>
 *     <li> clickElementJs
 *     <li> clickElement
 *     <li> clickButton
 *     <li> clickCheckbox
 *     <li> clickLink
 *     <li> clickRadioButton
 *     <li> sendText
 *     <li> readText
 *     <li> select
 *     <li> selectedOption
 *     <li> readTable
 * </ul>
 * Classes that <em>implement</em> this interface should attempt to supply code for each of the methods.
 */
interface Accessor {

    /**
     * Clicks an element using the specified locator.
     *
     * @param locator
     * the locator that identifies which element to click
     *
     * @param useJs
     * when set to <strong>true</strong>, JavaScript will be used to click the element, otherwise
     * the standard Selenium API (i.e., {@link org.openqa.selenium.WebElement#click} method) will be used to click the element
     */
    void clickElement(By locator, boolean useJs)

    /**
     * Clicks an element using the specified locator.
     *
     * @param locator
     * the locator that identifies which element to click
     */
    void clickElement(By locator)

    /**
     * Clicks a button using the specified locator.
     *
     * @param locator
     * the locator that identifies which button to click
     */
    void clickButton(By locator)

    /**
     * Clicks a link using the specified locator.
     *
     * @param locator
     * the locator that identifies which link to click
     */
    void clickLink(By locator)

    /**
     * Clicks a checkbox using the specified locator. This method can be used to
     * toggle <em>checking</em> and <em>un-checking</em> a box -- the end result depends on how
     * many times the box is clicked.
     *
     * @param locator
     * the locator that identifies which box to click
     */
    void clickCheckbox(By locator)

    /**
     * Determines if the checkbox is selected by using the supplied locator.
     * @param locator
     * the locator that identifies the checkbox
     * @return
     * <b>true</b> if the specified checkbox is selected, otherwise <b>false</b>
     */
    boolean isCheckboxSelected(By locator)

    /**
     * Clicks a radio button using the specified locator and the option to be selected.
     *
     * @param locator
     * the locator that identifies which radio button to click
     * @param optionToSelect
     * the value of the option to be selected
     */
    void clickRadioButton(By locator, String optionToSelect)

    /**
     * Determines if the radio button is selected by using the supplied locator and the option
     * selected
     * @param locator
     * the locator that identifies which radio button is selected
     * @param optionToSelect
     * the value of the option selected
     * @return
     * <b>true</b> if the specified radio button is selected, otherwise <b>false</b>
     */
    boolean isRadioButtonSelected(By locator, String optionToSelect)

    /**
     * Enters text into a text box or text area
     *
     * @param element
     * the {@link WebElement} that identifies which text box/area to send the text to
     * @param text
     * the text that gets entered into the text box/area
     */
    void sendText(By locator, String text)

    /**
     * Reads text from a text box or text area using the specified locator.
     *
     * @param locator
     * the locator that identifies which text box/area to read from
     * @return
     * the text contained within the text box/area
     */
    String readText(By locator)

    /**
     * Selects from a drop down list or combo box
     * <h4>Example: Selecting by visible text</h4>
     * <pre>
     *     By capColorDropDownLocator = By.cssSelector("#color")
     *     select(capColorDropDownLocator, SelectBy.VISIBLE_TEXT, 'Orange')
     * </pre>
     *
     * @param locator
     * the locator that identifies which drop down list or combo box to select
     * @param selectBy
     * determines whether to select by index, value or visible text
     * @param optionToSelect
     * the value of the option to be selected
     */
    void select(By locator, SelectBy selectBy, Object optionToSelect)

    /**
     * Retrieves the selected option from a drop down list or combo box
     * <h4>Example: Return the option selected in a drop down</h4>
     * <pre>
     *             By dropdownLocator = By.id('project-template')
     *             String optionSelected = selectedOption(dropdownLocator) // returns the option selected in the drop down list or combobox
     * </pre>
     * @return
     * the option selected
     */
    String selectedOption(By locator)

    /**
     * Reads the data from a table contained within each cell, using the specified
     * list of column names and the specified locator.  This method also
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
     *     By byTableWithLinks = By.cssSelector("table#table-with-links")
     *
     *     dataSet = pageObject.readTable(byTableWithLinks) <em> { columnCounter, cell ->
     *          if (columnCounter == 0) {
     *              return cell.findElement(By.tagName('a'))
     *          } else {
     *              return cell.getText()
     *          }
     *     }
     *     </em>
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
     * the locator that identifies which table to read from
     * @param closure
     * a block of code that is executed in order to extract customized data from within a table's cell
     * @return
     * a list that contains a mapping of the contents of each row. The values contained
     * within the list represent each row, and the contents of each row is represented by a map,
     * where the key is the name of the column, and the value is the content within that column
     */
    List<Map<String, Object>> readTable(By locator, Closure closure)


    /**
     * Reads the data from a table contained within each cell, using the specified
     * list of column names and the specified locator.
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
     *     By byMoviesTable = By.cssSelector("table#movies")
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
     * the locator that identifies which table to read from
     * @return
     * a list that contains a mapping of the contents of each row. The values contained
     * within the list represent each row, and the contents of each row is represented by a map,
     * where the key is the name of the column, and the value is the content within that column
     */
    List<Map<String, Object>> readTable(By locator)
}