package com.example.myprofilapp.database

import android.content.Context
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

object DatabaseModule {
    private var database: NoteDatabase? = null

    fun getDatabase(context: Context): NoteDatabase {
        if (database == null) {
            val driver = AndroidSqliteDriver(NoteDatabase.Schema, context, "notes.db")
            database = NoteDatabase(driver)
        }
        return database!!
    }
}
