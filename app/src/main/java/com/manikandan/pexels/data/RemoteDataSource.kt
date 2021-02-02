package com.manikandan.pexels.data

import com.manikandan.pexels.data.network.PexelImagesApi
import com.manikandan.pexels.modal.PexelImage
import com.manikandan.pexels.modal.Photo
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val pexelImagesApi: PexelImagesApi) {

    suspend fun getPexelImages(queries: Map<String, String>): Response<PexelImage>{
        return pexelImagesApi.getPexelImages(queries)
    }
}