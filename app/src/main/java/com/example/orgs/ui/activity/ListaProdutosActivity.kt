package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.database.AppDatabase
import com.example.orgs.database.dao.ProdutoDao
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.model.Produto
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.*

class ListaProdutosActivity : AppCompatActivity() {
  private val TAG = "ListaProdutosActivity"
  private val adapter = ListaProdutosAdapter(context = this)
  private val binding by lazy {
    ActivityListaProdutosBinding.inflate(layoutInflater)
  }
  private val job = Job()
  private val dao by lazy {
    val db = AppDatabase.instancia(this)
    db.produtoDao()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    configuraRecyclerView()
    configuraFab()
  }

  override fun onResume() {
    super.onResume()
    val scope = MainScope()

//    scope.launch(job + handler + Dispatchers.IO + CoroutineName("primerio")) {
//      repeat(1000) {
//        Log.i(TAG, "onResume: coroutine está em execução $it")
//        delay(1000)
//      }
//    }

    scope.launch {
      val produtos = buscaTodosProdutos()
      adapter.atualiza(produtos)
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    job.cancel()
  }

  private suspend fun buscaTodosProdutos() =
    withContext(Dispatchers.IO) {
      dao.buscarTodos()
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