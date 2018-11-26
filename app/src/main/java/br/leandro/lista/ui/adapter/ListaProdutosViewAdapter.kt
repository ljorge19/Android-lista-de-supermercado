package br.leandro.lista.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.leandro.lista.R
import br.leandro.lista.model.Produto
import kotlinx.android.synthetic.main.item_ingrediente_view.view.*

class ListaProdutosViewAdapter(private val produto: List<Produto>,
                               private val context: Context) : RecyclerView.Adapter<ListaProdutosViewAdapter.ListaIngredientesViewHolder>() {

    override fun getItemCount(): Int {
        return produto.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaIngredientesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ingrediente_view, parent, false)
        return ListaIngredientesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaIngredientesViewHolder, position: Int) {
        val ingrediente = produto[position]
        holder?.let {
            it.bindView(ingrediente)
        }
    }

    class ListaIngredientesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(produto: Produto) {
            itemView.tvIngredienteNome.text = produto.nome
            itemView.tvIngredienteQtde.text = produto.qtde
        }
    }
}