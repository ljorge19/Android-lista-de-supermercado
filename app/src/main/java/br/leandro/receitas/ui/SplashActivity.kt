package br.agner.receitas.ui

import android.app.AlertDialog
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import br.agner.receitas.R
import br.agner.receitas.api.HealthAPI
import br.agner.receitas.api.RetrofitClient
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        carregar()
    }

    fun carregar() {
        val animacao = AnimationUtils.loadAnimation(this, R.anim.animacao_splash)
        ivLogo.startAnimation(animacao)

        ApiHealthTask(this).execute();
    }

    class ApiHealthTask(var thisActivy: AppCompatActivity) : AsyncTask<Void, Void, String>() {

        private var api = RetrofitClient.getInstance().create(HealthAPI::class.java)

        override fun doInBackground(vararg params: Void?): String? {
            Log.i("TAG", "Verificar API")

            var falhas = 0
            var success = false
            Log.i("TAG", "Verificando!")
            do {
                falhas++
                Log.i("TAG", "Tentativa "+falhas+"!")

                try {
                    var call = api.getHealth()
                    var res = call.execute()
                    success = res.isSuccessful()
                }catch (e: Exception) {
                    success = false
                    Thread.sleep(5000)
                }

                Thread.sleep(3000)

            } while (falhas<10 && !success)

            if (!success) {
                Log.i("TAG", "API fora do ar!")

                val builder = AlertDialog.Builder(thisActivy)
                builder.setTitle(R.string.app_name)
                builder.setMessage(R.string.question_api_off)
                //builder.setMessage("API não está respondendo! Deseja continuar mesmo assim?")
                builder.setPositiveButton(R.string.yes){dialog, which ->
                    //thisActivy.startActivity(Intent(thisActivy, MainActivity::class.java))
                    thisActivy.startActivity(Intent(thisActivy, LoginActivity::class.java))
                    thisActivy.finish()
                }
                builder.setNegativeButton(R.string.no){dialog,which ->
                    thisActivy.finish()
                }
                val dialog: AlertDialog = builder.create()
                dialog.show()
            } else {
                Log.i("TAG", "API OK!")
                //thisActivy.startActivity(Intent(thisActivy, MainActivity::class.java))
                thisActivy.startActivity(Intent(thisActivy, LoginActivity::class.java))
                thisActivy.finish()
                return "Ok"
            }
            return "Fail"
        }
    }

    fun onYesClick() {
        finish()
    }

}
