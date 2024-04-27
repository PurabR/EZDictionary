package com.example.ezdictionary


import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface DictionaryAPI {
@GET("en/{word}")
suspend fun getMeaning(@Path("word")word: String) : retrofit2.Response<List<WordResult>>

}