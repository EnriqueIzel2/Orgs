package com.example.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.orgs.model.Produto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutoDao {

  @Query("SELECT * FROM produto")
  fun buscarTodos(): Flow<List<Produto>>

  @Query("SELECT * FROM produto WHERE usuarioID = :usuarioID")
  fun buscaTodosDoUsuario(usuarioID: String) : Flow<List<Produto>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun salva(vararg produto: Produto)

  @Delete
  suspend fun remove(produto: Produto)

  @Query("SELECT * FROM produto WHERE id = :id")
  fun buscaPorId(id: Long): Flow<Produto?>
}