package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
    adapter.atualiza(produtoDao.buscarTodos())
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