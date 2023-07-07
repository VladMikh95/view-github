package ml.vladmikh.projects.view_github.data.network

import ml.vladmikh.projects.view_github.data.model.UserInfo
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {

    @GET("users/{username}")
    suspend fun getUserInfo(@Header("Authorization") accesstoken: String, @Path("username") username: String): Response<ResponseBody>
}