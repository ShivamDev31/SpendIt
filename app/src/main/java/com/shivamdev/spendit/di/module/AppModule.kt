package com.shivamdev.spendit.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.shivamdev.spendit.common.constants.PREF_FILE_NAME
import com.shivamdev.spendit.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by shivam on 01/02/18.
 */

@Module
class AppModule(private val application: Application) {

    @Provides
    @ApplicationContext
    fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE)
    }

    /*@Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "notes-db")
                .allowMainThreadQueries()
                .build()
    }

    @Provides
    @Singleton
    fun provideNotesDao(database: AppDatabase): NotesDao {
        return database.notesDao()
    }

    @Provides
    @Singleton
    fun provideNotesRepository(notesDao: NotesDao): NotesRepository {
        return NotesRepository(notesDao)
    }*/

    /*@Provides
    @Singleton
    fun provideFirebaseAuth() {

    }

    @Provides
    @Singleton
    fun provideFirebaseHelper() {

    }*/

}