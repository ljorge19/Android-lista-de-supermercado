package br.agner.receitas.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import br.agner.receitas.R
import br.agner.receitas.api.ReceitaAPI
import br.agner.receitas.api.RetrofitClient
import br.agner.receitas.model.Ingrediente
import br.agner.receitas.model.Receita
import br.agner.receitas.ui.adapter.ListaIngredientesFormAdapter
import br.agner.receitas.utils.MyDialog
import kotlinx.android.synthetic.main.activity_receita_form.*
import kotlinx.android.synthetic.main.item_ingrediente_form.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceitaFormActivity : AppCompatActivity() {

    private lateinit var receita: Receita
    private lateinit var adapter: ListaIngredientesFormAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita_form)

        carregarDados()
        bindReceitaFields()

        btAddIngrediente.setOnClickListener {
            bindFieldReceita()
            receita.ingredientes.add(Ingrediente("",""))
            bindReceitaFields()
        }

        btCancelar.setOnClickListener{
            var resultIntent = Intent()
            resultIntent.putExtra("receita", receita);
            setResult(1, resultIntent);
            finish();
        }

        btSalvar.setOnClickListener {
            var api = RetrofitClient.getInstance().create(ReceitaAPI::class.java)
            bindFieldReceita()
            if (receita.id == null) {
                api.postReceita(receita).enqueue(object :  Callback<Receita> {
                    override fun onResponse(call: Call<Receita>?, response: Response<Receita>?) {
                        if (response?.isSuccessful() == true) {
                            receita = response.body()!!
                            var resultIntent = Intent()
                            resultIntent.putExtra("receita", receita);
                            setResult(0, resultIntent);
                            finish();
                        } else {
                            Log.i("TAG", "Erro ao salvar receita!")
                            messageDialog(R.string.api_error)
                        }
                    }

                    override fun onFailure(call: Call<Receita>?, t: Throwable?) {
                        Log.i("TAG", "Erro ao salvar receita! "+t?.message)
                        messageDialog(R.string.api_error)
                    }
                })
            } else {
                api.putReceita(receita).enqueue(object :  Callback<Receita> {
                    override fun onResponse(call: Call<Receita>?, response: Response<Receita>?) {
                        if (response?.isSuccessful() == true) {
                            receita = response.body()!!
                            var resultIntent = Intent()
                            resultIntent.putExtra("receita", receita);
                            setResult(0, resultIntent);
                            finish();
                        } else {
                            Log.i("TAG", "Erro ao salvar receita!")
                            messageDialog(R.string.api_error)
                        }
                    }

                    override fun onFailure(call: Call<Receita>?, t: Throwable?) {
                        Log.i("TAG", "Erro ao salvar receita! "+t?.message)
                        messageDialog(R.string.api_error)
                    }
                })
            }
        }
    }

    override fun onBackPressed() {
        var resultIntent = Intent()
        resultIntent.putExtra("receita", receita);
        setResult(1, resultIntent);
        super.onBackPressed()
    }

    fun carregarDados() {
        receita = intent.extras.get("receita") as Receita
        Log.i("TAG", "Chegou aqui a receita "+ receita.nome)
    }

    fun bindReceitaFields() {
        Log.i("TAG", "Binding fields")
        tilReceitaNome.editText?.setText(receita.nome)
        adapter = ListaIngredientesFormAdapter(receita.ingredientes, this)
        rvIngredientes.adapter = adapter
        val layoutManager = LinearLayoutManager(this)
        rvIngredientes.layoutManager = layoutManager

        tilReceitaPreparo.editText?.setText(receita.preparo)
    }

    fun bindFieldReceita() {
        Log.i("TAG", "Binding fields")
        receita.nome = tilReceitaNome.editText?.text.toString()

        receita.ingredientes.clear()
        adapter.views.forEach {
            if (!it.tilIngredienteNome.editText?.text.isNullOrBlank()) {
                receita.ingredientes.add(Ingrediente(it.tilIngredienteNome.editText?.text.toString(),
                        it.tilIngredienteQtde.editText?.text.toString()))
            }
        }

        receita.preparo = tilReceitaPreparo.editText?.text.toString()
    }

    fun messageDialog(msgId : Int) {
        MyDialog.messageDialog(this, msgId)
    }

}
