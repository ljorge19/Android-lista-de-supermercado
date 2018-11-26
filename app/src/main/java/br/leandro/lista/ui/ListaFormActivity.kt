package br.leandro.lista.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import br.leandro.lista.R
import br.leandro.lista.api.ListaAPI
import br.leandro.lista.api.RetrofitClient
import br.leandro.lista.model.Produto
import br.leandro.lista.model.Lista
import br.leandro.lista.ui.adapter.ListaProdutosFormAdapter
import br.leandro.lista.utils.MyDialog
import kotlinx.android.synthetic.main.activity_receita_form.*
import kotlinx.android.synthetic.main.item_ingrediente_form.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaFormActivity : AppCompatActivity() {

    private lateinit var lista: Lista
    private lateinit var adapter: ListaProdutosFormAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita_form)

        carregarDados()
        bindReceitaFields()

        btAddProduto.setOnClickListener {
            bindFieldReceita()
            lista.produtos.add(Produto("",""))
            bindReceitaFields()
        }

        btCancelar.setOnClickListener{
            var resultIntent = Intent()
            resultIntent.putExtra("lista", lista);
            setResult(1, resultIntent);
            finish();
        }

        btSalvar.setOnClickListener {
            var api = RetrofitClient.getInstance().create(ListaAPI::class.java)
            bindFieldReceita()
            if (lista.id == null) {
                api.postLista(lista).enqueue(object :  Callback<Lista> {
                    override fun onResponse(call: Call<Lista>?, response: Response<Lista>?) {
                        if (response?.isSuccessful() == true) {
                            lista = response.body()!!
                            var resultIntent = Intent()
                            resultIntent.putExtra("lista", lista);
                            setResult(0, resultIntent);
                            finish();
                        } else {
                            Log.i("TAG", "Erro ao salvar lista!")
                            messageDialog(R.string.api_error)
                        }
                    }

                    override fun onFailure(call: Call<Lista>?, t: Throwable?) {
                        Log.i("TAG", "Erro ao salvar lista! "+t?.message)
                        messageDialog(R.string.api_error)
                    }
                })
            } else {
                api.putLista(lista).enqueue(object :  Callback<Lista> {
                    override fun onResponse(call: Call<Lista>?, response: Response<Lista>?) {
                        if (response?.isSuccessful() == true) {
                            lista = response.body()!!
                            var resultIntent = Intent()
                            resultIntent.putExtra("lista", lista);
                            setResult(0, resultIntent);
                            finish();
                        } else {
                            Log.i("TAG", "Erro ao salvar lista!")
                            messageDialog(R.string.api_error)
                        }
                    }

                    override fun onFailure(call: Call<Lista>?, t: Throwable?) {
                        Log.i("TAG", "Erro ao salvar lista! "+t?.message)
                        messageDialog(R.string.api_error)
                    }
                })
            }
        }
    }

    override fun onBackPressed() {
        var resultIntent = Intent()
        resultIntent.putExtra("lista", lista);
        setResult(1, resultIntent);
        super.onBackPressed()
    }

    fun carregarDados() {
        lista = intent.extras.get("lista") as Lista
        Log.i("TAG", "Chegou aqui a lista "+ lista.nome)
    }

    fun bindReceitaFields() {
        Log.i("TAG", "Binding fields")
        tilReceitaNome.editText?.setText(lista.nome)
        adapter = ListaProdutosFormAdapter(lista.produtos, this)
        rvProdutos.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        rvProdutos.layoutManager = layoutManager

        tilComentario.editText?.setText(lista.comentario)
    }

    fun bindFieldReceita() {
        Log.i("TAG", "Binding fields")
        lista.nome = tilReceitaNome.editText?.text.toString()

        lista.produtos.clear()
        adapter.views.forEach {
            if (!it.tilProdutoNome.editText?.text.isNullOrBlank()) {
                lista.produtos.add(Produto(it.tilProdutoNome.editText?.text.toString(),
                        it.tilProdutoQtde.editText?.text.toString()))
            }
        }

        lista.comentario = tilComentario.editText?.text.toString()
    }

    fun messageDialog(msgId : Int) {
        MyDialog.messageDialog(this, msgId)
    }

}
