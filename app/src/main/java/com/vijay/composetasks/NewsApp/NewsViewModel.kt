package com.vijay.composetasks.NewsApp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vijay.composetasks.NewsApp.model.Article
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NewsViewModel:ViewModel() {
    private val _articles = MutableLiveData<List<Article>>(emptyList())
    val articles : LiveData<List<Article>> = _articles

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val newsApiService = retrofit.create(NewsApiResponse::class.java)

    init {
        fetchTopHeadlines()
    }

    fun fetchTopHeadlines(){
        viewModelScope.launch {
            try {
                val response = newsApiService.getTopHeadLines()
                if(response.isSuccessful){
                    _articles.value = response.body()?.articles ?: emptyList()
                }else{
                    Log.d("exception", "error")
                }
            }catch (e:Exception){
                Log.d("exception", e.localizedMessage.toString())
            }
        }
    }

}