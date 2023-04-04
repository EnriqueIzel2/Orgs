package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class ListaProdutosActivity : AppCompatActivity() {
  private val adapter = ListaProdutosAdapter(context = this)
  private val binding by lazy {
    ActivityListaProdutosBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    configuraRecyclerView()
    configuraFab()
  }

  override fun onResume() {
    super.onResume()
    val db = AppDatabase.instancia(this)
    val produtoDao = db.produtoDao()
    val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
      Toast.makeText(this@ListaProdutosActivity, "Deu ruim", Toast.LENGTH_SHORT).show()
    }

    val mainScope = MainScope()

    mainScope.launch(handler) {
      MainScope().launch(handler) {
        throw Exception("Em outro escopo")
      }
      throw Exception("Lançando na coroutine")
      val produtos = withContext(Dispatchers.IO) {
        produtoDao.buscarTodos()
      }
      adapter.atualiza(produtos)
    }
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