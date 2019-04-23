package com.example.wppstats.data.model

import com.example.wppstats.data.dao.StatusDao
import java.io.Serializable
import java.util.*

class ObStatus(
    var id: Int,
    var ehNovo: Boolean,
    var ehSistema: Boolean,
    var imagem: String,
    var texto: String,
    val usuario: ObUsuario
) : Serializable{
    var data: Date? = null

    init {
        val cal = Calendar.getInstance()
        data = cal.time
    }

    companion object {
        fun criarMensagemSistema(ehNovo: Boolean):ObStatus{
            if (ehNovo)
                return ObStatus(0, true, true, "", "Novos Status", ObUsuario(""))

            return ObStatus(0, false, true, "", "Status Vistos", ObUsuario(""))
        }
    }

}
