package com.fahad.sicpa.repositories.article.remote.responses

import com.squareup.moshi.Json

data class PopularArticleListResponse(
    @Json(name = "results")
    val results: List<Result>
) {

    data class Result(
        @Json(name = "title")
        val title: String,

        @Json(name = "published_date")
        val publishedDate: String
    )
}
