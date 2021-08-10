package com.fahad.sicpa.repositories

import com.fahad.sicpa.mock.MockArticleRepository
import com.fahad.sicpa.network.source.Resource
import com.fahad.sicpa.repositories.article.IArticleRepository
import com.fahad.sicpa.repositories.article.remote.requests.SearchArticleRequest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class ArticleRepositoryTest {

    private lateinit var articleRepository: IArticleRepository

    @Before
    fun setUp() {
        articleRepository = MockArticleRepository()
    }

    @After
    fun tearDown() {
    }

    @Test
    fun searchArticles_test() {
        runBlockingTest {
            val request = SearchArticleRequest(
                query = "test"
            )
            articleRepository.searchArticles(request)
                .collect { result ->
                    assertThat(result)
                        .isEqualTo(Resource.Success(MockArticleRepository.SEARCH_ARTICLES))
                }
        }
    }

    @Test
    fun getMostEmailedArticles_test() {
        runBlockingTest {
            articleRepository.getMostEmailedArticles()
                .collect { result ->
                    assertThat(result)
                        .isEqualTo(Resource.Success(MockArticleRepository.MOST_EMAILED_ARTICLES))
                }
        }
    }

    @Test
    fun getMostSharedArticles_test() {
        runBlockingTest {
            articleRepository.getMostSharedArticles()
                .collect { result ->
                    assertThat(result)
                        .isEqualTo(Resource.Success(MockArticleRepository.MOST_SHARED_ARTICLES))
                }
        }
    }

    @Test
    fun getMostViewedArticles_test() {
        runBlockingTest {
            articleRepository.getMostViewedArticles()
                .collect { result ->
                    assertThat(result)
                        .isEqualTo(Resource.Success(MockArticleRepository.MOST_VIEWED_ARTICLES))
                }
        }
    }
}