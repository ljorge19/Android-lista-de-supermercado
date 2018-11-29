package br.leandro.lista.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.leandro.lista.R
import br.leandro.lista.model.Lista
import kotlinx.android.synthetic.main.item_lista.view.*
import android.content.Intent
import br.leandro.lista.ui.ListaViewActivity


class ListaProdutosAdapter(private val listas: List<Lista>,
                           private val context: Context) : RecyclerView.Adapter<ListaProdutosAdapter.ListaListasHolder>() {

    override fun getItemCount(): Int {
        return listas.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListaListasHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_lista, parent, false)
        return ListaListasHolder(view, context)
    }

    override fun onBindViewHolder(holder: ListaListasHolder, position: Int) {
        val receita = listas[position]
        holder?.let {
            it.bindView(receita)
        }
    }

    class ListaListasHolder(itemView: View, val context: Context) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var lista: Lista? = null

        init {
            itemView.setOnClickListener(this)
        }

        fun bindView(lista: Lista) {
            this.lista = lista
            itemView.tvReceita.text = lista.nome
            itemView.tvReceitaId.text = lista.id
        }

        override fun onClick(itemView: View?) {
            Log.i("TAG", "Elemento "+ itemView?.tvReceita?.text + " clicado. ID: "+itemView?.tvReceitaId?.text)
            var i = Intent(context, ListaViewActivity::class.java)
            i.putExtra("lista", lista)
            context.startActivity(i)
        }
    }
}