package com.tushar.personselector.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tushar.personselector.model.user.PersonDetail


@Database(entities = [PersonDetail::class], version = 1)
@TypeConverters(PersonListConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPerson(): PersonDao

    companion object {
        const val DATABASE_NAME = "person.db"
    }

}