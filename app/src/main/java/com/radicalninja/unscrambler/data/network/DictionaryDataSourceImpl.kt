package com.radicalninja.unscrambler.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.radicalninja.unscrambler.data.network.response.SearchQueryResponse
import com.radicalninja.unscrambler.internal.NoConnectivityException

class DictionaryDataSourceImpl(
    private val apiWiktionaryService: ApiWiktionaryService
) : DictionaryDataSource {

    private val _lastFetchedWordData = MutableLiveData<SearchQueryResponse>()
    override val lastFetchedWordData: LiveData<SearchQueryResponse>
        get() = _lastFetchedWordData

    override suspend fun fetchWords(titles: String) {
        try {
            val fetchedWordData = apiWiktionaryService
                .getWords(titles)
            _lastFetchedWordData.postValue(fetchedWordData.body())
        } catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }
    }
}