package com.francisco.strider.data.api

import com.francisco.strider.data.entities.CheckIn
import com.francisco.strider.data.entities.Event
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface EventApi {
    @GET("events")
    suspend fun getEvents(): List<Event>

    @GET("events/{id}")
    suspend fun getEvent(@Path("id") id: String): Event

    @POST("checkin")
    suspend fun checkIn(@Body checkIn: CheckIn)
}