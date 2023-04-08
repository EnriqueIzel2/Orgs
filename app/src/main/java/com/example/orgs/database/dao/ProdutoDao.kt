package com.example.orgs.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.orgs.model.Produto

@Dao
interface ProdutoDao {

  @Query("SELECT * FROM produto")
  suspend fun buscarTodos(): List<Produto>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun salva(vararg produto: Produto)

  @Delete
  suspend fun remove(produto: Produto)

  @Query("SELECT * FROM produto WHERE id = :id")
  suspend fun buscaPorId(id: Long) : Produto?
}