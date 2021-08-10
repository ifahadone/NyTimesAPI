package com.fahad.sicpa.mock

import com.fahad.sicpa.models.Article
import com.fahad.sicpa.network.source.Resource
import com.fahad.sicpa.repositories.article.IArticleRepository
import com.fahad.sicpa.repositories.article.remote.requests.SearchArticleRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockArticleRepository : IArticleRepository {

    companion object {
        val SEARCH_ARTICLES = listOf(
            Article(
                "first",
                "2021-05-24T21:35:22+0000"
            )
        )

        val MOST_EMAILED_ARTICLES = listOf(
            Article(
                "second",
                "2021-05-24T21:35:22+0000"
            )
        )

        val MOST_SHARED_ARTICLES = listOf(
            Article(
                "third",
                "2021-05-24T21:35:22+0000"
            )
        )

        val MOST_VIEWED_ARTICLES = listOf(
            Article(
                "forth",
                "2021-05-24T21:35:22+0000"
            )
        )
    }

    override fun searchArticles(request: SearchArticleRequest): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Success(SEARCH_ARTICLES))
        }
    }

    override fun getMostEmailedArticles(): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Success(MOST_EMAILED_ARTICLES))
        }
    }

    override fun getMostSharedArticles(): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Success(MOST_SHARED_ARTICLES))
        }
    }

    override fun getMostViewedArticles(): Flow<Resource<List<Article>>> {
        return flow {
            emit(Resource.Success(MOST_VIEWED_ARTICLES))
        }
    }
}