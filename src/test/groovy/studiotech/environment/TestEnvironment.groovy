package studiotech.environment

import cucumber.runtime.ScenarioImpl
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.ie.InternetExplorerDriver
import org.openqa.selenium.remote.Augmenter
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.RemoteWebDriver
import org.openqa.selenium.remote.SessionId
import org.openqa.selenium.safari.SafariDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import studiotech.dsl.Guest

class TestEnvironment {
    ScenarioImpl scenario

	private static Logger log = LoggerFactory.getLogger(TestEnvironment.class);
    private  WebDriver driver = null;
    private SessionId sessionId
    private TestConfiguration testConfiguration;
    private Guest guest;
	private String environment;
    private String browser;
    private String browserVersion;
    private boolean isTestRemote;
    private boolean isBrowserMaximized;
    private boolean isScreenShotSupported;
    private String remoteHost;
    private String remotePort;
    private String remoteHub;
    public static String baseUrl;
    private String dmrUrl;
    private String movieDetailsUrl;
    private String baseUrlOverride;
    private String testAccountUsername;
    private String testAccountPassword;
    private String guestPoints
    private String vaultUsername
    private String vaultPassword
    private String rewardsCode
    private String receivedRewards
    private String invalidRewardsCode
    private List<String> guestEntitlements;
    private List<String> myCollectionSortOptions;
    private List<String> favoritesSortOptions;
    private List<String> moviesSortOptions;
    private List<String> movieCategoryPaths;
    private List<String> contactUsCategory;
    private List<String> closeCaptioningOptions;
    private List<String> videoGUIDs
    public HashMap<String,String> accountDetails

    // included this bit of 'magic' to avoid those pesky INFO messages that get displayed
    // when running PhantomJS on the continuous integration server (damn Bamboo) -- this will
    // only log errors with a WARN level and above
    // ref: https://www.ibm.com/developerworks/community/blogs/greenelk/entry/httpcomponents-logging?lang=en
//    static {
//        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
//        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "warn");
//    }

//    static {
//        Runtime.getRuntime().addShutdownHook( new Thread() {
//            @Override
//            public void run() {
//                log.info("Shutting down the test environment");
//                if (driver != null) {
//                    driver.quit();
//                }
//            }
//        });
//    }

    TestEnvironment() {
        this.testConfiguration = new TestConfiguration();
        this.guest = new Guest();
    }

    public String getSeleniumSessionId() {
        return sessionId
    }

    public String getSauceOnDemandSessionAndJob() {
        return "SauceOnDemandSessionID=${getSeleniumSessionId()} job-name=${scenario.name}"
    }

