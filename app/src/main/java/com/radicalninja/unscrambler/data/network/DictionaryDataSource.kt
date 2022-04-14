package com.radicalninja.unscrambler.data.network

import androidx.lifecycle.LiveData
import com.radicalninja.unscrambler.data.network.response.SearchQueryResponse

interface DictionaryDataSource {
    val lastFetchedWordData: LiveData<SearchQueryResponse>

    suspend fun fetchWords(
        titles: String
    )
}