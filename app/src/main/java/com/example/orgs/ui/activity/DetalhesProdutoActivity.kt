package com.example.orgs.ui.activity

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.databinding.ActivityDetalhesProdutoBinding
import com.example.orgs.extensions.formatarMoedaBrasileira
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto

class DetalhesProdutoActivity : AppCompatActivity() {

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

  private fun tentaCarregarProduto() {
    intent.getParcelableExtra<Produto>("produto")?.let { produtoCarregado ->
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