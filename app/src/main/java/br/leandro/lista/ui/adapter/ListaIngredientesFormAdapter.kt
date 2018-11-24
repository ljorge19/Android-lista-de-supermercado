package br.leandro.lista.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.leandro.lista.R
import br.leandro.lista.model.Ingrediente
import kotlinx.android.synthetic.main.item_ingrediente_form.view.*

class ListaIngredientesFormAdapter(private val ingredientes: List<Ingrediente>,
                                   private val context: Context) : RecyclerView.Adapter<ListaIngredientesFormAdapter.ListaIngredientesFormHolder>() {

    var views = mutableListOf<View>()

    override fun getItemCount(): Int {
        return ingredientes.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaIngredientesFormHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_ingrediente_form, parent, false)
        views.add(view)
        return ListaIngredientesFormHolder(view)
    }

    override fun onBindViewHolder(holder: ListaIngredientesFormHolder, position: Int) {
        val ingrediente = ingredientes[position]
        holder?.let {
            it.bindView(ingrediente)
        }
    }

    class ListaIngredientesFormHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(ingrediente: Ingrediente) {
            itemView.tilIngredienteNome.editText?.setText(ingrediente.nome)
            itemView.tilIngredienteQtde.editText?.setText(ingrediente.qtde)
        }
    }
}