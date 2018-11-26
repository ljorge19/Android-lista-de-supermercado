package br.leandro.lista.api

import br.leandro.lista.model.User
import retrofit2.Call
import retrofit2.http.*

interface UserAPI {

    @GET("/user")
    fun listaUsers() : Call<List<User>>

    @POST("/user")
    fun postUser(@Body user: User) : Call<User>

    @GET("/user/{login}")
    fun getUser(@Path("login") login: String) : Call<User>

    @DELETE("/user")
    fun deleteUser(@Body user: User) : Call<User>

    @DELETE("/user/{login}")
    fun deleteUser(@Path("login") login: String) : Call<User>

    @PUT("/user")
    fun putUser(@Body user: User) : Call<User>

}