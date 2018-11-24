package br.leandro.lista.api

import br.leandro.lista.model.Receita
import retrofit2.Call
import retrofit2.http.*

interface ReceitaAPI {

    @GET("/receita")
    fun listaReceitas() : Call<List<Receita>>

    @POST("/receita")
    fun postReceita(@Body receita: Receita) : Call<Receita>

    @GET("/receita/{id}")
    fun getReceita(@Path("id") id: String) : Call<Receita>

    @DELETE("/receita")
    fun deleteReceita(@Body receita: Receita) : Call<Receita>

    @DELETE("/receita/{id}")
    fun deleteReceita(@Path("id") id: String?) : Call<Receita>

    @PUT("/receita")
    fun putReceita(@Body receita: Receita) : Call<Receita>

}