Acceptance tests for the DMA project
==================

## Table of Contents ##
- [Prerequisites] (https://github.disney.com/QA/disney_movies_anywhere/blob/master/README.md#prerequisites)  -- **READ THIS SECTION _BEFORE_ PROCEEDING**
- [Description] (https://github.disney.com/QA/disney_movies_anywhere/blob/master/README.md#description)
- [Usage] (https://github.disney.com/QA/disney_movies_anywhere/blob/master/README.md#usage)
- [IDE Configuration] (https://github.disney.com/QA/disney_movies_anywhere/blob/master/README.md#ide-configuration)
- [Reviewing Test Results] (https://github.disney.com/QA/disney_movies_anywhere/blob/master/README.md#reviewing-test-results)
 

## Prerequisites ##

### How to Setup Test Environment ###

- Refer to the following wiki documentation: [How to Setup Test Environment to Support Cucumber and Selenium] (https://tech.studio.disney.com/wiki/display/QA/How+to+Setup+Test+Environment+to+Support+Cucumber+and+Selenium)
- Install a copy of Firefox, **version 27 or older**

### Maven settings.xml file ###

If you do not have a __settings.xml__ file located at <code>C:/Users/&lt;user name&gt;/.m2</code> that points to Disney's Studio Technology artifactory, then follow the steps outlined below to get your copy

1. open up a browser and navigate to https://tech.studio.disney.com/artifactory
2. from the navigation pane on the left side, click the _Maven Settings_ link
3. select the _Mirror Any_ check box and then select __repo__ from the drop down list
4. click _Generate Settings_ button
5. select _Download Settings_ and save the generated _settings.xml_ file to <code>C:/Users/&lt;user name&gt;/.m2</code>


## Description ##

Description to be provided by the DMA Team.


## Usage ##

### Available JVM Options ###

```text
Usage: mvn clean verify [maven options] [JVM options]
            
      JVM Options:
            -Denvironment=[qa|dev|staging|prod]    Define the environment in which to run the test. If this option
                                                   is not provided, default to 'qa'
            -DbaseUrl=[URL to base address]        The URL to the application under test. Providing this option
                                                   overrides the 'baseUrl' settings in the configuration file
                                                   Example: http://my.base.url:8888/
            -Dbrowser=[firefox|chrome|ie|safari]   Define the browser to use. If this option is not
                                                   provided, default to using 'firefox'
            -DisTestRemote=[true|false]            Determines whether the test should be run remotely. This parameter
                                                   must be used along with the 'remoteHost' parameter.
            -DremoteHost=[hostname]                The host name of the remote Selenium hub
            -DremotePort=[number]                  The port number of the remote Selenium hub. If this option is
                                                   not provided, default to 4444
            -DisBrowserMaximized=[true|false]      Maximize the browser. If this option is not provided, default to 'false'
            -DisScreenShotSupported=[true|false]   If 'true', then screen shots shall be taken for any failing
                                                   scenarios. If 'false', then skip taking screen shots. If this option
                                                   is not provided, default to 'true'
            -Dhelp                                 Prints this message
```

### Controlling How Features are Executed ###

The primary means of handling which Cucumber features are to be executed is through the editing of the _RunDmaITCase.java_
file.  This file is located within the _/src/test/groovy/studiotech_ directory and should look something like

```groovy
package studiotech

import org.junit.runner.RunWith
import cucumber.api.junit.Cucumber

@RunWith(Cucumber.class)
@Cucumber.Options(
  	features = ["classpath:features/"]                                                  (1)
		, monochrome = true                                                             (2)
		, format = ["pretty", "json:target/cucumber.json" ]                             (3)
		, tags = ["~@manual"]                                                           (4)
		, glue = [ "src/test/groovy/studiotech/steps" ]                                 (5)
		)
public class RunDmaITCase {
}
```

Note the @Cucumber.Options annotation.  The options contained within this annotation inform Cucumber

1. where to find the features 
2. whether we'd like to print statements to the console in monochrome (i.e., without color)
3. the type of format the results will take (i.e., 'pretty', 'json', etc.)
4. the tags we'd like to include or ignore
5. the java package(s) that contain the step definition files

The _features_ option noted above says that we are only interested in running the _search.feature_.  If additional
features exist within the features directory, these would be ignored.  If, on the other hand, you wanted
to run all the features contained within the features directory, then simply alter the option to read

```groovy
@Cucumber.Options(
    features = ["classpath:features/"]
```

The _format_ option handles how Cucumber results shall be presented.  In the example above, we have

```groovy
@Cucumber.Options(
  	format = ["pretty", "json:target/cucumber.json" ]
```

which informs Cuucmber that we'd like the results presented to us in 2 different ways:

__pretty__ -- prints to the console the scenarios and steps as they are run. Something like


```text
  Scenario: Simple text search
    Given I have navigated to google's home page
    When I search for "Star Wars"
    Then a list of "Star Wars" links is returned
```

__json:target/cucumber.json__ -- creates a JSON file contained in the _target_ directory <br/>


The _tags_ option informs Cucumber which features/scenarios should be executed or excluded.  In our example, 
we have <code>~@manual</code>, which says "ignore any feature/scenario" tagged with
@manual. For more information on tagging, refer to the [Tags] (https://github.com/cucumber/cucumber/wiki/Tags) 
section or the [Tagged Hooks] (https://github.com/cucumber/cucumber/wiki/Hooks#tagged-hooks) section of the 
Cucumber documentation.

### Running your Features ###

#### Command-line ####

The easiest and most straightforward way to run the features is from the command-line.  To do this, simply 

1. open up a console and navigate to the directory where you've cloned your project -- probably, something like 
<code>C:\Users\STAGR005\workspace\disney_movies_anywhere</code>. 
2. at the console, type <code>mvn clean integration-test</code>. If you would like the full report generated (the one based on the JSON file) type <code>mvn clean verify</code>
 
#### IDE ####

Another way of running your features is to do so within your IDE (either Eclipse or IntelliJ IDEA). Simply run
the <code>RunDmaITCase.java</code> file as a JUnit test.  Refer to your IDE's documentation for details on how to do this.

#### Browser Support ####

To specify which browser you'd like to test with, include the system property setting: <code>mvn clean integration-test -Dbrowser=ie</code>.  Currently, there are 3 types of browsers  supported:

1. _Firefox_: use the <code>-Dbrowser=firefox</code> option
2. _Internet Explorer_: use the <code>-Dbrowser=ie</code> option ( __REQUIRES__ an IE Driver installed on your machine -- see [Internet Explorer Driver] (https://code.google.com/p/selenium/wiki/InternetExplorerDriver) for more information )
3. _Safari_: use the <code>-Dbrowser=safari</code> option


### POM Profiles ###

For convenience, two POM profiles have been created: (1) _smoke_ profile and (2) _regression_ profile. These
have been created in order to avoid having to alter the Cucumber options within the <code>RunDmaITCase.java</code> file 
when it comes time to run a smoke test or regression test. In order to effectively use these profiles, simply tag
the feature or scenario with <code>@smoke</code>.  For example, let's say you'd like the following scenario to be 
run as part of your smoke test suite.

```cucumber
  Scenario: Simple text search
    Given I have navigated to google's home page
    When I search for "Star Wars"
    Then a list of "Star Wars" links is returned
```

Just add the <code>@smoke</code> tag above the Scenario keyword ... like so

```cucumber
  @smoke
  Scenario: Simple text search
    Given I have navigated to google's home page
    When I search for "Star Wars"
    Then a list of "Star Wars" links is returned
```

Now, when you're ready to run this as part of your smoke test, execute the following Maven command:
<code>mvn clean verify -Psmoke</code>.  That's it.  Any feature/scenario tagged with <code>@smoke</code> will be run.

To run a regression test, execute the following Maven command: <code>mvn clean verify -Pregression</code>.  Any
feature/scenario that is _not_ tagged with <code>@smoke</code> will be run.

## IDE Configuration ##

Since this project is a mix of Java and Groovy code, there are a few customizations that should be applied to your IDE
before proceeding.

### IntelliJ IDEA ###

1. clone this project
2. open IntelliJ and __Import Project...__
3. navigate to the folder containing the project you've just cloned and select it -- this should open up the Import Project window
4. click the __Import project from external model__ radio button
5. select a _Maven_ project from the list presented
6. on the next screen, make sure that the __Use Maven output directories__ and the __Import Maven Projects automatically__ are checked, and select _Don't detect_ from the __Generated sources folder__ dropdown
7. finish the import -- this should open up your project within IntelliJ
8. once IntelliJ has opened the project, in the Project pane (left-hand side), expand the _test->groovy_ folder and right-click on the _groovy_ directory
9. select __Mark Directory As-> Test Source Root__

### Eclipse ###

TBD

## Reviewing Test Results ##

If you've setup up your <code>RunDmaITCase.java</code> file with the following Cucumber options:

```groovy
@Cucumber.Options(
  	features = ["classpath:features/search.feature"]
		, monochrome = true                                                            
		, format = ["pretty", "json:target/cucumber.json" ]
		, tags = ["~@manual"]
		, glue = [ "src/test/groovy/studiotech/steps" ]
		)
public class RunDmaITCase {
}
```

then you can view the cucumber report, by opening the <code>feature-overview.html</code> file located within <code>target/cucumber-html-reports</code>.
