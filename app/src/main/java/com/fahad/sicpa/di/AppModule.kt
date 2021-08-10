package com.fahad.sicpa.di

import com.fahad.sicpa.repositories.article.ArticleRepository
import com.fahad.sicpa.repositories.article.IArticleRepository
import com.fahad.sicpa.repositories.article.remote.ArticleApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideArticleApi(retrofit: Retrofit): ArticleApi = retrofit.create(ArticleApi::class.java)

    @Provides
    @Singleton
    fun provideArticleRepository(repository: ArticleRepository): IArticleRepository = repository
}