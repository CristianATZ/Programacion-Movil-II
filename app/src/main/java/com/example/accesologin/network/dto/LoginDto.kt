package com.example.accesologin.network.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("strMatricula") val matricula: String,
    @SerializedName("strContrasenia") val contrasenia: String,
    @SerializedName("tipoUsuario") val tipoUsuario: String
)
data class TokenDto(
    @SerializedName("accessToken") val accessTokenVerify: String
)