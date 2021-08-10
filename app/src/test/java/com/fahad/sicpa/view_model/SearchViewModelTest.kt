package com.fahad.sicpa.view_model

import com.fahad.sicpa.activities.search.SearchState
import com.fahad.sicpa.activities.search.SearchViewModel
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
class SearchViewModelTest {

    private lateinit var articleRepository: IArticleRepository
    private lateinit var searchViewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)

        articleRepository = MockArticleRepository()
        searchViewModel = SearchViewModel(articleRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun searchState_test() {
        runBlockingTest {
            val results = mutableListOf<SearchState>()
            val job = launch {
                searchViewModel.state.toList(results)
            }

            searchViewModel.searchArticle("test")

            assertThat(results[0]).isEqualTo(SearchState.Init)
            assertThat(results[1]).isEqualTo(SearchState.LoadingSearchArticle)
            assertThat(results[2])
                .isEqualTo(SearchState.GetSearchArticleSuccess(MockArticleRepository.SEARCH_ARTICLES))

            job.cancel()
        }
    }
}