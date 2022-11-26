package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.dao.ProdutosDao
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListaProdutosActivity : AppCompatActivity() {
  private val dao = ProdutosDao()
  private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_lista_produtos)
    configuraRecyclerView()
    configuraFab()
  }

  override fun onResume() {
    super.onResume()
    adapter.atualiza(dao.buscaTodos())
  }

  private fun configuraFab() {
    val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
    fab.setOnClickListener {
      val intent = Intent(this, FormularioProdutoActivity::class.java)
      startActivity(intent)
    }
  }

  private fun configuraRecyclerView() {
    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
    recyclerView.adapter = adapter
  }
}