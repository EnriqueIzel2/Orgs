package com.example.orgs.dao

import com.example.orgs.model.Produto
import java.math.BigDecimal

class ProdutosDao {

  fun adiciona(produto: Produto) {
    produtos.add(produto)
  }

  fun buscaTodos(): List<Produto> {
    return produtos.toList()
  }

  companion object {
    private val produtos = mutableListOf<Produto>(
      Produto(
        nome = "Cesta de frutas",
        descricao = "laranja, uva e jaca",
        valor = BigDecimal("25.76")
      )
    )
  }
}