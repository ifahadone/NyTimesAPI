package com.fahad.sicpa.repositories.article

import com.fahad.sicpa.models.Article
import com.fahad.sicpa.network.source.Error
import com.fahad.sicpa.network.source.Resource
import com.fahad.sicpa.repositories.article.remote.ArticleApi
import com.fahad.sicpa.repositories.article.remote.requests.SearchArticleRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class ArticleRepository @Inject constructor(
    private val articleApi: ArticleApi
) : IArticleRepository {

    override fun searchArticles(request: SearchArticleRequest): Flow<Resource<List<Article>>> {
        return flow {
            val result = getResponse(
                request = {
                    articleApi.searchArticles(
                        query = request.query
                    )
                }
            )

            val transformed = result.transform { response ->
                response.response.docs.map { doc ->
                    Article(
                        title = doc.headline.main,
                        publishedDate = doc.publishedDate
                    )
                }
            }

            emit(transformed)
        }.flowOn(Dispatchers.IO)
    }

    override fun getMostEmailedArticles(): Flow<Resource<List<Article>>> {
        return flow {
            val result = getResponse(
                request = {
                    articleApi.getMostEmailedArticles()
                }
            )

            val transformed = result.transform { response ->
                response.results.map { result ->
                    Article(
                        title = result.title,
                        publishedDate = result.publishedDate
                    )
                }
            }

            emit(transformed)
        }.flowOn(Dispatchers.IO)
    }

    override fun getMostSharedArticles(): Flow<Resource<List<Article>>> {
        return flow {
            val result = getResponse(
                request = {
                    articleApi.getMostSharedArticles()
                }
            )

            val transformed = result.transform { response ->
                response.results.map { result ->
                    Article(
                        title = result.title,
                        publishedDate = result.publishedDate
                    )
                }
            }

            emit(transformed)
        }.flowOn(Dispatchers.IO)
    }

    override fun getMostViewedArticles(): Flow<Resource<List<Article>>> {
        return flow {
            val result = getResponse(
                request = {
                    articleApi.getMostViewedArticles()
                }
            )

            val transformed = result.transform { response ->
                response.results.map { result ->
                    Article(
                        title = result.title,
                        publishedDate = result.publishedDate
                    )
                }
            }

            emit(transformed)
        }.flowOn(Dispatchers.IO)
    }

    private suspend fun <T> getResponse(
        request: suspend () -> Response<T>
    ): Resource<T> {
        return try {
            val result = request.invoke()
            if (result.isSuccessful) {
                if (result.body() != null) {
                    Resource.Success(result.body()!!)
                } else {
                    Resource.EmptySuccess
                }
            } else {
                Resource.Failed(Error(-1, result.errorBody()?.string() ?: ""))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Failed(Error(-2, e.localizedMessage))
        }
    }
}