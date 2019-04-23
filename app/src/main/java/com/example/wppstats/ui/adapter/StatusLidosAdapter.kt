package com.example.wppstats.ui.adapter

import com.example.wppstats.data.model.ObStatus

class StatusLidosAdapter(list: ArrayList<ObStatus>, private val onCLick: (ObStatus) -> Unit) : BaseAdapter(list) {

    override fun onClickItem(item: ObStatus) {
        onCLick(item)
    }

    override fun criarMensagemSistema() {
        val st = getItem(0)
        if (!st.ehSistema)
            addMensagemSistema(ObStatus.criarMensagemSistema(false))
    }
}