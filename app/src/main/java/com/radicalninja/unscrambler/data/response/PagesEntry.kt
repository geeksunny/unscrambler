package com.radicalninja.unscrambler.data.response

data class PagesEntry(
    val ns: Int,
    val pageid: Int?,
    val missing: String?,
    val title: String
) {

    fun isValidWord() = missing == null

}