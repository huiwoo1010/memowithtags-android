package com.example.memowithtags.Network

data class SignupRequest(
    val email: String,
    val nickname: String,
    val password: String
)

data class SendEmailRequest(
    val email: String
)

data class VerifyEmailRequest(
    val email: String,
    val verificationCode: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class ChangePwRequest(
    val email: String,
    val password: String
)



data class SignupResponse(
    val accessToken: String,
    val refreshToken: String
)

data class LoginResponse(
    val accessToken: String,
    val refreshToken: String
)