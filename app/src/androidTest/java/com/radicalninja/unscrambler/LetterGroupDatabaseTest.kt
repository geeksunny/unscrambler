package com.radicalninja.unscrambler

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.radicalninja.unscrambler.data.db.LetterGroupDao
import com.radicalninja.unscrambler.data.db.LetterGroupDatabase
import com.radicalninja.unscrambler.data.db.entity.LetterGroup
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LetterGroupDatabaseTest {

    private lateinit var letterGroupDao: LetterGroupDao
    private lateinit var db: LetterGroupDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using in-memory database for simple test database creation
        db = Room.inMemoryDatabaseBuilder(context, LetterGroupDatabase::class.java)
            // Allow main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        letterGroupDao = db.letterGroupDao
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGet() {
        val letterGroupToInsert = LetterGroup("abc")
        letterGroupDao.insert(letterGroupToInsert)
        val letterGroupRetrieved = letterGroupDao.getLetterGroup("abc")
        assert(letterGroupRetrieved != null)
    }
}