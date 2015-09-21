package studiotech.environment

import com.google.common.io.Resources
import org.apache.commons.lang.Validate

import static java.lang.String.format

public class TestConfiguration {
    private Map config;
    private String testEnvironment
    ConfigObject mergedConfig = []


    public void readConfiguration(List<String> configFileNames, String testEnvironment) throws MalformedURLException {
        Validate.notNull(configFileNames, format("The supplied configuration file, '%s', cannot be undefined.", configFileNames));
        Validate.notEmpty(configFileNames, format("The supplied configuration file, '%s', cannot be empty.", configFileNames));
        Validate.notEmpty(testEnvironment, format("The supplied environment setting, '%s', cannot be empty", testEnvironment));

        this.testEnvironment = testEnvironment

        if (! testEnvironment.matches("qa|dev|ci|staging|load|prod|preview")) {
            throw new IllegalArgumentException(format("The supplied environment setting, '%s', is not supported! " +
                    "Supported options include: qa, dev, ci, staging, load, prod or preview.", testEnvironment));
        }

        for(configFileName in configFileNames) {
            URL urlToConfigurationFile = Resources.getResource(configFileName);
            ConfigObject conf = new ConfigSlurper(testEnvironment.toLowerCase()).parse(urlToConfigurationFile);
            mergedConfig = mergedConfig.merge(conf)
        }
        config = mergedConfig.flatten()
    }

    public String getBaseURL() {
        String setting = (String) config.get("base.url")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getDmrURL() {
        String setting = (String) config.get("dmr.url")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getMovieDetailsUrl() {
        String setting = (String) config.get("movie_details.url")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }
	
    public String getTestAccountUsername() {
        String setting = (String) config.get("userdata.withMovies.username")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getTestAccountPassword() {
        String setting = (String) config.get("userdata.withMovies.password")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getNoMoviesTestAccountUsername() {
        String setting = (String) config.get("userdata.noMovies.username")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getNoMoviesTestAccountPassword() {
        String setting = (String) config.get("userdata.noMovies.password")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public List<String> getGuestEntitlements() {
        List<String> setting = (List<String>) config.get("userdata.withMovies.entitlements")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public List<String> getMyCollectionSortOptions() {
        List<String> setting = (List<String>) config.get("testdata.sortOptions.mycollection")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public List<String> getFavoritesSortOptions() {
        List<String> setting = (List<String>) config.get("testdata.sortOptions.favorites")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public List<String> getMoviesSortOptions() {
        List<String> setting = (List<String>) config.get("testdata.sortOptions.movies")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public List<String> getMovieCategoryPaths() {
        List<String> setting = (List<String>) config.get("testdata.movies.movieCategoryPaths")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getGuestPoints() {
        String setting = (String) config.get("userdata.withMovies.points")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getReceivedPoints() {
        String setting = (String) config.get("userdata.received_points")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }
    
    public List<String> getContactUsCategory() {
        List<String> setting = (List<String>) config.get("testdata.contactUs.category")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    private void verifyPropertySettingIsNotNull(Object configSetting) {
        Validate.notNull(configSetting, "The reference to the supplied configuration setting cannot be undefined: '${configSetting}'. " +
                "Please check the configuration file settings for the '${testEnvironment}' environment.")
    }

    public String getVaultUsername() {
        String setting = (String) config.get("userdata.vault.username")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getVaultPassword() {
        String setting = (String) config.get("userdata.vault.password")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public List<String> getCloseCaptioningOptions() {
        List<String> setting = (List<String>) config.get("userdata.closeCaptioningOptions")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getRewardsCode() {
        String setting = (String) config.get("userdata.rewardsCode")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public String getInvalidRewardsCode() {
        String setting = (String) config.get("userdata.invalidRewardsCode")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    public List<String> getVideoGUIDs() {
        List<String> setting = (List<String>) config.get("userdata.guid")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }

    HashMap<String,String> getAccountDetails(){
        HashMap<String,String> setting= (HashMap<String,String>) config.get("userdata.accountCreation.accountDetails")
        verifyPropertySettingIsNotNull(setting)
        return setting
    }



}
