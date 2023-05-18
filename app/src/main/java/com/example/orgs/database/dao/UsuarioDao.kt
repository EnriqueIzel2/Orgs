package com.example.orgs.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.orgs.model.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

  @Insert
  suspend fun salva(usuario: Usuario)

  @Query("SELECT * FROM Usuario WHERE id = :usuarioID AND senha = :senha")
  suspend fun autentica(usuarioID: String, senha: String): Usuario?

  @Query("SELECT * FROM Usuario WHERE id = :usuarioID")
  fun buscaPorID(usuarioID: String): Flow<Usuario>
}