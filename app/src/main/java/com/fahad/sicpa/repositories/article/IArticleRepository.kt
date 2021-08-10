package com.fahad.sicpa.repositories.article

import com.fahad.sicpa.models.Article
import com.fahad.sicpa.network.source.Resource
import com.fahad.sicpa.repositories.article.remote.requests.SearchArticleRequest
import kotlinx.coroutines.flow.Flow

interface IArticleRepository {

    fun searchArticles(request: SearchArticleRequest): Flow<Resource<List<Article>>>

    fun getMostEmailedArticles(): Flow<Resource<List<Article>>>

    fun getMostSharedArticles(): Flow<Resource<List<Article>>>

    fun getMostViewedArticles(): Flow<Resource<List<Article>>>
}