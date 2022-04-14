package com.radicalninja.unscrambler.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.radicalninja.unscrambler.data.db.converter.StringListTypeConverters
import com.radicalninja.unscrambler.data.network.response.SearchQueryResponse

// TODO: Add table index to restrict each entry to a unique set of letters

@Entity(tableName = "letter_groups")
data class LetterGroup(@ColumnInfo(name = "letters") val letters: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null

    @ColumnInfo(name = "valid_words")
    @TypeConverters(StringListTypeConverters::class)
    var _validWords: MutableList<String> = mutableListOf()

    //fun getValidWords() = _validWords as List<String>

    fun getPossibleWordsSet(): Set<String> {
        TODO("Need to create strings of possible words, max 50 at a time. ex: abc|acb|bac|bca")
    }

    fun setValidWordsFromApiResponse(apiResponse: SearchQueryResponse) {
        _validWords.clear()
        apiResponse.result.pages.forEach { (s, pagesEntry) ->
            if (pagesEntry.isValidWord()) {
                _validWords.add(pagesEntry.title)
            }
        }
    }

    companion object {
        fun fromString(unsortedLetters: String) =
            LetterGroup(unsortedLetters.toCharArray().sorted().joinToString(""))
    }

}