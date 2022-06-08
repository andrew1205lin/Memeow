package com.example.memeow.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import android.util.Log
import com.example.memeow.feature_main.data.repository.FakeMemeRepository
import com.example.memeow.feature_main.domain.repository.MemeRepository
import com.example.memeow.feature_main.domain.use_case.AddMeme
import com.example.memeow.feature_main.domain.use_case.DeleteMeme
import com.example.memeow.feature_main.domain.use_case.GetMemes
import com.example.memeow.feature_main.domain.use_case.MemeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /*Database*/


    /*Repository*/
    @Provides
    @Singleton
    fun provideNoteRepository(@ApplicationContext context: Context): MemeRepository {
        Log.d("","inside provideNoteRepository")
        return FakeMemeRepository(context.contentResolver)
    }

    /*UseCases*/
    @Provides
    @Singleton
    fun provideNoteUseCases(repository: MemeRepository): MemeUseCases {
        Log.d("","inside provideNoteUseCases")
        return MemeUseCases(
            getMemes = GetMemes(repository),
            deleteMeme = DeleteMeme(repository),
            addMeme = AddMeme(repository)
        )
    }

}