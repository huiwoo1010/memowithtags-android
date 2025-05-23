package com.example.memowithtags.Viewmodels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.memowithtags.Network.WithdrawalRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _withdrawalResult = MutableLiveData<Result<Boolean>>()
    val withdrawalResult: LiveData<Result<Boolean>> get() = _withdrawalResult

    fun withdrawAccount() {
        val token = repository.getToken()
        val email = repository.getEmail()

        if (token.isNullOrBlank() || email.isNullOrBlank()) {
            _withdrawalResult.value = Result.failure(Throwable("저장된 로그인 정보가 없습니다."))
            return
        }

        val request = WithdrawalRequest(email)
        val authHeader = "Bearer $token"

        repository.withdrawUser(authHeader, request).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    repository.clearAuthData()
                    _withdrawalResult.value = Result.success(true)
                } else {
                    _withdrawalResult.value = Result.failure(Throwable("탈퇴 실패: ${response.code()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                _withdrawalResult.value = Result.failure(t)
            }
        })
    }
}
