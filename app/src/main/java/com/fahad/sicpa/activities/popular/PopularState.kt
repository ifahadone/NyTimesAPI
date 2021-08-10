package com.fahad.sicpa.activities.popular

import com.fahad.sicpa.models.Article
import com.fahad.sicpa.network.source.Error

sealed class PopularState {
    object Init : PopularState()

    object LoadingPopularArticle : PopularState()

    data class GetPopularArticleSuccess(val data: List<Article>) : PopularState()

    data class GetPopularArticleFailed(val error: Error) : PopularState()
}