package com.manikandan.pexels.data.network

import com.manikandan.pexels.modal.PexelImage
import com.manikandan.pexels.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.QueryMap

interface PexelImagesApi {

    @GET("search")
    suspend fun getPexelImages(@QueryMap queries: Map<String, String>): Response<PexelImage>
}