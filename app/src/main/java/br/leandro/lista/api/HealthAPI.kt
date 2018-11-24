package br.leandro.lista.api

import br.leandro.lista.model.Health
import retrofit2.Call
import retrofit2.http.*

interface HealthAPI {

    @GET("/health")
    fun getHealth() : Call<Health>


}