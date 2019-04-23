package com.example.wppstats.data.dao

import com.example.wppstats.data.model.ObStatus
import com.example.wppstats.data.model.ObUsuario

class StatusDao {

    companion object {
        private var id = 0
        get() {
            field ++
            return field
        }
        private val statusNovos = arrayListOf(
            ObStatus(id,true, false, "https://api.adorable.io/avatars/285/1.png", "", ObUsuario("usuario1")),
            ObStatus(id,true, false, "", "Exemplo com texto curto", ObUsuario("usuario2")),
            ObStatus(id,true, false, "https://api.adorable.io/avatars/285/23.png", "", ObUsuario("usuario3")),
            ObStatus(id,true, false, "https://api.adorable.io/avatars/285/597.png", "", ObUsuario("usuario4")),
            ObStatus(id,true, false, "", "Exemplo de um texto longo para mostrar na tela de visualização", ObUsuario("usuario5")),
            ObStatus(id,true, false, "https://api.adorable.io/avatars/285/06651.png", "", ObUsuario("usuario6"))
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

    fun criaStatusImagem(path: String) {
        statusNovos.add(ObStatus(StatusDao.id, true, false, path, "", ObUsuario("Meu Status")))
    }

    fun criaStatusTexto(texto: String) {
        statusNovos.add(ObStatus(StatusDao.id, true, false, "", texto, ObUsuario("Meu Status")))
    }

}