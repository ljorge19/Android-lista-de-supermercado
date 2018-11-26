package br.leandro.lista.api

import br.leandro.lista.model.Lista
import retrofit2.Call
import retrofit2.http.*

interface ListaAPI {

    @GET("/lista")
    fun listaListas() : Call<List<Lista>>

    @POST("/lista")
    fun postLista(@Body lista: Lista) : Call<Lista>

    @GET("/lista/{id}")
    fun getLista(@Path("id") id: String) : Call<Lista>

    @DELETE("/lista")
    fun deleteLista(@Body lista: Lista) : Call<Lista>

    @DELETE("/lista/{id}")
    fun deleteLista(@Path("id") id: String?) : Call<Lista>

    @PUT("/lista")
    fun putLista(@Body lista: Lista) : Call<Lista>

}