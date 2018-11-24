package br.leandro.lista.ui

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import br.leandro.lista.R
import br.leandro.lista.api.ReceitaAPI
import br.leandro.lista.api.RetrofitClient
import br.leandro.lista.model.Receita
import br.leandro.lista.ui.adapter.ListaIngredientesViewAdapter
import br.leandro.lista.utils.MyDialog
import kotlinx.android.synthetic.main.activity_receita_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReceitaViewActivity : AppCompatActivity() {

    private lateinit var receita: Receita
    private var api = RetrofitClient.getInstance().create(ReceitaAPI::class.java)
    private val reqCodeForm = 1;
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receita_view)

        carregarDados()

        btExcluir.setOnClickListener {


            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setMessage(R.string.question_confirme_delete)
            builder.setPositiveButton(R.string.yes){dialog, which ->
                api.deleteReceita(receita.id).enqueue(object : Callback<Receita> {

                    override fun onResponse(call: Call<Receita>?, response: Response<Receita>?) {

                        if (response?.isSuccessful() == true) {
                            finish()
                        } else {
                            Log.i("TAG", response?.errorBody()?.charStream()?.readText())
                            messageDialog(R.string.api_error)
                        }

                    }

                    override fun onFailure(call: Call<Receita>?, t: Throwable?) {
                        Log.i("TAG", t?.message)
                        messageDialog(R.string.api_error)
                    }
                })
            }
            builder.setNegativeButton(R.string.no){dialog,which ->
                Log.i("TAG", "Exclusão cancelada")
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()

            //finish()

        }

        btEditar.setOnClickListener {
            var i = Intent(this, ReceitaFormActivity::class.java)
            i.putExtra("receita", receita)
            //this.startActivity(i)
            startActivityForResult(i, reqCodeForm)
        }

        btVoltar.setOnClickListener {
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == reqCodeForm && resultCode == 0) {
            receita = data?.extras?.get("receita") as Receita
            bindReceitaFields()
        }
    }

    override fun onResume() {
        super.onResume()
        bindReceitaFields()
    }

    fun carregarDados() {
        receita = intent.extras.get("receita") as Receita
        Log.i("TAG", "Chegou aqui a receita "+ receita.nome)
    }

    fun bindReceitaFields() {
        Log.i("TAG", "Binding fields")
        tvReceitaNome.text = receita.nome
        rvIngredientes.adapter = ListaIngredientesViewAdapter(receita.ingredientes, this)
        val layoutManager = LinearLayoutManager(this)
        rvIngredientes.layoutManager = layoutManager
        tvReceitaPreparo.text = receita.preparo
    }

    fun messageDialog(msgId : Int) {
        MyDialog.messageDialog(this, msgId)
    }
}
