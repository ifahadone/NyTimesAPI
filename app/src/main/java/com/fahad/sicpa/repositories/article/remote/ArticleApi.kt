package com.fahad.sicpa.repositories.article.remote

import com.fahad.sicpa.BuildConfig
import com.fahad.sicpa.network.ApiConstant.APP_PATH_GET_MOST_EMAILED_ARTICLES
import com.fahad.sicpa.network.ApiConstant.APP_PATH_GET_MOST_SHARED_ARTICLES
import com.fahad.sicpa.network.ApiConstant.APP_PATH_GET_MOST_VIEWED_ARTICLES
import com.fahad.sicpa.network.ApiConstant.APP_PATH_SEARCH_ARTICLES
import com.fahad.sicpa.repositories.article.remote.responses.PopularArticleListResponse
import com.fahad.sicpa.repositories.article.remote.responses.SearchArticleListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET(APP_PATH_SEARCH_ARTICLES)
    suspend fun searchArticles(
        @Query("q") query: String,
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): Response<SearchArticleListResponse>

    @GET(APP_PATH_GET_MOST_EMAILED_ARTICLES)
    suspend fun getMostEmailedArticles(
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): Response<PopularArticleListResponse>

    @GET(APP_PATH_GET_MOST_SHARED_ARTICLES)
    suspend fun getMostSharedArticles(
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): Response<PopularArticleListResponse>

    @GET(APP_PATH_GET_MOST_VIEWED_ARTICLES)
    suspend fun getMostViewedArticles(
        @Query("api-key") apiKey: String = BuildConfig.API_KEY
    ): Response<PopularArticleListResponse>
}