package br.com.mariodias.dearbook.di

import br.com.mariodias.dearbook.data.repository.LibraryRepositoryImpl
import br.com.mariodias.dearbook.domain.repository.LibraryRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class LibraryRepositoryModule {


    @Binds
    abstract fun bindLibraryRepository(
        libraryRepositoryImpl: LibraryRepositoryImpl
    ): LibraryRepository
}