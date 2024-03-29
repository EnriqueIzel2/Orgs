package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto
import com.example.orgs.ui.dialog.FormularioImagemDialog
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormularioProdutoActivity : UsuarioBaseActivity() {
  private val binding by lazy {
    ActivityFormularioProdutoBinding.inflate(layoutInflater)
  }
  private val produtoDao by lazy {
    AppDatabase.instancia(this).produtoDao()
  }

  private var url: String? = null
  private var produtoId = 0L

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = "Cadastrar Produto"
    setContentView(binding.root)

    configuraBotaoSalvar()

    binding.activityFormularioProdutoImagem.setOnClickListener {
      FormularioImagemDialog(this).mostra(url) {
        url = it
        binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
      }
    }

    tentaCarregarProduto()
    tentaBuscarProduto()

    lifecycleScope.launch {
      usuario.filterNotNull().collect {

      }
    }
  }

  private fun tentaBuscarProduto() {
    lifecycleScope.launch {
      produtoDao.buscaPorId(produtoId).collect { produto ->
        produto?.let {
          title = "Alterar produto"
          preencheCampos(it)
        }
      }
    }
  }

  private fun tentaCarregarProduto() {
    produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
  }

  private fun preencheCampos(produto: Produto) {
    title = "Altera Produto"
    url = produto.imagem
    binding.activityFormularioProdutoImagem.tentaCarregarImagem(produto.imagem)
    binding.activityFormularioProdutoNome.setText(produto.nome)
    binding.activityFormularioProdutoDescricao.setText(produto.descricao)
    binding.activityFormularioProdutoValor.setText(produto.valor.toPlainString())
  }

  private fun configuraBotaoSalvar() {
    val botaoSalvar = binding.buttonSalvar

    botaoSalvar.setOnClickListener {
      lifecycleScope.launch {
        usuario.value?.let { usuario ->
          val produtoNovo = criaProduto(usuario.id)
          produtoDao.salva(produtoNovo)
          finish()
        }
      }
    }
  }

  private fun criaProduto(usuarioID: String): Produto {
    val campoNome = binding.activityFormularioProdutoNome
    val campoDescricao = binding.activityFormularioProdutoDescricao
    val campoValor = binding.activityFormularioProdutoValor

    val nome = campoNome.text.toString()
    val descricao = campoDescricao.text.toString()
    val valorEmTexto = campoValor.text.toString()
    val valor = if (valorEmTexto.isBlank()) {
      BigDecimal.ZERO
    } else {
      BigDecimal(valorEmTexto)
    }

    return Produto(
      id = produtoId,
      nome = nome,
      descricao = descricao,
      valor = valor,
      imagem = url,
      usuarioID = usuarioID
    )
  }
}