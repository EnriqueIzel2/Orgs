package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.example.orgs.dao.ProdutosDao
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.model.Produto
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigDecimal

class ListaProdutosActivity : AppCompatActivity() {
  private val dao = ProdutosDao()
  private val adapter = ListaProdutosAdapter(context = this, produtos = dao.buscaTodos())
  private val binding by lazy {
    ActivityListaProdutosBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    configuraRecyclerView()
    configuraFab()

    val db = Room.databaseBuilder(
      this,
      AppDatabase::class.java,
      "orgs.db"
    ).allowMainThreadQueries().build()

    val produtoDao = db.produtoDao()
    produtoDao.salva(
      Produto(
        nome = "teste nome 1",
        descricao = "teste desc 1",
        valor = BigDecimal("10.51")
      )
    )
  }

  override fun onResume() {
    super.onResume()
    adapter.atualiza(dao.buscaTodos())
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
        putExtra("produto", it)
      }
      startActivity(intent)
    }
  }
}