package br.leandro.lista.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.leandro.lista.R
import br.leandro.lista.model.Ingrediente
import kotlinx.android.synthetic.main.item_ingrediente_view.view.*

class ListaIngredientesViewAdapter(private val ingredientes: List<Ingrediente>,
                                   private val context: Context) : RecyclerView.Adapter<ListaIngredientesViewAdapter.ListaIngredientesViewHolder>() {

    override fun getItemCount(): Int {
        return ingredientes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaIngredientesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ingrediente_view, parent, false)
        return ListaIngredientesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListaIngredientesViewHolder, position: Int) {
        val ingrediente = ingredientes[position]
        holder?.let {
            it.bindView(ingrediente)
        }
    }

    class ListaIngredientesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(ingrediente: Ingrediente) {
            itemView.tvIngredienteNome.text = ingrediente.nome
            itemView.tvIngredienteQtde.text = ingrediente.qtde
        }
    }
}