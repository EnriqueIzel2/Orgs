package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.orgs.dao.ProdutosDao
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.databinding.FormularioImagemBinding
import com.example.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {
  private val binding by lazy {
    ActivityFormularioProdutoBinding.inflate(layoutInflater)
  }
  private var url: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    configuraBotaoSalvar()

    binding.activityFormularioProdutoImagem.setOnClickListener {
      val bindingFormularioImagem = FormularioImagemBinding.inflate(layoutInflater)
      bindingFormularioImagem.formularioImagemBotaoCarregar.setOnClickListener {
        val url = bindingFormularioImagem.formularioImagemUrl.text.toString()
        bindingFormularioImagem.formularioImagemImageView.load(url)
      }

      AlertDialog.Builder(this)
        .setView(bindingFormularioImagem.root)
        .setPositiveButton("Confirmar") { _, _ ->
          url = bindingFormularioImagem.formularioImagemUrl.text.toString()
          binding.activityFormularioProdutoImagem.load(url)
        }
        .setNegativeButton("Cancelar") { _, _ -> }
        .show()
    }
  }

  private fun configuraBotaoSalvar() {
    val botaoSalvar = binding.buttonSalvar
    val dao = ProdutosDao()

    botaoSalvar.setOnClickListener {
      val produtoNovo = criaProduto()
      dao.adiciona(produtoNovo)
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
      nome = nome,
      descricao = descricao,
      valor = valor,
    )
  }
}