package com.example.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.databinding.ProdutoItemBinding
import com.example.orgs.extensions.tentaCarregarImagem
import com.example.orgs.model.Produto
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.*

class ListaProdutosAdapter(
  private val context: Context,
  produtos: List<Produto> = emptyList(),
  var quandoClicaNoItem: (produto: Produto) -> Unit = {},
  var quandoClicaEditar: (produto: Produto) -> Unit = {},
  var quandoClicaRemover: (produto: Produto) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

  private val produtos = produtos.toMutableList()

  inner class ViewHolder(private val binding: ProdutoItemBinding) :
    RecyclerView.ViewHolder(binding.root), PopupMenu.OnMenuItemClickListener {

    private lateinit var produto: Produto

    init {
      itemView.setOnClickListener {
        if (::produto.isInitialized) {
          quandoClicaNoItem(produto)
        }
      }

      itemView.setOnLongClickListener {
        PopupMenu(context, itemView).apply {
          menuInflater.inflate(R.menu.detalhes_produto_menu, menu)
          setOnMenuItemClickListener(this@ViewHolder)
        }.show()
        true
      }
    }

    fun vincula(produto: Produto) {
      val nome = binding.produtoItemNome
      val descricao = binding.produtoItemDescricao
      val valor = binding.produtoItemValor
      val imagem = binding.imageView

      this.produto = produto
      nome.text = produto.nome
      descricao.text = produto.descricao
      val valorEmMoeda: String = formataParaMoedaBrasileira(produto.valor)
      valor.text = valorEmMoeda
      imagem.tentaCarregarImagem(produto.imagem)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
      item?.let {
        when (it.itemId) {
          R.id.menu_detalhes_produto_editar -> {
            quandoClicaEditar(produto)
          }
          R.id.menu_detalhes_produto_remover -> {
            quandoClicaRemover(produto)
          }
        }
      }

      return true
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
