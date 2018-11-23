package br.agner.receitas.api

import br.agner.receitas.model.Health
import retrofit2.Call
import retrofit2.http.*

interface HealthAPI {

    @GET("/health")
    fun getHealth() : Call<Health>


}