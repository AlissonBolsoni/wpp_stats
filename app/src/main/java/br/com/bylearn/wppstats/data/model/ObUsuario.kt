package br.com.bylearn.wppstats.data.model

import java.io.Serializable

class ObUsuario(val nome: String): Serializable {
    val imagem = "https://api.adorable.io/avatars/285/$nome.png"

    companion object {
        fun meuUsuario() = ObUsuario("Meu Status")
    }

}
