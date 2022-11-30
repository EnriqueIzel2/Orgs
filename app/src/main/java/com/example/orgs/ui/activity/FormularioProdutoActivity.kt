package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.dao.ProdutosDao
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity() {
  private val binding by lazy {
    ActivityFormularioProdutoBinding.inflate(layoutInflater)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(binding.root)

    configuraBotaoSalvar()
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
    val campoNome = binding.editNome
    val campoDescricao = binding.editDescricao
    val campoValor = binding.editValor

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
      valor = valor
    )
  }
}