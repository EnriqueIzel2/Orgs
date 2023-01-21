package com.example.orgs.ui.recyclerview.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.databinding.ProdutoItemBinding
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class ListaProdutosAdapter(
  private val context: Context,
  produtos: List<Produto>,
  var quandoClicaNoItemListener: (produto: Produto) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

  private val produtos = produtos.toMutableList()

  inner class ViewHolder(binding: ProdutoItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val nome = binding.produtoItemNome
    private val descricao = binding.produtoItemDescricao
    private val valor = binding.produtoItemValor
    private val imagem = binding.imageView
    private lateinit var produto: Produto

    init {
      binding.root.setOnClickListener {
        Log.i("ListaAdapter", "clicando no item")
        if (::produto.isInitialized) {
          quandoClicaNoItemListener(produto)
        }
      }
    }

    fun vincula(produto: Produto) {
      nome.text = produto.nome
      descricao.text = produto.descricao
      val valorEmMoeda: String = formataParaMoedaBrasileira(produto.valor)
      valor.text = valorEmMoeda
      imagem.tentaCarregarImagem(produto.imagem)
    }

    private fun formataParaMoedaBrasileira(valor: BigDecimal): String {
      val formatador: NumberFormat = NumberFormat.getCurrencyInstance(Locale("pt", "br"))

      return formatador.format(valor)
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val binding = ProdutoItemBinding.inflate(
      LayoutInflater.from(context),
      parent,
      false
    )

    return ViewHolder(binding)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val produto = produtos[position]
    holder.vincula(produto)
  }

  override fun getItemCount(): Int = produtos.size

  fun atualiza(produtos: List<Produto>) {
    this.produtos.clear()
    this.produtos.addAll(produtos)
    notifyDataSetChanged()
  }
}
