package com.fahad.sicpa.activities.search

import com.fahad.sicpa.models.Article
import com.fahad.sicpa.network.source.Error

sealed class SearchState {
    object Init : SearchState()

    object LoadingSearchArticle : SearchState()

    data class GetSearchArticleSuccess(val data: List<Article>) : SearchState()

    data class GetSearchArticleFailed(val error: Error) : SearchState()
}