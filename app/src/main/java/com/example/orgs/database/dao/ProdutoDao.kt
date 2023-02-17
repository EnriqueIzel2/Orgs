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
  fun buscarTodos(): List<Produto>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun salva(vararg produto: Produto)

  @Delete
  fun remove(produto: Produto)

  @Query("SELECT * FROM produto WHERE id = :id")
  fun buscaPorId(id: Long) : Produto?
}