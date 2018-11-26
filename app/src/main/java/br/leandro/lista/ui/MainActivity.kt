package br.leandro.lista.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import br.leandro.lista.R
import br.leandro.lista.api.ListaAPI
import br.leandro.lista.api.RetrofitClient
import br.leandro.lista.model.Produto
import br.leandro.lista.model.Lista
import br.leandro.lista.model.User
import br.leandro.lista.ui.adapter.ListaProdutosAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class MainActivity : AppCompatActivity(), Serializable {

    private val reqCodeForm = 1
    private var listas : List<Lista>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = intent.extras.get("user") as User

        Log.i("TAG", "Chegou aqui o user "+ user.name)

        btNovo.setOnClickListener {
            var i = Intent(this, ListaFormActivity::class.java)
            i.putExtra("lista", Lista(null, "", arrayListOf(Produto("","")), ""))
            startActivityForResult(i, reqCodeForm)
        }

        tilListaFilter.editText?.setOnKeyListener { v, keyCode, event ->
            filterListas()
            false
        }

        btAbout.setOnClickListener {
            var i = Intent(this, AboutActivity::class.java)
            startActivity(i)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onResume() {
        super.onResume()

        carregarDados()
    }

    fun carregarDados() {
        var api = RetrofitClient.getInstance().create(ListaAPI::class.java)

        loading.visibility = View.VISIBLE

        api.listaListas().enqueue(object : Callback<List<Lista>> {

            override fun onResponse(call: Call<List<Lista>>?, response: Response<List<Lista>>?) {
                if (response?.isSuccessful() == true) {
                    setListas(response?.body())
                } else {
                    error.visibility = View.VISIBLE
                    tvMensagemErro.text = response?.errorBody()?.charStream()?.readText();
                }

                loading.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Lista>>?, t: Throwable?) {
                Log.i("TAG", t?.message)
                error.visibility = View.VISIBLE
                tvMensagemErro.text = t?.message
                loading.visibility = View.GONE
            }
        })
    }

    fun setListas(listas: List<Lista>?) {

        this.listas = listas
        loadReciclyView(this.listas)
    }

    fun loadReciclyView(listas: List<Lista>?) {
        listas.let {
            rvListas.adapter = ListaProdutosAdapter(listas!!, this)
            val layoutManager = LinearLayoutManager(this)
            rvListas.layoutManager = layoutManager
        }
    }

    fun filterListas() {
        Log.i("TAG", "KeyListener")
        var filter = tilListaFilter.editText?.text.toString()
        if (!filter.isNullOrBlank()) {
            loadReciclyView(this.listas?.filter{ r -> r.nome.contains(filter, true) })
        } else {
            loadReciclyView(this.listas)
        }
    }
}
