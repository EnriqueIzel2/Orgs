package com.example.orgs.ui.recyclerview.adapter

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.example.orgs.databinding.ProdutoItemBinding
import com.example.orgs.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

class ListaProdutosAdapter(
  private val context: Context,
  produtos: List<Produto>
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

  private val produtos = produtos.toMutableList()

  class ViewHolder(binding: ProdutoItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val nome = binding.produtoItemNome
    private val descricao = binding.produtoItemDescricao
    private val valor = binding.produtoItemValor
    private val imagem = binding.imageView

    fun vincula(produto: Produto, context: Context) {
      val imageLoader = ImageLoader.Builder(context)
        .components {
          if (Build.VERSION.SDK_INT >= 28) {
            add(ImageDecoderDecoder.Factory())
          } else {
            add(GifDecoder.Factory())
          }
        }.build()
      nome.text = produto.nome
      descricao.text = produto.descricao
      val valorEmMoeda: String = formataParaMoedaBrasileira(produto.valor)
      valor.text = valorEmMoeda
      imagem.load(produto.imagem, imageLoader)
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
    holder.vincula(produto, context)
  }

  override fun getItemCount(): Int = produtos.size

  fun atualiza(produtos: List<Produto>) {
    this.produtos.clear()
    this.produtos.addAll(produtos)
    notifyDataSetChanged()
  }
}
