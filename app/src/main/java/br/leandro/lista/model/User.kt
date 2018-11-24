package br.leandro.lista.model

import java.io.Serializable

data class User(var id: String?,
                 var login: String,
                 var name: String,
                 var password: String) : Serializable