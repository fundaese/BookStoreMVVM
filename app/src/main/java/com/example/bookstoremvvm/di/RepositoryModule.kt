package com.example.bookstoremvvm.di

import com.example.bookstoremvvm.data.repository.BooksRepository
import com.example.bookstoremvvm.data.source.remote.BookService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideBookRepository(bookService: BookService) : BooksRepository =
        BooksRepository(bookService)

}