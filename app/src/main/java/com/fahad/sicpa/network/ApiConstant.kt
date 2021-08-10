package com.fahad.sicpa.network

object ApiConstant {

    const val APP_DOMAIN = "https://api.nytimes.com/svc/"

    private const val MOST_POPULAR = "mostpopular/v2"

    const val APP_PATH_SEARCH_ARTICLES = "search/v2/articlesearch.json"
    const val APP_PATH_GET_MOST_EMAILED_ARTICLES = "$MOST_POPULAR/emailed/1.json"
    const val APP_PATH_GET_MOST_SHARED_ARTICLES = "$MOST_POPULAR/shared/1/facebook.json"
    const val APP_PATH_GET_MOST_VIEWED_ARTICLES = "$MOST_POPULAR/viewed/7.json"
}