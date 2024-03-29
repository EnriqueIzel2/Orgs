package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

class ListaProdutosActivity : UsuarioBaseActivity() {
  private val adapter = ListaProdutosAdapter(context = this)
  private val binding by lazy {
    ActivityListaProdutosBinding.inflate(layoutInflater)
  }
  private val produtoDao by lazy {
    val db = AppDatabase.instancia(this)
    db.produtoDao()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    configuraRecyclerView()
    configuraFab()

    lifecycleScope.launch {
      launch {
        usuario
          .filterNotNull()
          .collect { usuario ->
            buscaProdutosUsuario(usuario.id)
          }
      }
    }
  }

  private suspend fun buscaProdutosUsuario(usuarioID: String) {
    produtoDao.buscaTodosDoUsuario(usuarioID).collect { produtos ->
      adapter.atualiza(produtos)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.menu_lista_produtos, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.menu_lista_produtos_sair_app -> {
        lifecycleScope.launch {
          deslogaUsuario()
        }
      }
    }
    return super.onOptionsItemSelected(item)
  }

  private fun configuraFab() {
    val fab: FloatingActionButton = binding.floatingActionButton
    fab.setOnClickListener {
      val intent = Intent(this, FormularioProdutoActivity::class.java)
      startActivity(intent)
    }
  }

  private fun configuraRecyclerView() {
    val recyclerView: RecyclerView = binding.recyclerView
    recyclerView.adapter = adapter
    adapter.quandoClicaNoItem = {
      val intent = Intent(this, DetalhesProdutoActivity::class.java).apply {
        putExtra(CHAVE_PRODUTO_ID, it.id)
      }
      startActivity(intent)
    }

    adapter.quandoClicaEditar = {
      Log.i("Clique longo", "configuraRecyclerView: clicou editar")
    }

    adapter.quandoClicaRemover = {
      Log.i("Clique longo", "configuraRecyclerView: clicou remover")
    }
  }
}