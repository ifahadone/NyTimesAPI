package com.fahad.sicpa.activities.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.sicpa.network.source.Resource
import com.fahad.sicpa.repositories.article.IArticleRepository
import com.fahad.sicpa.repositories.article.remote.requests.SearchArticleRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val articleRepository: IArticleRepository
) : ViewModel() {

    private val _state = MutableStateFlow<SearchState>(SearchState.Init)
    internal val state: StateFlow<SearchState>
        get() = _state

    private var searchJob: Job? = null

    internal fun searchArticle(query: String) {
        searchJob?.cancel()

        searchJob = viewModelScope.launch {
            articleRepository.searchArticles(
                SearchArticleRequest(
                    query = query
                )
            ).onStart {
                _state.value = SearchState.LoadingSearchArticle
            }.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = SearchState.GetSearchArticleSuccess(result.data)
                    }

                    is Resource.Failed -> {
                        _state.value = SearchState.GetSearchArticleFailed(result.error)
                    }

                    else -> Unit
                }
            }
        }
    }
}