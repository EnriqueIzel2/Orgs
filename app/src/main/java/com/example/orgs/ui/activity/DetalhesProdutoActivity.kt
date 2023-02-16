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

private const val TAG = "Detalhes Produto"

class DetalhesProdutoActivity : AppCompatActivity() {

  private lateinit var produto: Produto
  private val binding by lazy {
    ActivityDetalhesProdutoBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    tentaCarregarProduto()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.detalhes_produto_menu, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (::produto.isInitialized) {
      val db = AppDatabase.instancia(this)
      val produtoDao = db.produtoDao()

      when (item.itemId) {
        R.id.menu_detalhes_produto_remover -> {
          produtoDao.remove(produto)
          finish()
        }
        R.id.menu_detalhes_produto_editar -> {
          Intent(this, FormularioProdutoActivity::class.java).apply {
            putExtra("produto", produto)
            startActivity(this)
          }
        }
      }
    }

    return super.onOptionsItemSelected(item)
  }

  private fun tentaCarregarProduto() {
    intent.getParcelableExtra<Produto>("produto")?.let { produtoCarregado ->
      produto = produtoCarregado
      prencheCampos(produtoCarregado)
    } ?: finish()
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