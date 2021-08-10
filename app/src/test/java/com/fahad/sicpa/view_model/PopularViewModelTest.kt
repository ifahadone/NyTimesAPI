package com.fahad.sicpa.view_model

import com.fahad.sicpa.activities.popular.PopularArticleType
import com.fahad.sicpa.activities.popular.PopularState
import com.fahad.sicpa.activities.popular.PopularViewModel
import com.fahad.sicpa.mock.MockArticleRepository
import com.fahad.sicpa.repositories.article.IArticleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PopularViewModelTest {

    private lateinit var articleRepository: IArticleRepository
    private lateinit var popularViewModel: PopularViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        articleRepository = MockArticleRepository()
        popularViewModel = PopularViewModel(articleRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun popularState_test() {
        runBlockingTest {
            val results = mutableListOf<PopularState>()
            val job = launch {
                popularViewModel.state.toList(results)
            }

            popularViewModel.getPopularArticles(PopularArticleType.MOST_VIEWED)

            assertThat(results[0]).isEqualTo(PopularState.Init)
            assertThat(results[1]).isEqualTo(PopularState.LoadingPopularArticle)
            assertThat(results[2])
                .isEqualTo(PopularState.GetPopularArticleSuccess(MockArticleRepository.MOST_VIEWED_ARTICLES))

            job.cancel()
        }
    }
}