base.url = 'http://qa.disneymoviesanywhere.com/?home'

environments {
    dev {
        base.url = 'https://dev.disneymoviesanywhere.com/?home'
        dmr.url = 'http://staging2.disneymovierewards.go.com/member/index.htm'
        movie_details.url = 'http://dev.disneymoviesanywhere.com/movie/'
    }
    ci {
        base.url = 'https://ci.disneymoviesanywhere.com/?home'
        dmr.url = 'http://dmr2.spika.com/'
        movie_details.url = 'http://ci.disneymoviesanywhere.com/movie/'
    }
    qa {
        base.url = 'https://qa.disneymoviesanywhere.com/?home'
        dmr.url = 'http://dmr2.spika.com/'
        movie_details.url = 'http://qa.disneymoviesanywhere.com/movie/'
    }
    staging {
        base.url = 'https://staging.disneymoviesanywhere.com/?home'
        dmr.url = 'http://staging2.disneymovierewards.go.com/member/index.htm'
        movie_details.url = 'http://staging.disneymoviesanywhere.com/movie/'
    }
    prod {
        base.url = 'https://www.disneymoviesanywhere.com'
        dmr.url = 'http://www.disneymovierewards.go.com/member/index.htm'
        movie_details.url = 'http://www.disneymoviesanywhere.com/movie/'
    }
    preview {
        base.url = 'https://preview.disneymoviesanywhere.com'
        dmr.url = 'http://www.disneymovierewards.go.com/member/index.htm'
        movie_details.url = 'http://preview.disneymoviesanywhere.com/movie/'
    }
    load {
        base.url = 'https://load.disneymoviesanywhere.com/?home'
        dmr.url = 'http://staging.disneymovierewards.go.com/'
        movie_details.url = 'http://load.disneymoviesanywhere.com/movie/'
    }
}