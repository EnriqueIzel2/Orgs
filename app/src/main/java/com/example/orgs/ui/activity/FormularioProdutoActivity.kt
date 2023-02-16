package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto
import com.example.orgs.ui.dialog.FormularioImagemDialog
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {
  private val binding by lazy {
    ActivityFormularioProdutoBinding.inflate(layoutInflater)
  }
  private var url: String? = null
  private var idProduto = 0L

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    title = "Cadastrar Produto"
    setContentView(binding.root)

    configuraBotaoSalvar()

    binding.activityFormularioProdutoImagem.setOnClickListener {
      FormularioImagemDialog(this).mostra(url) { it ->
        url = it
        binding.activityFormularioProdutoImagem.tentaCarregarImagem(url)
      }
    }

    intent.getParcelableExtra<Produto>("produto")?.let { produtoCarregado ->
      idProduto = produtoCarregado.id
      url = produtoCarregado.imagem
      binding.activityFormularioProdutoImagem.tentaCarregarImagem(produtoCarregado.imagem)
      binding.activityFormularioProdutoNome.setText(produtoCarregado.nome)
      binding.activityFormularioProdutoDescricao.setText(produtoCarregado.descricao)
      binding.activityFormularioProdutoValor.setText(produtoCarregado.valor.toPlainString())
    }
  }

  private fun configuraBotaoSalvar() {
    val botaoSalvar = binding.buttonSalvar
    val db = AppDatabase.instancia(this)

    val produtoDao = db.produtoDao()

    botaoSalvar.setOnClickListener {
      val produtoNovo = criaProduto()
      if (idProduto > 0) {
        produtoDao.atualiza(produtoNovo)
      } else {
        produtoDao.salva(produtoNovo)
      }
      finish()
    }
  }

  private fun criaProduto(): Produto {
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
      id = idProduto,
      nome = nome,
      descricao = descricao,
      valor = valor,
      imagem = url
    )
  }
}