    public String getRemoteHub() {
        return remoteHub
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getDmrUrl() {
        return dmrUrl;
    }

	public String getMovieDetailsUrl() {
		return movieDetailsUrl;
	}
	
    public String getBrowser() {
        return browser;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getTestAccountUsername() {
        return testAccountUsername;
    }

    public String getTestAccountPassword() {
        return testAccountPassword
    }

    public List<String> getGuestEntitlements() {
        return guestEntitlements
    }

    public String getGuestRewards() {
        return guestPoints
    }

    public String getReceivedRewards() {
        return receivedRewards
    }

    public List<String> getMyCollectionSortOptions() {
        return myCollectionSortOptions
    }

    public List<String> getFavoritesSortOptions() {
        return favoritesSortOptions
    }

    public List<String> getMoviesSortOptions() {
        return moviesSortOptions;
    }

    public List<String> getMovieCategoryPaths() {
        return movieCategoryPaths;
    }
    
    public Guest getGuest() {
            return guest;
    }

    public String getEnvironment() {
        return environment;
    }

    public boolean isScreenShotSupported() {
        return isScreenShotSupported;
    }

    public List<String> getContactUsCategory(){
        return contactUsCategory
    }

    public String getRewardsCode() {
        return rewardsCode
    }

    public String getInvalidRewardsCode() {
        return invalidRewardsCode
    }

    public List<String> getCloseCaptioningOptions(){
        return closeCaptioningOptions
    }

    public List<String> getVideoGUIDs(){
        return videoGUIDs
    }

    public HashMap<String,String> getAccountDetails(){
        return accountDetails
    }


	public WebDriver loadEnvironment() throws IOException {
        // read in the command-line system arguments
        readCommandLineSystemArguments();
		// load up the props
		loadProperties();
        // load selenium
        return loadSelenium();
	}


    private void readCommandLineSystemArguments() {
        // pull in the command-line properties
        environment = System.getProperty("environment") == null ? "qa" : System.getProperty("environment").toLowerCase();
        browser = System.getProperty("browser") == null ? "firefox" : System.getProperty("browser").toLowerCase();
        baseUrlOverride = System.getProperty("baseUrl") == null ? null : System.getProperty("baseUrl").trim();
        isTestRemote = System.getProperty("isTestRemote") != null && Boolean.parseBoolean(System.getProperty("isTestRemote"));
        isBrowserMaximized = System.getProperty("isBrowserMaximized") != null && Boolean.parseBoolean(System.getProperty("isBrowserMaximized"));
        isScreenShotSupported = System.getProperty("isScreenShotSupported") == null ? Boolean.parseBoolean("true") : Boolean.parseBoolean(System.getProperty("isScreenShotSupported"));
        remoteHost = System.getProperty("remoteHost") == null ? "localhost" : System.getProperty("remoteHost");
        remotePort = System.getProperty("remotePort") == null ? "4444" : System.getProperty("remotePort");
        remoteHub = "http://" + remoteHost + ":" + remotePort + "/wd/hub";

        if (System.getProperty("help") != null) {
            displayHelpMessage();
        }

    }
	
	private void loadProperties() throws IOException { 				
        try {
            // read in config settings
            List<String> confFiles = ["config/config.groovy","config/test_data.groovy"]
            testConfiguration.readConfiguration(confFiles, environment);

            // extract a browser version, if one exists
            // at the moment, to pass a version into the application,
            // supply the following system property
            //    -Dbrowser="ie,8"
            if (browser.contains(",")) {
                String[] browserProps = browser.split(",");
                if (browserProps.length == 2) {
                    browser = browserProps[0].trim();
                    browserVersion = browserProps[1].trim();
                } else if (browserProps.length == 1) {
                    browser = browserProps[0].trim();
                } else {
                    throw new IllegalArgumentException("Unsupported/unknown arguments supplied to browser property: " + browser);
                }

            }

            // override the base URL, if necessary
            if (baseUrlOverride != null) {
                baseUrl = baseUrlOverride;
            } else {
                baseUrl = testConfiguration.getBaseURL()
            }

            // set up some test properties
            testAccountUsername = testConfiguration.getTestAccountUsername()
            testAccountPassword = testConfiguration.getTestAccountPassword()
            guestEntitlements = testConfiguration.getGuestEntitlements()
            guestPoints = testConfiguration.getGuestPoints()
            receivedRewards = testConfiguration.getReceivedPoints()
            guest.setUsername(testAccountUsername)
            guest.setPassword(testAccountPassword)
            guest.setEntitlements(guestEntitlements )
            guest.setPoints(guestPoints)
            guest.setReceivedRewards(receivedRewards)

            dmrUrl = testConfiguration.getDmrURL()
			movieDetailsUrl = testConfiguration.getMovieDetailsUrl()
            myCollectionSortOptions = testConfiguration.getMyCollectionSortOptions()
            favoritesSortOptions = testConfiguration.getFavoritesSortOptions()
            moviesSortOptions = testConfiguration.getMoviesSortOptions()
            movieCategoryPaths = testConfiguration.getMovieCategoryPaths()
            contactUsCategory = testConfiguration.getContactUsCategory()
            vaultUsername = testConfiguration.getVaultUsername()
            vaultPassword = testConfiguration.getVaultPassword()
            closeCaptioningOptions = testConfiguration.getCloseCaptioningOptions()
            rewardsCode = testConfiguration.getRewardsCode()
            invalidRewardsCode = testConfiguration.getInvalidRewardsCode()
            videoGUIDs = testConfiguration.getVideoGUIDs()
            accountDetails = testConfiguration.getAccountDetails()


            // log it
            log.info("Test environment settings:");
            log.info("\t Test Environment            = " + environment);
            log.info("\t Browser                     = " + browser);
            log.info("\t Execute Test Remotely       = " + isTestRemote);
            log.info("\t Remote Host                 = " + remoteHost);
            log.info("\t Remote Port                 = " + remotePort);
            log.info("\t Remote Hub                  = " + remoteHub);
            log.info("\t Base URL                    = " + baseUrl);
        } catch (Exception e) {
            // Exit the test, if we encounter any sort of configuration/property errors
            System.out.println();
            System.out.println(e.getMessage());
            System.exit(-1);
        }


	}

    private void displayHelpMessage() {
        String message = new StringBuilder()
            .append("\n\n")
            .append("Usage: mvn clean verify [maven options] [JVM options]\n")
            .append("\n")
            .append("JVM Options:\n\n")
            .append("\t-Denvironment=[qa|dev|ci|staging|prod]    Define the environment in which to run the test. If this option\n")
            .append("\t                                       is not provided, default to 'qa'\n\n")
            .append("\t-DbaseUrl=[URL to base address]        The URL to the application under test. Providing this option\n")
            .append("\t                                       overrides the 'baseUrl' settings in the configuration file\n")
            .append("\t                                       Example: http://my.base.url:8888/\n\n")
            .append("\t-Dbrowser=[firefox|chrome|ie|safari]   Define the browser to use. If this option is not\n")
            .append("\t                                       provided, default to using 'firefox'\n\n")
            .append("\t-DisTestRemote=[true|false]            Determines whether the test should be run remotely. This parameter\n")
            .append("\t                                       must be used along with the 'remoteHost' parameter.\n\n")
            .append("\t-DremoteHost=[hostname]                The host name of the remote Selenium hub\n\n")
            .append("\t-DremotePort=[number]                  The port number of the remote Selenium hub. If this option is\n")
            .append("\t                                       not provided, default to 4444\n\n")
            .append("\t-DisBrowserMaximized=[true|false]      Maximize the browser. If this option is not provided, default to 'false'\n\n")
            .append("\t-DisScreenShotSupported=[true|false]   If 'true', then screen shots shall be taken for any failing\n")
            .append("\t                                       scenarios. If 'false', then skip taking screen shots. If this option\n")
            .append("\t                                       is not provided, default to 'true'\n\n")
            .append("\t-Dhelp                                 Prints this message\n").toString();


        System.out.println(message);
        System.exit(0);
    }

    private WebDriver loadSelenium() {
        switch (browser) {
            case "firefox":
                try {
                    driver = loadFirefoxDriver();
                } catch (Exception e) {
                    log.error(e.getMessage());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
                break;
            case "ie":
                try {
                    driver = loadInternetExplorerDriver();
                } catch (Exception e) {
                    log.error(e.getMessage());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
                break;
            case "chrome":
                try {
                    driver = loadChromeDriver();
                } catch (Exception e) {
                    log.error(e.getMessage());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
                break;
            case "safari":
                try {
                    driver = loadSafariDriver();
                } catch (Exception e) {
                    log.error(e.getMessage());
                    System.out.println(e.getMessage());
                    System.exit(1);
                }
                break;
            default:
                throw new IllegalArgumentException("The supplied browser '" + browser + "' is not supported at this time.");
        }

        if (isTestRemote) {
            sessionId = driver.properties['sessionId'] as SessionId
            log.info("Running test against the following session: '{}'", sessionId)
        }

        if (isBrowserMaximized) {
            driver.manage().window().maximize();
        }

        return driver

    }

    private WebDriver loadFirefoxDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        if (browserVersion != null) {
            capabilities.setVersion(browserVersion);
        }

        if (isTestRemote) {
            if (System.getenv('SAUCE_BAMBOO_BUILDNUMBER')) {
                println ">>>>> BAMBOO BUILD KEY = ${System.getenv('SAUCE_BAMBOO_BUILDNUMBER')}"
                capabilities.setCapability('build', System.getenv('SAUCE_BAMBOO_BUILDNUMBER'))
            }
            return new Augmenter().augment(new RemoteWebDriver(new URL(remoteHub), capabilities));
        } else {
            return new FirefoxDriver(capabilities);
        }
    }

    private WebDriver loadInternetExplorerDriver() throws MalformedURLException {
        // set the REQUIRED system property to locate the IEDriver
        System.setProperty("webdriver.ie.driver", "C:/twds/IEDriverServer.exe");

        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
        capabilities.setBrowserName(DesiredCapabilities.internetExplorer().getBrowserName());
        if (browserVersion != null) {
            capabilities.setVersion(browserVersion);
        }

        if (isTestRemote) {
            return new Augmenter().augment(new RemoteWebDriver(new URL(remoteHub), capabilities));
        } else {
            return new InternetExplorerDriver(capabilities);
        }
    }

    public WebDriver loadSafariDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.safari();
        if (browserVersion != null) {
            capabilities.setVersion(browserVersion);
        }

        if (isTestRemote) {
            return new Augmenter().augment(new RemoteWebDriver(new URL(remoteHub), capabilities));
        } else {
            return new SafariDriver(capabilities);
        }
    }

    private WebDriver loadChromeDriver() throws MalformedURLException {
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        if (browserVersion != null) {
            capabilities.setVersion(browserVersion);
        }

        if (isTestRemote) {
            return new Augmenter().augment(new RemoteWebDriver(new URL(remoteHub), capabilities));
        } else {
            return new ChromeDriver(capabilities);
        }
    }
}
