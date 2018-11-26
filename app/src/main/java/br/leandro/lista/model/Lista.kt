package br.leandro.lista.model

import java.io.Serializable


data class Lista(var id: String?,
                 var nome: String,
                 var produtos: ArrayList<Produto>,
                 var comentario: String) : Serializable