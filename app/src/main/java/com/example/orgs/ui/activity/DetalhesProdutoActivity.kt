package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityDetalhesProdutoBinding
import com.example.orgs.extensions.formatarMoedaBrasileira
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {

  private var produtoId: Long = 0L
  private var produto: Produto? = null
  private val binding by lazy {
    ActivityDetalhesProdutoBinding.inflate(layoutInflater)
  }
  private val produtoDao by lazy {
    AppDatabase.instancia(this).produtoDao()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    tentaCarregarProduto()
  }

  override fun onResume() {
    super.onResume()
    buscaProduto()
  }

  private fun buscaProduto() {
    produto = produtoDao.buscaPorId(produtoId)
    produto?.let {
      prencheCampos(it)
    } ?: finish()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.detalhes_produto_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {

    when (item.itemId) {
      R.id.menu_detalhes_produto_remover -> {
        produto?.let { produtoDao.remove(it) }
        finish()
      }
      R.id.menu_detalhes_produto_editar -> {
        Intent(this, FormularioProdutoActivity::class.java).apply {
          putExtra("produto", produto)
          startActivity(this)
        }
      }
    }


    return super.onOptionsItemSelected(item)
  }

  private fun tentaCarregarProduto() {
    produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
  }

  private fun prencheCampos(produtoCarregado: Produto) {
    with(binding) {
      activityDetalhesProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
      activityDetalhesProdutoTitulo.text = produtoCarregado.nome
      activityDetalhesProdutoDescricao.text = produtoCarregado.descricao
      activityDetalhesProdutoPreco.text = produtoCarregado.valor.formatarMoedaBrasileira()
    }
  }
}