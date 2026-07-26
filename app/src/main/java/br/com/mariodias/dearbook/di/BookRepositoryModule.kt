package br.com.mariodias.dearbook.di

import br.com.mariodias.dearbook.data.repository.BookRepositoryImpl
import br.com.mariodias.dearbook.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BookRepositoryModule {

    @Binds
    abstract fun bindBookRe(bookRepositoryImpl: BookRepositoryImpl): BookRepository
}