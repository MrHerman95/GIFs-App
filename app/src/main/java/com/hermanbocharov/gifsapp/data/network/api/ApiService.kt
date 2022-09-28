package com.hermanbocharov.gifsapp.data.network.api

import com.hermanbocharov.gifsapp.data.network.model.GifsListDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("gifs/search")
    suspend fun getSearchGifsList(
        @Query(QUERY_PARAM_SEARCH_KEY) searchKey: String,
        @Query(QUERY_PARAM_LIMIT) limit: Int,
        @Query(QUERY_PARAM_OFFSET) offset: Int,
        @Query(QUERY_PARAM_RATING) rating: String = DEFAULT_RATING,
        @Query(QUERY_PARAM_LANG) lang: String = DEFAULT_LANG,
        @Query(QUERY_PARAM_API_KEY) apiKey: String = API_KEY
    ): GifsListDto

    companion object {
        private const val QUERY_PARAM_SEARCH_KEY = "q"
        private const val QUERY_PARAM_API_KEY = "api_key"
        private const val QUERY_PARAM_LIMIT = "limit"
        private const val QUERY_PARAM_OFFSET = "offset"
        private const val QUERY_PARAM_RATING = "rating"
        private const val QUERY_PARAM_LANG = "lang"

        private const val DEFAULT_RATING = "g"
        private const val DEFAULT_LANG = "en"
        private const val API_KEY = "pLfhO999r4l8wRuommWgSPCEk91pW0t6"
    }
}