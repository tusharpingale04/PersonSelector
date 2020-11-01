package com.tushar.personselector.di.module

import android.app.Application
import androidx.annotation.NonNull
import androidx.room.Room
import com.tushar.personselector.db.PersonDao
import com.tushar.personselector.db.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class PersistenceModule {

  @Provides
  @Singleton
  fun provideDatabase(@NonNull application: Application): AppDatabase {
    return Room
      .databaseBuilder(application, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
      .build()
  }

  @Provides
  @Singleton
  fun providePersonDao(@NonNull database: AppDatabase): PersonDao {
    return database.getPerson()
  }

}
