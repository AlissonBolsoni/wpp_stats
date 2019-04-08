package com.example.wppstats.data.model

import java.io.Serializable

class ObStatus(
    var id: Int,
    var ehNovo: Boolean,
    var imagem: String,
    var texto: String,
    val usuario: ObUsuario
) : Serializable
