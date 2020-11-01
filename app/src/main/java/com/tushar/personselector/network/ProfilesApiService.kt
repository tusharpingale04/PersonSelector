package com.tushar.personselector.network

import com.tushar.personselector.model.user.PersonDetailsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * This Interface consists of all the abstract methods pertaining to network present inside the app.
 */
interface ProfilesApiService {

    @GET("api")
    suspend fun fetchPersonList(@Query("results") results : Int): Response<PersonDetailsResponse>
}