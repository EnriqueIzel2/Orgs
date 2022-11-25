package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.model.Produto
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
    recyclerView.adapter = ListaProdutosAdapter(
      context = this, produtos = listOf(
        Produto(nome = "teste", descricao = "Teste task", valor = BigDecimal("19.99")),
        Produto(nome = "teste2", descricao = "Teste task2", valor = BigDecimal("21.99")),
      )
    )

    val fab: FloatingActionButton = findViewById(R.id.floatingActionButton)
    fab.setOnClickListener {
      val intent = Intent(this, FormularioProdutoActivity::class.java)
      startActivity(intent)
    }
  }
}