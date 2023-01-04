package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.dao.ProdutosDao
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

    AlertDialog.Builder(this)
      .setTitle("Teste")
      .setView(R.layout.formulario_imagem)
      .setMessage("Teste Message")
      .setPositiveButton("Confirmar") { _, _ -> }
      .setNegativeButton("Cancelar") { _, _ -> }
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
  }
}