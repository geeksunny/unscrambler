package com.radicalninja.unscrambler.data.response

import com.google.gson.annotations.SerializedName

data class SearchQueryResponse(
    val batchcomplete: String,
    @SerializedName("query") val result: QueryResponse
)

data class QueryResponse(
    val pages: Map<String, PagesEntry>
)