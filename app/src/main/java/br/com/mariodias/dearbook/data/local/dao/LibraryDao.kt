package br.com.mariodias.dearbook.data.local.dao

import androidx.room3.Dao
import androidx.room3.Delete
import androidx.room3.Query
import androidx.room3.Upsert
import br.com.mariodias.dearbook.data.local.entity.LibraryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryDao {

    @Upsert
    suspend fun upsert(book: LibraryEntity)

    @Delete
    suspend fun remove(book: LibraryEntity)

    @Query("SELECT * FROM LibraryEntity")
    fun getAll(): Flow<List<LibraryEntity>>

    @Query("SELECT * FROM LibraryEntity WHERE id = :id")
    suspend fun getById(id: String): LibraryEntity?
}