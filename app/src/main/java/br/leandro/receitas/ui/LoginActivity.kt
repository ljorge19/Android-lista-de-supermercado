package br.agner.receitas.ui

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import br.agner.receitas.R
import br.agner.receitas.api.RetrofitClient
import br.agner.receitas.api.UserAPI
import br.agner.receitas.model.User
import br.agner.receitas.utils.MyDialog
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : AppCompatActivity() {

    private var api = RetrofitClient.getInstance().create(UserAPI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btSignIn.setOnClickListener{
            signIn()
        }

        btRegistry.setOnClickListener{
            registry()
        }

    }

    fun signIn() {
        var login = tvLogin.text.toString()
        var password = etPassword.text.toString()

        api.getUser(login).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                if (response?.isSuccessful() == true) {
                    var user = response.body()
                    if (user?.password.equals(etPassword.text.toString())) {
                        var i = Intent(applicationContext, MainActivity::class.java)
                        i.putExtra("user", user)
                        startActivity(i)
                        finish()
                    } else {
                        messageDialog(R.string.wrong_password)
                    }

                } else {
                    Log.i("TAG", "Erro de senha");
                    Log.i("TAG", response?.errorBody()?.charStream()?.readText())
                    messageDialog(R.string.api_error)
                }
            }

            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.i("TAG", t?.message)
                messageDialog(R.string.api_error)
            }
        })

    }

    fun registry() {
        var i = Intent(this, RegistryActivity::class.java)
        startActivity(i)
        finish()
    }

    fun messageDialog(msgId : Int) {
        MyDialog.messageDialog(this, msgId)
    }

    fun cleanField() {
        tvLogin.text.clear()
        etPassword.text.clear()
    }
}
