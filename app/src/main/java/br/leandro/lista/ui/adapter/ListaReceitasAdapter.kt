package br.leandro.lista.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.leandro.lista.R
import br.leandro.lista.model.Receita
import kotlinx.android.synthetic.main.item_receita.view.*
import android.content.Intent
import br.leandro.lista.ui.ReceitaViewActivity


class ListaReceitasAdapter(private val receitas: List<Receita>,
                           private val context: Context) : RecyclerView.Adapter<ListaReceitasAdapter.ListaReceitasHolder>() {

    override fun getItemCount(): Int {
        return receitas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaReceitasHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_receita, parent, false)
        return ListaReceitasHolder(view, context)
    }

    override fun onBindViewHolder(holder: ListaReceitasHolder, position: Int) {
        val receita = receitas[position]
        holder?.let {
            it.bindView(receita)
        }
    }

    class ListaReceitasHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var receita: Receita? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(receita: Receita) {
            this.receita = receita
            itemView.tvReceita.text = receita.nome
            itemView.tvReceitaId.text = receita.id
        }

        override fun onClick(itemView: View?) {
            Log.i("TAG", "Elemento "+ itemView?.tvReceita?.text + " clicado. ID: "+itemView?.tvReceitaId?.text)
            var i = Intent(context, ReceitaViewActivity::class.java)
            i.putExtra("receita", receita)
            context.startActivity(i)
        }
    }
}