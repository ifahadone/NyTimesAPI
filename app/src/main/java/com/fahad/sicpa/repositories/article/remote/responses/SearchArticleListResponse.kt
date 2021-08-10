package com.fahad.sicpa.repositories.article.remote.responses

import com.squareup.moshi.Json

data class SearchArticleListResponse(
    @Json(name = "response")
    val response: Response
) {

    data class Response(
        @Json(name = "docs")
        val docs: List<Doc>
    ) {

        data class Doc(
            @Json(name = "headline")
            val headline: Headline,

            @Json(name = "pub_date")
            val publishedDate: String
        ) {

            data class Headline(
                @Json(name = "main")
                val main: String
            )
        }
    }
}
