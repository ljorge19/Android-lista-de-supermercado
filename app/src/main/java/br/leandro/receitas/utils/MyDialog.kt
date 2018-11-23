package br.agner.receitas.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import br.agner.receitas.R

class MyDialog {

    companion object Factory{

        fun messageDialog(context : Context, msg : String) {
            val builder = AlertDialog.Builder (context)
            builder.setTitle(R.string.app_name)
            builder.setMessage(msg)
            builder.setNeutralButton(R.string.ok, null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        fun messageDialog(context : Context, msgId : Int) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(R.string.app_name)
            builder.setMessage(msgId)
            builder.setNeutralButton(R.string.ok, null)
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

    }

}