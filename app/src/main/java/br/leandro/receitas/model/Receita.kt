package br.agner.receitas.model

import java.io.Serializable


data class Receita(var id: String?,
                   var nome: String,
                   var ingredientes: ArrayList<Ingrediente>,
                   var preparo: String) : Serializable