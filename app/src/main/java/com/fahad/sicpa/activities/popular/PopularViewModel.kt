package com.fahad.sicpa.activities.popular

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fahad.sicpa.network.source.Resource
import com.fahad.sicpa.repositories.article.IArticleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PopularViewModel @Inject constructor(
    private val articleRepository: IArticleRepository
) : ViewModel() {

    private val _state = MutableStateFlow<PopularState>(PopularState.Init)
    internal val state: StateFlow<PopularState>
        get() = _state

    internal fun getPopularArticles(type: PopularArticleType) {
        viewModelScope.launch {
            val api = when (type) {
                PopularArticleType.MOST_VIEWED -> articleRepository.getMostViewedArticles()
                PopularArticleType.MOST_SHARED -> articleRepository.getMostSharedArticles()
                PopularArticleType.MOST_EMAILED -> articleRepository.getMostEmailedArticles()
            }
            api.onStart {
                _state.value = PopularState.LoadingPopularArticle
            }.collect { result ->
                when (result) {
                    is Resource.Success -> {
                        _state.value = PopularState.GetPopularArticleSuccess(result.data)
                    }

                    is Resource.Failed -> {
                        _state.value = PopularState.GetPopularArticleFailed(result.error)
                    }

                    else -> Unit
                }
            }
        }
    }
}