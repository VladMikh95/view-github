package ml.vladmikh.projects.view_github.data.repository

import ml.vladmikh.projects.view_github.data.network.ApiService
import javax.inject.Inject

class UserInfoRepository @Inject constructor(
    private val apiService: ApiService
) {

    suspend fun getUserInfo(token: String, userName: String) = apiService.getUserInfo(token, userName)
}