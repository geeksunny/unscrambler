package com.radicalninja.unscrambler.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.radicalninja.unscrambler.data.db.entity.LetterGroup

@Database(entities = [LetterGroup::class], version = 1, exportSchema = false)
abstract class LetterGroupDatabase : RoomDatabase() {

    abstract val letterGroupDao: LetterGroupDao

    companion object {
        @Volatile
        private var INSTANCE: LetterGroupDatabase? = null

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                LetterGroupDatabase::class.java,
                "letter_group.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}