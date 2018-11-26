package br.leandro.lista.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.leandro.lista.R
import br.leandro.lista.model.Produto
import kotlinx.android.synthetic.main.item_ingrediente_form.view.*

class ListaProdutosFormAdapter(private val Produtos: List<Produto>,
                                   private val context: Context) : RecyclerView.Adapter<ListaProdutosFormAdapter.ListaProdutosFormHolder>() {

    var views = mutableListOf<View>()

    override fun getItemCount(): Int {
        return Produtos.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaProdutosFormHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ingrediente_form, parent, false)
        views.add(view)
        return ListaProdutosFormHolder(view)
    }

    override fun onBindViewHolder(holder: ListaProdutosFormHolder, position: Int) {
        val produto = Produtos[position]
        holder?.let {
            it.bindView(produto)
        }
    }

    class ListaProdutosFormHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(produto: Produto) {
            itemView.tilProdutoNome.editText?.setText(produto.nome)
            itemView.tilProdutoQtde.editText?.setText(produto.qtde)
        }
    }
}