package br.com.bylearn.wppstats.ui.adapter

import br.com.bylearn.wppstats.data.model.ObStatus

class StatusLidosAdapter(list: ArrayList<ObStatus>, private val onClick: (ObStatus) -> Unit): BaseAdapter(list) {

    override fun criarMensagemSistema() {
        val status = getItem(0)
        if (!status.ehSistema)
            addMensagemSistema(ObStatus.criarMensagemSistema(false))
    }

    override fun onClickItem(obStatus: ObStatus) {
        onClick(obStatus)
    }
}