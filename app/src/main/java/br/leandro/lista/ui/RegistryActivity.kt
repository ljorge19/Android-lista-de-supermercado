package br.leandro.lista.ui

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import br.leandro.lista.R
import br.leandro.lista.api.RetrofitClient
import br.leandro.lista.api.UserAPI
import br.leandro.lista.model.User
import br.leandro.lista.utils.MyDialog
import kotlinx.android.synthetic.main.activity_registry.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistryActivity : AppCompatActivity() {

    private var api = RetrofitClient.getInstance().create(UserAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registry)

        btSave.setOnClickListener{
            save()
        }
    }

    fun save() {
        if (etPassword.text.toString().equals(etRePassword.text.toString())) {
            var user = User(null, tvLogin.text.toString(), tvName.text.toString(), etPassword.text.toString())
            api.postUser(user).enqueue(object : Callback<User> {
                override fun onResponse(call: Call<User>?, response: Response<User>?) {
                    if (response?.isSuccessful() == true) {
                        var user = response.body()
                        Log.i("TAG", "User "+user?.name+" salvo!")
                        var i = Intent(applicationContext, MainActivity::class.java)
                        i.putExtra("user", user)
                        startActivity(i)
                        finish()
                    } else {
                        Log.i("TAG", "Erro ao salvar!")
                        Log.i("TAG", response?.errorBody()?.charStream()?.readText())
                        messageDialog(R.string.registry_error)
                    }
                }

                override fun onFailure(call: Call<User>?, t: Throwable?) {
                    Log.i("TAG", t?.message)
                }
            })
        } else {
            Log.i("TAG", "Senha não confere!")
            messageDialog(R.string.wrong_password)
        }
    }

    fun messageDialog(msgId : Int) {
        MyDialog.messageDialog(this, msgId)
    }

}
