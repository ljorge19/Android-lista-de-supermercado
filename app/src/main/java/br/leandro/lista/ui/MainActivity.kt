package br.leandro.lista.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import br.leandro.lista.R
import br.leandro.lista.api.ReceitaAPI
import br.leandro.lista.api.RetrofitClient
import br.leandro.lista.model.Ingrediente
import br.leandro.lista.model.Receita
import br.leandro.lista.model.User
import br.leandro.lista.ui.adapter.ListaReceitasAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.error.*
import kotlinx.android.synthetic.main.loading.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.Serializable

class MainActivity : AppCompatActivity(), Serializable {

    private val reqCodeForm = 1
    private var receitas : List<Receita>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = intent.extras.get("user") as User

        Log.i("TAG", "Chegou aqui o user "+ user.name)

        btNovo.setOnClickListener {
            var i = Intent(this, ReceitaFormActivity::class.java)
            i.putExtra("receita", Receita(null, "", arrayListOf(Ingrediente("","")), ""))
            startActivityForResult(i, reqCodeForm)
        }

        tilReceitaFilter.editText?.setOnKeyListener { v, keyCode, event ->
            filterReceitas()
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
        var api = RetrofitClient.getInstance().create(ReceitaAPI::class.java)

        loading.visibility = View.VISIBLE

        api.listaReceitas().enqueue(object : Callback<List<Receita>> {

            override fun onResponse(call: Call<List<Receita>>?, response: Response<List<Receita>>?) {
                if (response?.isSuccessful() == true) {
                    setReceitas(response?.body())
                } else {
                    error.visibility = View.VISIBLE
                    tvMensagemErro.text = response?.errorBody()?.charStream()?.readText();
                }

                loading.visibility = View.GONE
            }

            override fun onFailure(call: Call<List<Receita>>?, t: Throwable?) {
                Log.i("TAG", t?.message)
                error.visibility = View.VISIBLE
                tvMensagemErro.text = t?.message
                loading.visibility = View.GONE
            }
        })
    }

    fun setReceitas(receitas: List<Receita>?) {

        this.receitas = receitas
        loadReciclyView(this.receitas)
    }

    fun loadReciclyView(receitas: List<Receita>?) {
        receitas.let {
            rvReceitas.adapter = ListaReceitasAdapter(receitas!!, this)
            val layoutManager = LinearLayoutManager(this)
            rvReceitas.layoutManager = layoutManager
        }
    }

    fun filterReceitas() {
        Log.i("TAG", "KeyListener")
        var filter = tilReceitaFilter.editText?.text.toString()
        if (!filter.isNullOrBlank()) {
            loadReciclyView(this.receitas?.filter{ r -> r.nome.contains(filter, true) })
        } else {
            loadReciclyView(this.receitas)
        }
    }
}
