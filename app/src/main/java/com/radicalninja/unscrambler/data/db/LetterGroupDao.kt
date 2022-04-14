package com.radicalninja.unscrambler.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.radicalninja.unscrambler.data.db.entity.LetterGroup

@Dao
interface LetterGroupDao {

    @Insert
    fun insert(letterGroup: LetterGroup)

    @Query("SELECT * FROM letter_groups WHERE letters = :sortedLetters LIMIT 1")
    fun getLetterGroup(sortedLetters: String): LetterGroup?

    @Query("SELECT * FROM letter_groups ORDER BY id DESC")
    fun getAllLetterGroups(): LiveData<List<LetterGroup>>

    @Query("DELETE FROM letter_groups")
    fun clear()
}