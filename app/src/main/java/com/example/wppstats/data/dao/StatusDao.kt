package com.example.wppstats.data.dao

import com.example.wppstats.data.model.ObStatus
import com.example.wppstats.data.model.ObUsuario

class StatusDao {

    companion object {
        private val statusNovos = arrayListOf(
            ObStatus(1,true, "https://api.adorable.io/avatars/285/1.png", "", ObUsuario("usuario1")),
            ObStatus(2,true, "", "Exemplo com texto curto", ObUsuario("usuario2")),
            ObStatus(3,true, "https://api.adorable.io/avatars/285/23.png", "", ObUsuario("usuario3")),
            ObStatus(4,true, "https://api.adorable.io/avatars/285/597.png", "", ObUsuario("usuario4")),
            ObStatus(5,true, "", "Exemplo de um texto longo para mostrar na tela de visualização", ObUsuario("usuario5")),
            ObStatus(6,true, "https://api.adorable.io/avatars/285/06651.png", "", ObUsuario("usuario6"))
        )

        private val statusLidos = ArrayList<ObStatus>()
    }

    fun buscaStatusNovos(): ArrayList<ObStatus>{
        statusNovos.sortBy { obStatus -> obStatus.id }
        return statusNovos
    }

    fun buscaStatusLidos(): ArrayList<ObStatus>{
        statusLidos.sortBy { obStatus -> obStatus.id }
        return statusLidos
    }


    fun marcarComoVisto(obStatus: ObStatus){
        val iterator = statusNovos.iterator()

        while (iterator.hasNext()){
            val st = iterator.next()
            if (st.id == obStatus.id){
                st.ehNovo = false
                statusLidos.add(st)
                iterator.remove()
                return
            }
        }
    }

}