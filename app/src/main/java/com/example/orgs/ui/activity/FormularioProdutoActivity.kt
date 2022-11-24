package com.example.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.orgs.R
import com.example.orgs.model.Produto
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val botaoSalvar = findViewById<Button>(R.id.button_salvar)
    botaoSalvar.setOnClickListener {
      val campoNome = findViewById<EditText>(R.id.edit_nome)
      val campoDescricao = findViewById<EditText>(R.id.edit_descricao)
      val campoValor = findViewById<EditText>(R.id.edit_valor)
      val nome = campoNome.text.toString()
      val descricao = campoDescricao.text.toString()
      val valorEmTexto = campoValor.text.toString()
      val valor = if (valorEmTexto.isBlank()) {
        BigDecimal.ZERO
      } else {
        BigDecimal(valorEmTexto)
      }

      val produtoNovo = Produto(
        nome = nome,
        descricao = descricao,
        valor = valor
      )
      Log.i("FormularioProduto", "onCreate: $produtoNovo")
    }
  }
}