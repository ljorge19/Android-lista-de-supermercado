package br.leandro.lista.ui

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import br.leandro.lista.R
import br.leandro.lista.api.ListaAPI
import br.leandro.lista.api.RetrofitClient
import br.leandro.lista.model.Lista
import br.leandro.lista.ui.adapter.ListaProdutosViewAdapter
import br.leandro.lista.utils.MyDialog
import kotlinx.android.synthetic.main.activity_lista_view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListaViewActivity : AppCompatActivity() {

    private lateinit var lista: Lista
    private var api = RetrofitClient.getInstance().create(ListaAPI::class.java)
    private val reqCodeForm = 1;
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_view)

        carregarDados()

        btExcluir.setOnClickListener {


            val builder = AlertDialog.Builder(this)
            builder.setTitle(R.string.app_name)
            builder.setMessage(R.string.question_confirme_delete)
            builder.setPositiveButton(R.string.yes){dialog, which ->
                api.deleteLista(lista.id).enqueue(object : Callback<Lista> {

                    override fun onResponse(call: Call<Lista>?, response: Response<Lista>?) {

                        if (response?.isSuccessful() == true) {
                            finish()
                        } else {
                            Log.i("TAG", response?.errorBody()?.charStream()?.readText())
                            messageDialog(R.string.api_error)
                        }

                    }

                    override fun onFailure(call: Call<Lista>?, t: Throwable?) {
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
            var i = Intent(this, ListaFormActivity::class.java)
            i.putExtra("lista", lista)
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
            lista = data?.extras?.get("lista") as Lista
            bindReceitaFields()
        }
    }

    override fun onResume() {
        super.onResume()
        bindReceitaFields()
    }

    fun carregarDados() {
        lista = intent.extras.get("lista") as Lista
        Log.i("TAG", "Chegou aqui a lista "+ lista.nome)
    }

    fun bindReceitaFields() {
        Log.i("TAG", "Binding fields")
        tvReceitaNome.text = lista.nome
        rvIngredientes.adapter = ListaProdutosViewAdapter(lista.produtos, this)
        val layoutManager = LinearLayoutManager(this)
        rvIngredientes.layoutManager = layoutManager
        tvListaComentario.text = lista.comentario
    }

    fun messageDialog(msgId : Int) {
        MyDialog.messageDialog(this, msgId)
    }
}
