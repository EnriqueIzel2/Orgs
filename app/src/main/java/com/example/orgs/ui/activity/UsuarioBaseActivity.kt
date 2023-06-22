package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.lifecycleScope
import com.example.orgs.database.AppDatabase
import com.example.orgs.extensions.vaiPara
import com.example.orgs.model.Usuario
import com.example.orgs.preferences.dataStore
import com.example.orgs.preferences.usuarioLogadoPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

abstract class UsuarioBaseActivity : AppCompatActivity() {

  private val usuarioDao by lazy {
    AppDatabase.instancia(this).usuarioDao()
  }

  private var _usuario: MutableStateFlow<Usuario?> = MutableStateFlow(null)
  protected var usuario: StateFlow<Usuario?> = _usuario

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    lifecycleScope.launch {
      verificaUsuarioLogado()
    }
  }

  private suspend fun verificaUsuarioLogado() {
    dataStore.data.collect { preferences ->
      preferences[usuarioLogadoPreferences]?.let { usuarioID ->
        buscaUsuario(usuarioID)
      } ?: vaiParaLogin()
    }
  }

  private suspend fun buscaUsuario(usuarioID: String) {
      _usuario.value = usuarioDao.buscaPorID(usuarioID).firstOrNull()
  }

  protected suspend fun deslogaUsuario() {
    dataStore.edit { preferences ->
      preferences.remove(usuarioLogadoPreferences)
    }
  }

  private fun vaiParaLogin() {
    vaiPara(LoginActivity::class.java) {
      addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
    }
    finish()
  }
}