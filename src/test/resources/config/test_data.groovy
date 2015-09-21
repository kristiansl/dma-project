package config

//Cross environment test data
testdata {
    movies {
        movieCategoryPaths = ["all",
                              "star-wars",
                              "disney-pixar",
                              "marvel",
                              "disney-nature",
                              "classics",
                              "animation",
                              "action-and-adventure",
                              "princess",
                              "comedies",
                              "song-and-dance",
                              "disney-fairies",
                              "sports",
                              "romance",
                              "muppets",
                              "true-story",
                              "dogs",
                              "mickey-mouse",
                              "winnie-the-pooh",
                              "documentaries",
                              "westerns",
                              "scifi",
                              "best-2015",
                              "best-2014",
                              "best-2013",
                              "best-2012"]
    }

    sortOptions {
        mycollection = ['Date Added', 'Name', 'Release Date']
        movies = ['Release Date', 'Name']
        favorites = ['Date Added', 'Name']
    }

    contactUs{
        category = ['General','Technical','Code Related','I Like Something','I Don\'t Like Something','Other']
    }
}
environments {
    dev {
        userdata {
            withMovies {
                username = '10movies@dsaa.com'
                password = 'abcd1234'
                // Entitlements naming format is pulled form my-collection page HTML div attribute value of  - "data-analytics-movie-title":
                //<div class="col-xs-2 poster " data-analytics-movie-title="toy-story">
                entitlements = ['toy-story', 'toy-story-2', 'toy-story-3', 'a-bugs-life', 'cars', 'cars-2', 'cars-toon-maters-tall-tales',
                                'high-school-musical-3', '101-dalmatians-1996', 'aladdin', 'beverly-hills-chihuahua', 'john-carter',
                                'monsters-university', 'monsters-inc', 'alice-in-wonderland-2010']
                points = '400'

            }
            noMovies {
                username = 'nomovies_web@autodma.com'
                password = 'abcd1234'
                entitlements = []
                points = '0'
            }
            accountCreation {

                accountDetails = [['birthMonth':"May",'birthDay':"9",'birthYear':"1987",'password': "abcd1234",'firstName':"DEV",
                                   'middleName' :"web",'lastName':"DMA",'gender':"Female",'postalCode':"91202"]]
            }
            vault {
                username = 'vault_web@autodma.com'
                password = 'abcd1234'
            }
            closeCaptioningOptions = ['Font', 'Text Size', 'Text Color', 'Opacity', 'Text Effect', 'Text Align', 'Text Alignment', 'Background Color']
            rewardsCode = 'DIGITALTANGLED'
            invalidRewardsCode = 'DIGITALTANGLE'
            guid = ['394841667892', '388275779859', '393867331944']
            received_points = '400'

        }
    }

    ci {
        userdata {
            withMovies {
                username = 'ci10movies@autodma.com'
                password = 'abcd1234'
                // Entitlements naming format is pulled form my-collection page HTML div attribute value of  - "data-analytics-movie-title":
                //<div class="col-xs-2 poster " data-analytics-movie-title="toy-story">
                entitlements = ['toy-story', 'toy-story-2', 'toy-story-3', 'a-bugs-life',
                                'cars', 'cars-2', 'cars-toon-maters-tall-tales', 'high-school-musical-3',
                                'john-carter','tangled', 'alice-in-wonderland-2010', 'pirates-of-the-caribbean-at-worlds-end']
                points = '400'


            }
            noMovies {
                username = 'nomovies_web@autodma.com'
                password = 'abcd1234'
                entitlements = []
                points = '0'
            }
            accountCreation {

                accountDetails = [['birthMonth':"May",'birthDay':"9",'birthYear':"1987",'password': "abcd1234",'firstName':"CI",
                                   'middleName' :"web",'lastName':"DMA",'gender':"Female",'postalCode':"91202"]]
            }
            vault {
                username = 'vault_web@autodma.com'
                password = 'abcd1234'
            }
            closeCaptioningOptions = ['Font', 'Text Size', 'Text Color', 'Opacity', 'Text Effect', 'Text Align', 'Text Alignment', 'Background Color']
            rewardsCode = 'DIGITALTANGLED'
            invalidRewardsCode = 'DIGITALTANGLE'
            guid = ['394841667892', '388275779859', '393867331944']
            received_points = '400'
        }
    }

    qa {
        userdata {
            withMovies {
                username = 'qa_web@autodma.com'
                password = 'abcd1234'
                // Entitlements naming format is pulled form my-collection page HTML div attribute value of  - "data-analytics-movie-title":
                //<div class="col-xs-2 poster " data-analytics-movie-title="toy-story">
                entitlements = ['toy-story', 'toy-story-2', 'cars', 'toy-story-3',  'cars-2',
                                'cars-toon-maters-tall-tales', 'john-carter','tangled', 'alice-in-wonderland-2010' ,'wreck-it-ralph'
                                ]
                points = '200'

            }
            noMovies {
                username = 'nomovies_web@autodma.com'
                password = 'abcd1234'
                entitlements = []
                points = '0'
            }
            accountCreation {

                accountDetails = [['birthMonth':"May",'birthDay':"9",'birthYear':"1987",'password': "abcd1234",'firstName':"QA",
                                   'middleName' :"web",'lastName':"DMA",'gender':"Female",'postalCode':"91202"]]
                            }
            vault {
                username = 'vault_web@autodma.com'
                password = 'abcd1234'
            }
            closeCaptioningOptions = ['Font', 'Text Size', 'Text Color', 'Opacity', 'Text Effect', 'Text Align', 'Text Alignment', 'Background Color']
            rewardsCode = 'DIGITALTANGLED'
            invalidRewardsCode = 'DIGITALTANGLE'
            guid = ['394841667892', '396939331911']
            received_points = '200'
        }
    }

    staging {
        userdata {
            withMovies {
                username = 'stg_web@autodma.com'
                password = 'abcd1234'
                // Entitlements naming format is pulled form my-collection page HTML div attribute value of  - "data-analytics-movie-title":
                //<div class="col-xs-2 poster " data-analytics-movie-title="toy-story">
                entitlements = ['toy-story', 'toy-story-2', 'cars', 'toy-story-3',  'cars-2',
                                'cars-toon-maters-tall-tales', 'john-carter', 'alice-in-wonderland-2010']
                points = '150'

            }
            noMovies {
                username = 'nomovies_web@autodma.com'
                password = 'abcd1234'
                entitlements = []
                points = '0'
            }
            accountCreation {

                accountDetails = [['birthMonth':"June",'birthDay':"12",'birthYear':"1985",'password': "abcd1234",'firstName':"Staging",
                                   'middleName' :"web",'lastName':"DMA",'gender':"Female",'postalCode':"91202"]]
            }
            vault {
                username = 'vault_stg_web@autodma.com'
                password = 'abcd1234'
            }
            closeCaptioningOptions = ['Font', 'Text Size', 'Text Color', 'Opacity', 'Text Effect', 'Text Align', 'Text Alignment', 'Background Color']
            rewardsCode = 'StgDigitalMater'
            invalidRewardsCode = 'DIGITALTANGLE'
            guid = ['394841667892', '394841667892', '388275779859']
            received_points = '150'
        }
    }

    prod {
        userdata {
            withMovies {
                username = 'prod_web@autodma.com'
                password = 'abcd1234'
                // Entitlements naming format is pulled form my-collection page HTML div attribute value of  - "data-analytics-movie-title":
                //<div class="col-xs-2 poster " data-analytics-movie-title="toy-story">
                entitlements = ['monkeys-uncle', 'magnificent-rebel']
                points = '2'

            }
            noMovies {
                username = 'nomovies@autodma.com'
                password = 'abcd1234'
                entitlements = []
                points = '0'
            }
            accountCreation {

                accountDetails = [['birthMonth':"March",'birthDay':"12",'birthYear':"1985",'password': "abcd1234",'firstName':"Production",
                                   'middleName' :"web",'lastName':"DMA",'gender':"Female",'postalCode':"91205"]]
            }
            vault {
                username = 'vault_web@autodma.com'
                password = 'abcd1234'
            }
            closeCaptioningOptions = ['Font', 'Text Size', 'Text Color', 'Opacity', 'Text Effect', 'Text Align', 'Text Alignment', 'Background Color']
            rewardsCode = 'DIGITALTANGLED'
            invalidRewardsCode = 'DIGITALTANGLE'
            guid = ['394841667892', '394841667892', '388275779859']
            received_points = '200'
        }
    }

    preview {
        userdata {
            withMovies {
                username = 'preview_web@autodma.com'
                password = 'abcd1234'
                // Entitlements naming format is pulled form my-collection page HTML div attribute value of  - "data-analytics-movie-title":
                //<div class="col-xs-2 poster " data-analytics-movie-title="toy-story">
                entitlements = ['monkeys-uncle', 'magnificent-rebel', 'thor-the-dark-world']
                points = '152'

            }
            noMovies {
                username = 'nomovies@autodma.com'
                password = 'abcd1234'
                entitlements = []
                points = '0'
            }
            accountCreation {

                accountDetails = [['birthMonth':"March",'birthDay':"12",'birthYear':"1985",'password': "abcd1234",'firstName':"Production",
                                   'middleName' :"web",'lastName':"DMA",'gender':"Female",'postalCode':"91205"]]
            }
            vault {
                username = 'vault_web@autodma.com'
                password = 'abcd1234'
            }
            closeCaptioningOptions = ['Font', 'Text Size', 'Text Color', 'Opacity', 'Text Effect', 'Text Align', 'Text Alignment', 'Background Color']
            rewardsCode = 'DIGITALTANGLED'
            invalidRewardsCode = 'DIGITALTANGLE'
            guid = ['394841667892', '394841667892', '388275779859']
            received_points = '200'
        }
    }

    load {
        userdata {
            withMovies {
                username = 'loadval1@nullmail.starwave.com'
                password = 'abcd1234'
                // Entitlements naming format is pulled form my-collection page HTML div attribute value of  - "data-analytics-movie-title":
                //<div class="col-xs-2 poster " data-analytics-movie-title="toy-story">
                entitlements = ['country-coyote-goes-hollywood', 'd2-the-mighty-ducks', 'disneynature-oceans','dougs-first-movie',
                                'home-on-the-range', 'homeward-bound-2-lost-in-san-francisco','inspector-gadget','apple-dumpling-gang',
                                'game-plan', 'toy-story', 'toy-story-2', 'toy-story-3','alice-in-wonderland-2010']
                points = '*****'

            }
            noMovies {
                username = 'nomovies@autodma.com'
                password = 'abcd1234'
                entitlements = []
                points = '0'
            }
            accountCreation {

                accountDetails = [['birthMonth':"October",'birthDay':"12",'birthYear':"1985",'password': "abcd1234",'firstName':"load",
                                   'middleName' :"web",'lastName':"DMA",'gender':"Female",'postalCode':"98007"]]
            }
            vault {
                username = 'vault_web@autodma.com'
                password = 'abcd1234'
            }
            closeCaptioningOptions = ['Font', 'Text Size', 'Text Color', 'Opacity', 'Text Effect', 'Text Align', 'Text Alignment', 'Background Color']
            rewardsCode = 'DIGITALTANGLED'
            invalidRewardsCode = 'DIGITALTANGLE'
            guid = ['394841667892', '388275779859', '393867331944']
            received_points = '*****'
        }
    }
}