package br.com.mariodias.dearbook.data.local

import androidx.room3.Database
import androidx.room3.RoomDatabase
import br.com.mariodias.dearbook.data.local.dao.LibraryDao
import br.com.mariodias.dearbook.data.local.entity.LibraryEntity

@Database(entities = [LibraryEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {

    abstract fun libraryDao(): LibraryDao
}