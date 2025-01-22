package com.vijay.composetasks.NewsApp

import com.vijay.composetasks.NewsApp.model.NewsModel
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiResponse {
    @GET("v2/everything?q=bitcoin&apiKey=cddd9bd810f04e0ebc31e903b373b3c7")
    suspend fun getTopHeadLines():Response<NewsModel>

}